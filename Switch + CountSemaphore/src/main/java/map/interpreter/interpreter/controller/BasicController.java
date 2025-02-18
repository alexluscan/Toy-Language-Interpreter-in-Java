////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.controller;

import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.values.RefValue;
import map.interpreter.interpreter.domain.values.Value;
import map.interpreter.interpreter.repository.BasicRepository;
import map.interpreter.interpreter.repository.Repository;
import map.interpreter.interpreter.repository.RepositoryException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class BasicController implements Controller {

    // BASIC CONTROLLER STRUCTURE
    Repository repository;
    ExecutorService executor;
    boolean displayFlag;


    // BASIC CONTROLLER CONSTRUCTOR
    public BasicController(int flagConfiguration, String logFilePath, PrgState prgState) {

        // Set up repository
        repository = new BasicRepository(logFilePath, prgState);

        // Set display flag
        switch (flagConfiguration){
            case 0: this.displayFlag = false; break;
            case 1: this.displayFlag = true; break;
        }
    }

    // BASIC CONTROLLER METHODS
    // Getter Methods
    // Gets the number of all programs
    @Override
    public Integer getPrgListCount() {
        return this.repository.getPrgListCount();
    }

    // Gets the list of all programs
    @Override
    public List<PrgState> getPrgList() {
        return this.repository.getPrgList();
    }

    // Management Methods
    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    // Execution Methods
    // Execute one step for all program states
    @Override
    public void oneStepForAllPrg(List<PrgState> prgStateList) throws ControllerException {

        // Save to log file before execution
        prgStateList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                System.out.println("There was an error executing one step for all programs: " + e);
            }
        });

        // Run concurrently one step for each existing program state
        // Prepare the list of callables
        List<Callable<PrgState>> callableList = prgStateList.stream().map((PrgState p) -> (Callable<PrgState>)(p::oneStep)).toList();

        // Start the execution of the callables
        // It returns the list of newly created PrgStates (namely threads)
        List<PrgState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callableList).stream()
                    .map(future -> {try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(String.valueOf(e));
                    }
                    }).filter(Objects::nonNull).toList();
        } catch (InterruptedException e) {
            throw new ControllerException(String.valueOf(e));
        }

        // Add the new created threads to the list of existing threads
        prgStateList.addAll(newPrgList);

        // Save to log file after execution
        prgStateList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                System.out.println("There was an error executing one step for all programs: " + e);
            }
        });

        // Save the current programs in the repository
        repository.setPrgList(prgStateList);
    }

    // Execute one step for all program states
    @Override
    public void oneStepForAllPrg() throws ControllerException {

        // Create new thread pool
        executor = Executors.newFixedThreadPool(2);

        // Remove the completed programs
        List<PrgState> prgStateList = removeCompletedPrg(repository.getPrgList());

        if(prgStateList.isEmpty())
            throw new ControllerException("The execution stack is empty!");

        // Display the current states before beginning work
        prgStateList.forEach(System.out::println);

        // Call conservative garbage collector
        conservativeGarbageCollector(prgStateList);

        // Execute one step of the program
        oneStepForAllPrg(prgStateList);

        // Display the current states
        prgStateList.forEach(System.out::println);
    }

    // Execute entire program - all statements
    @Override
    public void allStep() throws ControllerException {

        // Clear log file first
        try {
            this.repository.clearLogFile();
        } catch (RepositoryException e) {
            throw new ControllerException("LOG ERROR: " + e);
        }

        // Create new thread pool
        executor = Executors.newFixedThreadPool(2);

        // Remove the completed programs
        List<PrgState> prgStateList = removeCompletedPrg(repository.getPrgList());

        // Display the current states before beginning work
        prgStateList.forEach(System.out::println);

        // Execute until all threads have finished working
        while(!prgStateList.isEmpty()) {

            // Call conservative garbage collector
            conservativeGarbageCollector(prgStateList);

            // Execute one step of the program
            oneStepForAllPrg(prgStateList);

            // Display the current states
            prgStateList.forEach(System.out::println);

            // Remove the completed programs
            prgStateList = removeCompletedPrg(repository.getPrgList());
        }

        // Shutdown the thread pool now
        executor.shutdownNow();

        // Here the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository

        // Update the repository state
        repository.setPrgList(prgStateList);
    }

    // Garbage Collector Related Methods
    @Override
    public void conservativeGarbageCollector(List<PrgState> prgList) {

        // Collect all addresses from all program states' symbol tables
        List<Integer> symTableAddresses = prgList.stream()
                .flatMap(prg -> {
                        return getAddrFromSymTable(prg.getSymbolsTable().getContent().values()).stream();
                })
                .collect(Collectors.toList());

        // Get the shared heap from any program state (since it's shared)
        Map<Integer, Value> heap = prgList.getFirst().getHeap().getContent();

        // Use the safe garbage collector to filter the heap
        Map<Integer, Value> filteredHeap = safeGarbageCollector(symTableAddresses, heap);

        // Update the heap in all program states
        prgList.forEach(prg -> prg.getHeap().setContent(filteredHeap));
    }


    @Override
    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {

        // Filter the heap based on symbolic table reachable addresses
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {

        // Get all reachable addresses by including those in the heap cells
        List<Integer> reachable = getReachableAddresses(symTableAddr, heap);

        // Filter the heap based on reachable addresses
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getReachableAddresses(List<Integer> symTableAddr, Map<Integer, Value> heap) {

        // Initialize with symTable addresses
        List<Integer> reachable = new ArrayList<>(symTableAddr);

        // Track visited addresses to avoid loops
        Set<Integer> visited = new HashSet<>(reachable);

        // Traverse all reachable addresses
        for (int i = 0; i < reachable.size(); i++) {
            Integer addr = reachable.get(i);
            Value value = heap.get(addr);

            // Continue exploring as long as the value is a RefValue
            while (value instanceof RefValue) {
                Integer refAddr = ((RefValue) value).getAddress();

                // If this address has not been visited, add it to explore further
                if (visited.add(refAddr)) {
                    reachable.add(refAddr);

                    // Update value to follow the reference
                    value = heap.get(refAddr);
                }

                // If already visited, avoid re-processing
                else {
                    break;
                }
            }
        }

        return reachable;
    }

    // Flags Related Methods
    // Display Mode
    // Set display mode ON
    @Override
    public void setDisplayMode() {
        displayFlag = true;
    }

    // Set display mode OFF
    @Override
    public void clearDisplayMode() {
        displayFlag = false;
    }

    // Check status of display mode - ON = True / OFF = False
    @Override
    public boolean isDisplayMode() {
        return displayFlag;
    }
}
