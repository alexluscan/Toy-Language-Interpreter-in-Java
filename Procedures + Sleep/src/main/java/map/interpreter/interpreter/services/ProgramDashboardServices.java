////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.services;
import javafx.util.Pair;
import map.interpreter.interpreter.controller.BasicController;
import map.interpreter.interpreter.controller.Controller;
import map.interpreter.interpreter.controller.ControllerException;
import map.interpreter.interpreter.domain.PrgState;
import map.interpreter.interpreter.domain.datastructures.dictionary.MyDictionaryException;
import map.interpreter.interpreter.domain.datastructures.list.MyIList;
import map.interpreter.interpreter.domain.datastructures.stack.MyIStack;
import map.interpreter.interpreter.domain.datastructures.stack.MyStack;
import map.interpreter.interpreter.domain.datastructures.stack.MyStackException;
import map.interpreter.interpreter.domain.state.*;
import map.interpreter.interpreter.domain.statements.IStmt;
import map.interpreter.interpreter.domain.values.StringValue;
import map.interpreter.interpreter.domain.values.Value;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import map.interpreter.interpreter.procedures.IProcedure;

import java.util.List;
import java.util.Map;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class ProgramDashboardServices {

    // PROGRAM DASHBOARD SERVICES STRUCTURE
    // Backend Elements
    private Controller controller;

    // Frontend Elements
    @FXML private TextField txtNumberOfPrgStates;
    @FXML private TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddressColumn;
    @FXML private TableColumn<Map.Entry<Integer, Value>, Value> heapValueColumn;
    @FXML private ListView<Value> listOut;
    @FXML private ListView<StringValue> listFileTable;
    @FXML private ListView<Integer> listPrgStateIds;
    @FXML private TableView<Map.Entry<String, Value>> symTable;
    @FXML private TableColumn<Map.Entry<String, Value>, String> symTableVariableNameColumn;
    @FXML private TableColumn<Map.Entry<String, Value>, Value> symTableValueColumn;
    @FXML private ListView<IStmt> listExeStack;
    @FXML private TableView<Map.Entry<String, Pair<MyIList<String>, IStmt>>> procTable;
    @FXML private TableColumn<Map.Entry<String, Pair<MyIList<String>, IStmt>>, String> procTableSignature;
    @FXML private TableColumn<Map.Entry<String, Pair<MyIList<String>, IStmt>>, IStmt> procTableBody;
    @FXML private Button btnRunOneStep;



    // PROGRAM DASHBOARD SERVICES INITIALIZE
    public void initializeDashboard(IStmt programStatement, List<IProcedure> procedureList, String logFilePath) throws MyStackException, MyDictionaryException {

        MyIStack<ISymTable> symTableMyIStack = new MyStack<>();
        symTableMyIStack.push(new SymTable());

        IProcTable procTable = new ProcTable();
        for(IProcedure proc : procedureList){
            procTable.put(proc.getName(), new Pair<>(proc.getParameters(), proc.getStmt()));
        }

        // Creating program & controller
        PrgState prgState = new PrgState(new ExeStack(), symTableMyIStack, new OutList(), new FileTable(), new Heap(), programStatement, procTable);
        this.controller = new BasicController(1, logFilePath, prgState);

        // Update View
        updateDashboard();
        updateProcTable();

        // Event Listeners
        listPrgStateIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateExecStack();
            try {
                updateSymTable();
            } catch (MyStackException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void updateDashboard() {

        // Updating all elements of the screen
        updateProgramStateCount();
        updateHeapTable();
        updateOutList();
        updateFileList();
        updateIdentifierList();
        updateExecStack();
        try {
            updateSymTable();
        } catch (MyStackException e) {
            throw new RuntimeException(e);
        }
    }


    // PROGRAM DASHBOARD SERVICES HELPER METHODS
    // Update Program State List Count
    private void updateProgramStateCount() {
        txtNumberOfPrgStates.setText(controller.getPrgListCount().toString());
    }

    // Update Proc Table entries
    private void updateProcTable() {
        ObservableList<Map.Entry<String, Pair<MyIList<String>, IStmt>>> procTableData = FXCollections.observableArrayList(
                controller.getPrgList().getFirst().getProcTable().getContent().entrySet()
        );

        procTableSignature.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey() + data.getValue().getValue().getKey().toString()));
        procTableBody.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValue().getValue()));
        procTable.setItems(procTableData);
        procTable.refresh();
    }

    // Update Heap Table entries
    private void updateHeapTable() {
        ObservableList<Map.Entry<Integer, Value>> heapData = FXCollections.observableArrayList(
                controller.getPrgList().getFirst().getHeap().getContent().entrySet()
        );

        heapAddressColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey()));
        heapValueColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValue()));
        heapTable.setItems(heapData);
    }

    // Update Output List entries
    private void updateOutList() {
        ObservableList<Value> outputListData = FXCollections.observableArrayList(controller.getPrgList().getFirst().getOutputList().getContent());

        listOut.setItems(outputListData);
    }

    // Update File List entries
    private void updateFileList() {
        ObservableList<StringValue> fileTableList = FXCollections.observableArrayList(controller.getPrgList().getFirst().getFileTable().keySet());

        listFileTable.setItems(fileTableList);
    }

    // Update Program State Identifier List entries
    private void updateIdentifierList() {
        ObservableList<Integer> identifierList = FXCollections.observableArrayList();
        controller.getPrgList().forEach(prgState -> identifierList.add(prgState.getProgramID()));

        listPrgStateIds.setItems(identifierList);
    }

    // Update Symbols Table entries
    private void updateSymTable() throws MyStackException {
        int selectedIndex = listPrgStateIds.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex > this.controller.getPrgListCount()) {
            return;
        }

        ObservableList<Map.Entry<String, Value>> symTableData = FXCollections.observableArrayList(
                controller.getPrgList().get(selectedIndex).getSymbolsTable().getContent().getContent().entrySet()
        );

        symTableVariableNameColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey()));
        symTableValueColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValue()));
        symTable.setItems(symTableData);
        symTable.refresh();
    }

    // Update Execution Stack entries
    private void updateExecStack() {
        int selectedIndex = listPrgStateIds.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex > this.controller.getPrgListCount()) {
            return;
        }

        ObservableList<IStmt> execStackList = FXCollections.observableArrayList();
        execStackList.addAll(controller.getPrgList().get(selectedIndex).getExecutionStack().toList());

        listExeStack.setItems(execStackList);
    }

    // Show alert
    // Creates & Launches Error Alert
    private void showErrorAlert(String header, String content) {

        // Create a new alert
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Set alert content
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Launch alert
        alert.showAndWait();
    }

    // PROGRAM DASHBOARD SERVICES HANDLERS
    @FXML
    private void handleRunOneStep() {
        try{

            // Run one step for all programs
            this.controller.oneStepForAllPrg();

            // Update dashboard view
            updateDashboard();

        } catch (ControllerException e) {

            // Notify errors
            showErrorAlert("Program One Step Run Failed", e.toString());
        }
    }
}
