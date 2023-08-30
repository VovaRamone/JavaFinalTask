package View;

import Model.Toy;
import Presenter.ToyPresenter;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based view class that interacts with the user and displays the menu.
 */
public class ConsoleView {
    private final ToyPresenter toyPresenter;
    private final Scanner scanner;

    /**
     * Creates a new ConsoleView instance.
     *
     * @param toyPresenter The ToyPresenter instance to interact with.
     */
    public ConsoleView(ToyPresenter toyPresenter) {
        this.toyPresenter = toyPresenter;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Executes the main loop of the program.
     * Continuously displays the main menu, collects user input for actions,
     * and performs corresponding actions based on the user's choice.
     */
    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserInputInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    addToy();
                    break;
                case 2:
                    changeDropRate();
                    break;
                case 3:
                    toyPresenter.drawPrizeToy();
                    break;
                case 4:
                    displayToys(toyPresenter.getAllToys());
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu of the program, showing available actions to the user.
     * The menu includes options to add a toy, change the drop rate, draw a prize toy,
     * display the list of toys, and exit the program.
     */
    private void displayMenu() {
        System.out.println("Welcome to the Children's Goods Store!");
        System.out.println("1. Add Toy");
        System.out.println("2. Change Drop Rate");
        System.out.println("3. Draw Prize Toy");
        System.out.println("4. Display List of Toys"); // Added option
        System.out.println("5. Exit");
    }

    /**
     * Prompts the user to input details for adding a new toy.
     * Collects the toy's name, quantity, and drop rate from the user,
     * generates the next available toy ID, and adds the toy to the presenter.
     * Displays a success message after adding the toy.
     */
    private void addToy() {
        System.out.println("Adding a new toy:");
        String name = getUserInputString("Enter toy name: ");
        int quantity = getUserInputInt("Enter quantity: ");
        double dropRate = getUserInputDouble("Enter drop rate (%): ");

        int id = toyPresenter.getNextToyId(); // Get the next available toy ID
        toyPresenter.addToy(id, name, quantity, dropRate);
        System.out.println("Toy added successfully!");
    }

    /**
     * Prompts the user to input a new drop rate for a specific toy.
     * Takes the toy's ID and the new drop rate from the user,
     * updates the drop rate of the corresponding toy, and displays a success message.
     */
    private void changeDropRate() {
        System.out.println("Changing drop rate:");
        int toyId = getUserInputInt("Enter toy ID: ");
        double newDropRate = getUserInputDouble("Enter new drop rate (%): ");

        toyPresenter.changeDropRate(toyId, newDropRate);
        System.out.println("Drop rate changed successfully!");
    }

    /**
     * Prompts the user to input an integer value.
     * Repeatedly asks for input until a valid integer is provided.
     *
     * @param message The message to display before asking for input.
     * @return The valid integer input provided by the user.
     */
    private int getUserInputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Prompts the user to input a double value.
     * Repeatedly asks for input until a valid number is provided.
     *
     * @param message The message to display before asking for input.
     * @return The valid double input provided by the user.
     */
    private double getUserInputDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user to input a string.
     *
     * @param message The message to display before asking for input.
     * @return The string input provided by the user.
     */
    private String getUserInputString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Displays the list of toys along with their details.
     *
     * @param toys The list of toys to display.
     */
    private void displayToys(List<Toy> toys) {
        System.out.println("List of toys:");
        for (Toy toy : toys) {
            System.out.println("ID: " + toy.getId() +
                    ", Name: " + toy.getName() +
                    ", Quantity: " + toy.getQuantity() +
                    ", Drop Rate: " + toy.getDropRate() + "%");
        }
    }
}
