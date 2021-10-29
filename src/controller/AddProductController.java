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
 * FXML Controller class that provides control logic for the add product screen of the application.
 *
 * @author Martha Weldeyesus
 */
public class AddProductController implements Initializable {

    /**
     * A list of associatedPart with the product.
     */
    @FXML
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /**
     * Table view for associated part.
     */
    @FXML
    private TableView<Part> assocPartTableView;

    /**
     * Table column for the ID column of associated parts.
     */
    @FXML
    private TableColumn<Part, Integer> assocPartIdColumn;

    /**
     * Table column for the name column of associated parts.
     */
    @FXML
    private TableColumn<Part, String> assocPartNameColumn;

    /**
     * Table column for the inventory column of associated parts.
     */
    @FXML
    private TableColumn<Part, Integer> assocPartInventoryColumn;

    /**
     * Table column for the price column of associated parts.
     */
    @FXML
    private TableColumn<Part, Double> assocPartPriceColumn;

    /**
     * Table view for all parts.
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * Table column for ID column of all parts.
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * Table column for the name column of all parts.
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * Table column for the inventory level column of all parts.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    /**
     * Table column for the price column of the all parts.
     */
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    /**
     * Text field for the part search.
     */
    @FXML
    private TextField partSearchField;

    /**
     * Text field for the product name.
     */
    @FXML
    private TextField productNameField;

    /**
     * Text field for the product inventory level.
     */
    @FXML
    private TextField productInventoryField;

    /**
     * Text field the product price.
     */
    @FXML
    private TextField productPriceField;

    /**
     * Text field for the product maximum level.
     */
    @FXML
    private TextField productMaxField;

    /**
     * Text field for the product minimum level.
     */
    @FXML
    private TextField productMinField;
    
    
    
    /**
     * check that min is greater than 0 and less than max.
     *
     * @param min 
     * @param max 
     * @return Boolean check if min is valid.
     */
    private boolean minCheck(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            AlartMessage.displayAlertAdd(3);
        }

        return isValid;
    }

    /**
     * check that inventory level is equal too or between min and max.
     *
     * @param min 
     * @param max 
     * @param stock 
     * @return Boolean check if inventory is valid.
     */
    private boolean inventoryCheck(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            AlartMessage.displayAlertAdd(4);
        }

        return isValid;
    }

    /**
     * Search Parts by ID or name and table view with search results.
     *
     * @param event Part search button action.
     */
    @FXML
    void partSearchHandler(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchField.getText();

        allParts.stream().filter((part) -> (String.valueOf(part.getId()).contains(searchString) ||
                part.getName().contains(searchString))).forEachOrdered((part) -> {
                    partsFound.add(part);
        });

        partTableView.setItems(partsFound);

        if (partsFound.isEmpty()) {
            AlartMessage.displayAlertAdd(7);
        }
    }

    /**
     * Refreshes part table view to show all parts when parts search text field is empty.
     * 
     * @param event Parts search text field key pressed.
     */
    @FXML
    void RefresheSearchPart(KeyEvent event) {

        if (partSearchField.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }
    /**
     * Adds selected part into the associated parts table.
     *
     * check if no part is selected and display error message.
     *
     * @param event Add button action.
     */
    @FXML
    void addToAssociatedPart(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            AlartMessage.displayAlertAdd(6);
        } else {
            assocParts.add(selectedPart);
            assocPartTableView.setItems(assocParts);
        }
    }    
    
     /**
     * Removes selected part from associated parts table and display error message if no selected.
     *
     * @param event Remove button action.
     */
    @FXML
    void removeAssociatedPart(ActionEvent event) {

        Part selectedPart = assocPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            AlartMessage.displayAlertAdd(6);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                assocPartTableView.setItems(assocParts);
            }
        }
    }
    
     /**
     * Adds new part to MainScreenController by using the inventory.
     *
     * check the text fields if there is empty or invalid values.
     *
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void saveAddProduct(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = productNameField.getText();
            Double price = Double.parseDouble(productPriceField.getText());
            int stock = Integer.parseInt(productInventoryField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());

            if (name.isEmpty()) {
                AlartMessage.displayAlertAdd(5);
            } else {
                if (minCheck(min, max) && inventoryCheck(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    assocParts.forEach((part) -> {
                        newProduct.addAssociatedPart(part);
                    });

                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    mainScreen(event);
                }
            }
        } catch (IOException | NumberFormatException e){
            AlartMessage.displayAlertAdd(1);
        }
    }
  
     /**
     * Return to MainScreen page.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void cancelAddProduct(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            mainScreen(event);
        }
    }
    
    /**
     * Loads MainScreenController.
     *
     * @param event Passed from parent method.
     * @throws IOException From FXMLLoader.
     */
    private void mainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Initializes the controller class and populates tableView.
      * @param url 
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
