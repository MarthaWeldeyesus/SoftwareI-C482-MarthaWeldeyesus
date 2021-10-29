package softwarei.c482.marthaweldeyesus;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

  // submited the Javadoc folder as a second zip file.
/**
 * Main Class
 * @author Martha Weldeyesus
 */
public class Main extends Application{
   
    /** The main method is the entry point of the application. 
     * The main method creates sample data and launches the application.
     * 
     * @param args the command line arguments.
     
     */
    public static void main(String[] args) {

        //Add parts
        int partId = Inventory.getNewPartId();
        InHouse part1 = new InHouse(partId,"Brakes", 15.99, 10, 1, 10, 100);
        partId = Inventory.getNewPartId();
        InHouse part2 = new InHouse(partId,"Wheel", 11.99, 16, 1, 20, 100);
        partId = Inventory.getNewPartId();       
        OutSourced part3 = new OutSourced(partId, "Seat", 15.99, 10, 1,
                100, "Volkswagen Group");
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
 
         //Add product
        int productId = Inventory.getNewProductId();
        Product product1 = new Product(productId, "Glant Bike", 299.99, 5, 2,
                100);
        productId = Inventory.getNewProductId();
        Product product2 = new Product(productId, "Tricycle", 99.99, 5, 2, 100);
        
        //Associate the product
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
       
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
       launch(args);
    }
     /** The start method creates the FXML stage and loads the initial scene. 
     * @param stage
     * @throws Exception
     */

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
}
