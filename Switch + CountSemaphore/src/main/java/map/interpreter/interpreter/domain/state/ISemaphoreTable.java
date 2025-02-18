////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.domain.state;


import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyIDictionary;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.values.Value;
import javafx.util.Pair;

import java.util.HashMap;

public interface ISemaphoreTable {


    void setContent(MyIDictionary<Integer, Pair<Integer, MyIList<Integer>>> newContent);

    Integer put(Pair<Integer, MyIList<Integer>> value) throws MyDictionaryException;

    void put(Integer key, Pair<Integer, MyIList<Integer>> values) throws MyDictionaryException;

    Pair<Integer, MyIList<Integer>> remove(Integer key) throws MyDictionaryException;

    Pair<Integer, MyIList<Integer>> update(Integer key, Pair<Integer, MyIList<Integer>> newValues) throws MyDictionaryException;

    boolean containsKey(Integer key);

    Pair<Integer, MyIList<Integer>> get(Integer key) throws MyDictionaryException;

    HashMap<Integer, Pair<Integer, MyIList<Integer>>> getContent();

    boolean isEmpty();

    int size();
}
