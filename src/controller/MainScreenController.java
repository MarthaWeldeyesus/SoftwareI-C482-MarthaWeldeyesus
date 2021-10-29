package controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

/**
 * FXML Controller class that provides logic for the main screen of the application 
 *
 * @author Martha Weldeyesus
 */
public class MainScreenController implements Initializable {

    /**
     * Selected part table view by the user.
     */
    @FXML
    private static Part partToModify;
  
     /**
      * Text field for the part search.
     */
    @FXML
    private TextField partSearchField;
    
    /**
     * Table view for the parts.
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * The ID column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * The part name column for the parts table.
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * The inventory column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    /**
     * The price column for parts table.
     */
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    
       /**
     * the product object selected in the table view by the user.
     */
    @FXML
    private static Product productToModify;
    /**
     * Text field for the product search.
     */
    @FXML
    private TextField productSearchField;

    /**
     * Table view for the products.
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * The ID column for the product table.
     */
    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    /**
     * The name column for the product table.
     */
    @FXML
    private TableColumn<Product, String> productNameColumn;

    /**
     * The inventory column for the product table.
     */
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;

    /**
     * The price column for the product table.
     */
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    
    /**
     * Getter for part partToModify. 
     * @return A part object, null if no part selected.
     */
    @FXML
    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * Getter for ProductToModify 
     * @return A product object, null if no product selected.
     */
    @FXML
    public static Product getProductToModify() {
        return productToModify;
    }
    
    /**
     * Loads the AddPartController.
     * 
     * @param event add a button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML 
    void partAddButton(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();            
    }
    
    /**loads the ModifyPartController.
     * 
     * Displays an error message if no part is selected 
     * 
     * @param event Part modify button action.
     * @throws IOEexception from FXMLoader.
     */
    @FXML
    void partModifyButton(ActionEvent event) throws IOException{
        partToModify = partTableView.getSelectionModel().getSelectedItem();
       if(partToModify == null){
           AlartMessage.displayMainScreenAlert(3);
       }else {
           Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
             Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
       }
    }
    /**
     * Remove the Selected part by the user from the part table.
     * Displays an error message if no part is selected.
     * @param event Part delete button action.
     
     */
    @FXML
    void partDeleteButton(ActionEvent event){
         Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            AlartMessage.displayMainScreenAlert(3);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("are you sure you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                
                Inventory.deletePart(selectedPart);
            }
        }
    }
    /** Search parts by Id/Name and display the result on table view.
     * 
     * @param event Part search button action.
     */
   
        @FXML
    void partSearchButton(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchField.getText();

        allParts.stream().filter((part) -> (String.valueOf(part.getId()).contains(searchString) ||
                part.getName().contains(searchString))).forEachOrdered((part) -> {
                    partsFound.add(part);
        });

        partTableView.setItems(partsFound);

        if (partsFound.isEmpty()) {
            AlartMessage.displayMainScreenAlert(1);
        }
    }
    
       /**
     * Renew part table view to show all parts when parts search text field is empty.
     *
     * @param event Parts search text field key pressed.
     */
    @FXML
    void RefresheSearchPart(KeyEvent event){
        if(partSearchField.getText().isEmpty()){
            partTableView.setItems(Inventory.getAllParts());
        }
    }
    
    /** Loads AddProductController.
     * 
     * @param event add product button action.
     * @throws IOException from FXMLoader.
     */
    @FXML 
     void productAddButton(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();  
    }
     
      /**
     * Loads the ModifyProductController.
     *
     * Displays an error message if no product is selected.
     *
     * @param event Product modify button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void productModifyButton(ActionEvent event) throws IOException {

        productToModify = productTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            AlartMessage.displayMainScreenAlert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
      /**
     * Remove the selected product by the user from the product table.
     *
     * Displays an error message if no product is selected.
     * Displays a message before deleting the selected product and prevents user from deleting
     * a product with one or more associated parts.
     *
     * @param event Product delete button action.
     */
    @FXML
    void productDeleteButton(ActionEvent event) {

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            AlartMessage.displayMainScreenAlert(4);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();

                if (assocParts.size() >= 1) {
                    AlartMessage.displayMainScreenAlert(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }
    
     /**
     * Search Product by Id/Name and display the result on table view.
     *
     * @param event Part search button action.
     */
    @FXML
    void productSearchButton(ActionEvent event) {

         ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productSearchField.getText();

        allProducts.stream().filter((product) -> (String.valueOf(product.getId()).contains(searchString) ||
                product.getName().contains(searchString))).forEachOrdered((product) -> {
                    productsFound.add(product);
        });

        productTableView.setItems(productsFound);

        if (productsFound.isEmpty()) {
            AlartMessage.displayMainScreenAlert(2);
        }
        
    }
     /**
     * Renew product table view to show all products when products search text field is empty.
     *
     * @param event Products search text field key pressed.
     */
    @FXML
    void RefresheSearchProduct(KeyEvent event) {

        if (productSearchField.getText().isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
        }
    }

     /**
     * Exits the program.
     *
     * @param event Exit button action.
     */
    @FXML
    void exitButton(ActionEvent event) {

        System.exit(0);
    }
     /**
     * Initializes controller and populates table views.
     *
     * @param url 
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTableView.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }        
}
