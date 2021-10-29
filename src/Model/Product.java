package Model;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;

/**
 *
 * @author Martha Weldeyesus
 */
public class Product {
    /**
     * The ID for the product
     */
    private int id;

    /**
     * The name of the product
     */
    private String name;

    /**
     * The price of the product
     */
    private double price;

    /**
     * The inventory level of the product
     */
    private int stock;

    /**
     * The minimum level for the product
     */
    private int min;

    /**
     * The maximum level for the product
     */
    private int max;

    /**
     * A list of associated parts for the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for a new instance of a product
     *
     * @param id the ID for the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the inventory level of the product
     * @param min the minimum level for the product
     * @param max the maximum level for the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for ID
     * @return id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for ID
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for Name
     * The getter for the name
     *
     * @return name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Name 
     * @param name the name to set 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter Price 
     * @return price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for Price 
     * @param price the price to set 
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for Stock 
     * @return the stock of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for Stock 
     * @param stock The inventory level to set 
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for Min
     * @return the min of the product
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for Min
     * @param min the minimum level to set 
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for Max
     * @return the max of the product
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for Max
     * @param max the maximum level to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the associated parts list for the product.
     *
     * @param part the part to add
     */
    public void  addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from the associated parts list for the product.
     *
     * @param selectedAssociatedPart the part to delete
     * @return true/false indicating status of part deletion
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * Getter for All Associate Parts 
     * @return a list of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}
}
