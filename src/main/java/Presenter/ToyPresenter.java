package Presenter;

import Model.Toy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Presenter class that handles toy data and drawing prize toys.
 */
public class ToyPresenter {
    private static final String FILE_PATH = "toys.json";
    private final ObjectMapper objectMapper;
    private List<Toy> toys;
    private List<Toy> prizeToys;

    /**
     * Creates a new ToyPresenter instance.
     * Initializes ObjectMapper, loads toys from file, and initializes prize toys.
     */
    public ToyPresenter() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        loadToysFromFile();
        initializePrizeToys();
    }

    /**
     * Initializes the list of prize toys based on the drop rates of available toys.
     * For each toy, calculates the number of prize toys based on drop rate and quantity,
     * and adds them to the list of prize toys. Shuffles the list for random selection.
     */
    private void initializePrizeToys() {
        prizeToys = new ArrayList<>();

        for (Toy toy : toys) {
            int numberOfPrizeToys = (int) Math.round((toy.getDropRate() / 100) * toy.getQuantity());
            for (int i = 0; i < numberOfPrizeToys; i++) {
                prizeToys.add(toy);
            }
        }

        Collections.shuffle(prizeToys);
    }

    /**
     * Loads toy data from the JSON file.
     * If the file exists and is not empty, deserializes the JSON data into the toys list.
     * If the file doesn't exist or is empty, initializes the toys list as an empty ArrayList.
     */
    private void loadToysFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists() && file.length() > 0) {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Toy.class);
                toys = objectMapper.readValue(file, listType);
            } else {
                toys = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            toys = new ArrayList<>();
        }
    }

    /**
     * Saves the current list of toys to the JSON file.
     * Serializes the toys list into JSON format and writes it to the file.
     */
    private void saveToysToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), toys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new toy to the collection of toys with the provided details.
     * The toy is constructed using the provided ID, name, quantity, and drop rate.
     * The toy is then added to the toys list and the list is saved to the JSON file.
     *
     * @param id The ID of the toy.
     * @param name The name of the toy.
     * @param quantity The quantity of the toy.
     * @param dropRate The drop rate of the toy.
     */
    public void addToy(int id, String name, int quantity, double dropRate) {
        Toy toy = new Toy(id, name, quantity, dropRate);
        toys.add(toy);
        saveToysToFile();
    }

    /**
     * Gets the next available ID for a new toy.
     * Calculates the maximum ID among existing toys and returns the next ID.
     *
     * @return The next available toy ID.
     */
    public int getNextToyId() {
        int maxId = 0;
        for (Toy toy : toys) {
            maxId = Math.max(maxId, toy.getId());
        }
        return maxId + 1;
    }

    /**
     * Changes the drop rate of a specific toy based on its ID.
     * Searches for the toy with the provided ID and updates its drop rate.
     * The updated list of toys is then saved to the JSON file.
     *
     * @param toyId The ID of the toy whose drop rate is to be changed.
     * @param newDropRate The new drop rate to be set.
     */
    public void changeDropRate(int toyId, double newDropRate) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setDropRate(newDropRate);
                break;
            }
        }
        saveToysToFile();
    }

    /**
     * Retrieves a list of all available toys.
     *
     * @return The list of all available toys.
     */
    public List<Toy> getAllToys() {
        return toys;
    }

    /**
     * Draws a prize toy from the list of prize toys based on their drop rates.
     * Randomly selects a toy from the list using their drop rates as probabilities.
     * The selected toy is removed from the list, and the updated list is saved to the JSON file.
     * Displays a congratulatory message or a message indicating no prize toys are available.
     */
    public void drawPrizeToy() {
        if (!prizeToys.isEmpty()) {
            double totalDropRate = prizeToys.stream().mapToDouble(Toy::getDropRate).sum();
            double randomValue = Math.random() * totalDropRate;

            Toy selectedToy = null;
            for (Toy toy : prizeToys) {
                randomValue -= toy.getDropRate();
                if (randomValue <= 0) {
                    selectedToy = toy;
                    break;
                }
            }

            if (selectedToy != null) {
                prizeToys.remove(selectedToy);
                saveToysToFile();
                System.out.println("Congratulations! You won a " + selectedToy.getName() + "!");
            }
        } else {
            System.out.println("No prize toys available.");
        }
    }
}
