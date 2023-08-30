package Model;

/**
 * Represents a toy in the store with its ID, name, quantity, and drop rate.
 */
public class Toy {
    private int id;
    private String name;
    private int quantity;
    private double dropRate;

    // Default constructor for Jackson deserialization
    public Toy() {

    }

    /**
     * Creates a new Toy instance.
     *
     * @param id        The ID of the toy.
     * @param name      The name of the toy.
     * @param quantity  The quantity of the toy.
     * @param dropRate  The drop rate of the toy.
     */
    public Toy(int id, String name, int quantity, double dropRate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.dropRate = dropRate;
    }

    // Getter methods for toy properties

    /**
     * Returns the ID of the toy.
     *
     * @return The ID of the toy.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the toy.
     *
     * @return The name of the toy.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the quantity of the toy.
     *
     * @return The quantity of the toy.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the drop rate of the toy.
     *
     * @return The drop rate of the toy.
     */
    public double getDropRate() {
        return dropRate;
    }

    /**
     * Sets the drop rate of the toy.
     *
     * @param dropRate The drop rate of the toy.
     */
    public void setDropRate(double dropRate) {
        this.dropRate = dropRate;
    }
}
