package Model;
/**
* Supplied class Part.java 
 */
/**
 * class Part.
 * @author Martha Weldeyesus
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;  
       public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
     
      /**
       * Getter for ID
     * @return the id
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
     * @return the name
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
     * Getter for Price
     * @return the price
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
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for Stock
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for Min
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for Min
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for Max
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for Max
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}
