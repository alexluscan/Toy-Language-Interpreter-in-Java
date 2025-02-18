////////////////////////
// PACKAGES & IMPORTS //
////////////////////////
package map.interpreter.interpreter.view;

import map.interpreter.interpreter.view.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


//////////////////////////
// CLASS IMPLEMENTATION //
//////////////////////////
public class TextMenu {

    // TEXT MENU STRUCTURE
    // Text Menu is based on a list of commands
    private Map<String, Command> commands;

    // TEXT MENU CONSTRUCTOR
    public TextMenu() {
        commands = new HashMap<>();
    }

    // TEXT MENU METHODS
    // Add a new command to the text menu
    public void addCommand(Command c){commands.put(c.getKey(), c); }

    // Print the current menu
    private void printMenu() {
        for(Command command: commands.values()) {
            String line = String.format("%4s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    // Show the menu
    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            TextMenu.clearScreen();
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if(command == null){
                System.out.println("Invalid Option");
                continue;
            }
            command.execute();
            TextMenu.proceedEnter();
        }
    }

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
}
