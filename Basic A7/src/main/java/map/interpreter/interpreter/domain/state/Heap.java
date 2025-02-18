////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.state;

import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.values.RefValue;
import map.interpreter.interpreter.domain.values.Value;

import java.util.*;
import java.util.stream.Collectors;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class Heap implements IHeap {

    // HEAP STRUCTURE
    // Heap is based on a <integer, value> map
    HashMap<Integer, Value> heap;
    // Heap keeps track of the next free location in memory
    int nextFree;

    // HEAP CONSTRUCTOR
    public Heap() {
        this.heap = new HashMap<>();
        nextFree = 1;
    }


    // HEAP METHODS
    // Set Content
    // Sets the content of the heap to the new one
    @Override
    public void setContent(Map<Integer, Value> newHeapContent) {
        this.heap.clear();
        this.heap.putAll(newHeapContent);
    }


    // Put
    // Adds a new value to the dictionary in the next free "address"
    public Integer put(Value value) throws MyDictionaryException {
        this.heap.put(this.nextFree, value);
        this.nextFree += 1;

        return this.nextFree - 1;
    }

    // Put
    // Adds a new (key, value) pair to the dictionary
    // Throws an exception if the key is already present inside the dictionary
    @Override
    public void put(Integer key, Value value) throws MyDictionaryException {
        this.heap.put(key, value);
    }

    // Remove
    // Removes the (key, value) pair from the dictionary, given by its key
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public Value remove(Integer key) throws MyDictionaryException {
        return this.heap.remove(key);
    }

    // Update
    // Updates a (key, value) pair in the dictionary
    // Throws an exception if the key is not already present inside the dictionary
    @Override
    public Value update(Integer key, Value newValue) throws MyDictionaryException {
        return this.heap.replace(key, newValue);
    }

    // Contains Key
    // Return True if the key is present inside the dictionary
    // Returns False otherwise
    @Override
    public boolean containsKey(Integer key) {
        return this.heap.containsKey(key);
    }

    // Get
    // Gets the value associated to the given key from the dictionary
    // Throws an exception if the key is not present inside the dictionary
    @Override
    public Value get(Integer key) throws MyDictionaryException {
        return this.heap.get(key);
    }

    // Get Content
    // Returns the hash map of all values
    @Override
    public HashMap<Integer, Value> getContent() {
        return this.heap;
    }

    // Is Empty
    // Returns True if the structure is empty
    // Returns False otherwise
    @Override
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    // Size
    // Returns the size of the structure ( number of elements in the collection )
    @Override
    public int size() {
        return this.heap.size();
    }

    // Garbage Collector Related Methods
    @Override
    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        // Get all reachable addresses by including those in the heap cells
        List<Integer> reachable = getReachableAddresses(symTableAddr, heap);

        // Filter the heap based on reachable addresses
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<Integer> getReachableAddresses(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        List<Integer> reachable = new ArrayList<>(symTableAddr);
        Set<Integer> visited = new HashSet<>(reachable);

        for (int i = 0; i < reachable.size(); i++) {
            Integer addr = reachable.get(i);
            Value value = heap.get(addr);
            if (value instanceof RefValue) {
                Integer refAddr = ((RefValue) value).getAddress();
                if (!visited.contains(refAddr)) {
                    reachable.add(refAddr);
                    visited.add(refAddr);
                }
            }
        }

        return reachable;
    }

    @Override
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    // String Formatting
    @Override
    public String toString() {
        return this.heap.toString();
    }

    // Deep Copy
    // Returns a deep copy of the structure
    @Override
    public IHeap deepCopy() throws MyDictionaryException {
        Heap newHeap = new Heap();

        for (Map.Entry<Integer, Value> entry : this.heap.entrySet()) {
            Integer key = entry.getKey();
            Value value = entry.getValue().deepCopy();
            newHeap.put(key, value);
        }

        newHeap.nextFree = this.nextFree;
        return newHeap;
    }
}
