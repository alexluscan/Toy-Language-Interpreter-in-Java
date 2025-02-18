package map.interpreter.interpreter.view;
/**
////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package view;
import controller.BasicController;
import controller.Controller;
import controller.ControllerException;
import domain.PrgState;
import domain.datastructures.dictionary.*;
import domain.datastructures.list.*;
import domain.datastructures.stack.*;
import domain.expressions.*;
import domain.state.*;
import domain.statements.*;
import domain.values.*;
import domain.types.*;
import repository.RepositoryException;

import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.util.Scanner;

//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class UserInterface {

    // USER INTERFACE STRUCTURE
    Controller controller;


    // USER INTERFACE CONSTRUCTORS
    public UserInterface() {
        // Welcome Screen
        this.welcomeScreen();

        // Controller Setup
        this.setupScreen();
    }

    // USER INTERFACE METHODS
    // User Experience Methods - CLI Specific
    // Simulates clearing the console
    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }
    // Simulates proceeding on pressing enter
    public static void proceedEnter() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    // User Interface Views ( Screens )
    // Welcome Screen
    public void welcomeScreen() {
        clearScreen();
        System.out.println("Welcome to the Toy Language Interpreter Application");
        System.out.println("Made by Antonio Hus, Group 924/1");
        System.out.println();
        System.out.println();
        System.out.println("Press 'Enter' to proceed");
        proceedEnter();
    }

    public void setupScreen() {

        // Get desired flag configurations
        Scanner scanner = new Scanner(System.in);
        int flagConfiguration;

        while (true) {
            try {
                clearScreen();
                System.out.println("Let's setup the Interpreter Configurations");
                System.out.println("Please enter flags configuration options ID: ");
                System.out.println("0. Display Mode OFF");
                System.out.println("1. Display Mode ON");

                // Get the input and parse it as an integer
                String input = scanner.nextLine();
                flagConfiguration = Integer.parseInt(input); // Attempt to convert input to an integer

                if (flagConfiguration < 0 || flagConfiguration > 1) { // Check if capacity is in range
                    throw new NumberFormatException("Invalid flag configuration. Please try again.");
                }

                break; // Exit the loop if valid integer is entered
            } catch (NumberFormatException exc) { // Handle invalid integer input
                System.out.println("Invalid input. Please enter a valid integer: " + exc);
                proceedEnter();
            }
        }

        // Initialize controller for requested parking lot
        this.controller = new BasicController(flagConfiguration, "C:\\Users\\anton\\OneDrive\\Documents\\GitHub\\Advanced-Programming-Methods\\Interpreter\\logs\\programState3.log");
        clearScreen();
    }

    public int menuScreen() {
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            try {

                // Display User Options
                System.out.println("1. Input a Program");
                System.out.println("2. Execute a Program (One Step)");
                System.out.println("3. Execute a Program (All Steps)");
                System.out.println("0. Exit");
                System.out.println();

                // Get user option
                System.out.print("Please choose an option: ");
                option = scanner.nextInt();

                // Redirect based on the option
                switch (option) {
                    case 1: inputProgramScreen(); break;
                    case 2: executeProgramScreen(0); break;// Execute only one step
                    case 3: executeProgramScreen(1); break;// Execute all steps
                    case 0:
                        System.out.println("Exiting...");
                        return 0; // Exit the menu
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input by consuming the entire line
                proceedEnter();
            }
        }
    }

    public int programsListView() {
        clearScreen();
        System.out.println("Programs running:\n");
        System.out.println("-------------------------------------------");
        int count = 0;
        try {
            MyIList<PrgState> programs = this.controller.getPrgStates();
            for (int i = 0; i < programs.size(); ++i) {
                System.out.println(i + ".\n" + programs.get(i) + "\n");
                count += 1;
            }

            if(count == 0) {
                System.out.println("There are no current programs to show!");
            }

        } catch (MyListException exception) {
            System.out.println("There was an error parsing the programs: " + exception);
        }
        System.out.println("-------------------------------------------\n\n");
        return count;
    }

    public void inputProgramScreen() {
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            try {
                System.out.println("IN DEVELOPMENT - Please choose one of the hardcoded examples");
                System.out.println();

                System.out.println("Example 1:");
                System.out.println("int v;\nv=2;\nprint(v);");
                System.out.println();

                System.out.println("Example 2:");
                System.out.println("int a;\nint b;\na=2+3*5;\nb=a+1;\nprint(b);");
                System.out.println();

                System.out.println("Example 3:");
                System.out.println("bool a;\nint v;\na=true;\n(if a then v=2 else v=3);\nprint(v);");
                System.out.println();

                System.out.println("Example 4:");
                System.out.println("string varf;\nvarf=test.in\nopenRFile(varf);\nint varc;\nreadFile(varf, varc);\nprint(varc);\nreadFile(varf, varc);\nprint(varc);\ncloseRFile(varf);");
                System.out.println();

                // Get user option
                System.out.print("Please choose an example to add: ");
                option = scanner.nextInt();

                // Add new programs
                ExeStack stack = new ExeStack();
                SymTable symbolsTable = new SymTable();
                OutList out = new OutList();
                FileTable fileTable = new FileTable();
                IStmt initialStatement;
                switch (option) {
                    case 1:
                        initialStatement = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
                        this.controller.addPrgState(new PrgState(stack, symbolsTable, out, fileTable, initialStatement));
                        break;
                    case 2:
                        initialStatement = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()), new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3), 1)), new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), 1)), new PrintStmt(new VarExp("b"))))));
                        this.controller.addPrgState(new PrgState(stack, symbolsTable, out, fileTable, initialStatement));
                        break;
                    case 3:
                        initialStatement = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
                        this.controller.addPrgState(new PrgState(stack, symbolsTable, out, fileTable, initialStatement));
                        break;
                    case 4:
                        initialStatement = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("C:\\Users\\anton\\OneDrive\\Documents\\GitHub\\Advanced-Programming-Methods\\Interpreter\\files\\test1.in"))), new CompStmt(new OpenRFileStmt(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf"))))))))));
                        this.controller.addPrgState(new PrgState(stack, symbolsTable, out, fileTable, initialStatement));
                        break;
                    default: throw new InputMismatchException();
                }
                clearScreen();
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input by consuming the entire line
                proceedEnter();
            }
        }
    }

    public void executeProgramScreen(int option) {
        int programsCount = programsListView();

        if(programsCount == 0){
            System.out.println("There are no programs to run!");
            proceedEnter();
            return;
        }

        // TODO: Add option to choose what program to run

        try {
            if(option == 0) {
                this.controller.oneStep();
            }
            else if(option == 1){
                this.controller.allStep();
            }

            if(this.controller.getCrtPrg(this.controller.getPrgStates().size() - 1).getExecutionStack().isEmpty())
                this.controller.removePrgState(this.controller.getPrgStates().size() - 1);

        }catch (MyStackException | ControllerException | StmtException | ExpException | MyDictionaryException | MyListException |
        RepositoryException e) {
            System.out.println("There was an error executing the last program from the execution list: " + e);
        }
    }
}
**/