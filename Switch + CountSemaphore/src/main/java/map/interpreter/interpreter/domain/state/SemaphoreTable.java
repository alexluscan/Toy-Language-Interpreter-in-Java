package map.interpreter.interpreter.domain.state;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionary;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.list.MyList;
import map.interpreter.interpreter.domain.values.Value;
import javafx.util.Pair;

import java.util.*;

public class SemaphoreTable implements ISemaphoreTable {

    MyIDictionary<Integer, Pair<Integer, MyIList<Integer>>> semaphoreTable;
    int nextFree;

    public SemaphoreTable() {
        this.semaphoreTable = new MyDictionary<>();
        nextFree = 1;
    }


    @Override
    public synchronized void setContent(MyIDictionary<Integer, Pair<Integer, MyIList<Integer>>> newContent) {
        this.semaphoreTable.clear();
        this.semaphoreTable.putAll(newContent.getContent());
    }

    @Override
    public Integer put(Pair<Integer, MyIList<Integer>> value) throws MyDictionaryException {
        this.semaphoreTable.put(this.nextFree, value);
        this.nextFree += 1;

        return this.nextFree - 1;
    }

    @Override
    public synchronized void put(Integer key, Pair<Integer, MyIList<Integer>> values) throws MyDictionaryException {
        this.semaphoreTable.put(key, values);
    }

    @Override
    public synchronized Pair<Integer, MyIList<Integer>> remove(Integer key) throws MyDictionaryException {
        return this.semaphoreTable.remove(key);
    }

    @Override
    public synchronized Pair<Integer, MyIList<Integer>> update(Integer key, Pair<Integer, MyIList<Integer>> newValues) throws MyDictionaryException {
        return this.semaphoreTable.update(key, newValues);
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return this.semaphoreTable.containsKey(key);
    }

    @Override
    public synchronized Pair<Integer, MyIList<Integer>> get(Integer key) throws MyDictionaryException {
        return this.semaphoreTable.get(key);
    }

    @Override
    public synchronized HashMap<Integer, Pair<Integer, MyIList<Integer>>> getContent() {
        return this.semaphoreTable.getContent();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.semaphoreTable.isEmpty();
    }

    @Override
    public synchronized int size() {
        return this.semaphoreTable.size();
    }

    @Override
    public synchronized String toString() {
        StringBuilder state = new StringBuilder("{");

        for (Integer key : semaphoreTable.getContent().keySet()) {
            Pair<Integer, MyIList<Integer>> value = null;
            try {
                value = semaphoreTable.get(key);
            } catch (MyDictionaryException e) {
                throw new RuntimeException(e);
            }
            state.append(key).append("->").append(value.getKey().toString()).append(value.getValue().toString()).append(", ");
        }

        if (!semaphoreTable.isEmpty()) {
            state.setLength(state.length() - 2);
        }

        state.append("}");

        return state.toString();
    }
}
