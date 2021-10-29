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
 * FXML Controller class that provides control logic for the modify product screen of the application.
 *
 * @author Martha Weldeyesus
 */
public class ModifyProductController implements Initializable {

     /**
     * Selected product from the MainScreenController.
     */
    Product selectedProduct;

    /**
     * list of all associate part with the product.
     */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /**
     * Table view for associated part.
     */
    @FXML
    private TableView<Part> assocPartTableView;

    /**
     * Part ID column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, Integer> assocPartIdColumn;

    /**
     * Part name column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, String> assocPartNameColumn;

    /**
     * Inventory level column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, Integer> assocPartInventoryColumn;

    /**
     * Part price column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, Double> assocPartPriceColumn;

    /**
     * All parts table view.
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * Part ID column for all parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * Part name column for the all parts table.
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * Inventory level column for the all parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    /**
     * Part price column for the all parts table.
     */
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    /**
     * Text field for part search.
     */
    @FXML
    private TextField partSearchText;

    /**
     * Text field for product ID.
     */
    @FXML
    private TextField productIdText;

    /**
     * Text field for product name text field.
     */
    @FXML
    private TextField productNameText;

    /**
     * Text field for product inventory level.
     */
    @FXML
    private TextField productInventoryText;

    /**
     * Text field for product price.
     */
    @FXML
    private TextField productPriceText;

    /**
     * Text field for product maximum level.
     */
    @FXML
    private TextField productMaxText;

    /**
     * Text field for product minimum level.
     */
    @FXML
    private TextField productMinText;
    
  /**
     * Check that min is greater than 0 and less than max.
     *
     * @param min 
     * @param max 
     * @return Boolean check if min is valid.
     */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            AlartMessage.displayAlertAdd(3);
        }

        return isValid;
    }

    /**
     * Check that inventory level is equal too or between min and max.
     *
     * @param min 
     * @param max 
     * @param stock 
     * @return Boolean check if inventory is valid.
     */
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            AlartMessage.displayAlertAdd(4);
        }

        return isValid;
    }
     /**
     * Adds selected part into the all associated parts table.
     *
     * Displays error message if no part is selected.
     *
     * @param event Add button action.
     */
    @FXML
    void addButton(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            AlartMessage.displayAlertAdd(6);
        } else {
            assocParts.add(selectedPart);
            assocPartTableView.setItems(assocParts);
        }
    }
  /**
     * Search parts by Id/Name and display the result on table view.
     *
     * @param event Part search button action.
     */
    @FXML
    void partSearchButton(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchText.getText();

        allParts.stream().filter((part) -> (String.valueOf(part.getId()).contains(searchString) ||
                part.getName().contains(searchString))).forEachOrdered((part) -> {
                    partsFound.add(part);
        });

        partTableView.setItems(partsFound);

        if (partsFound.isEmpty()) {
            AlartMessage.displayAlertAdd(1);
        }
    }
     /**
     * Renew part table view to show all parts when parts search text field is empty.
     *
     * @param event Parts search text field key pressed.
     */
    @FXML
    void RefresheSearchPart(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }

     /**
     * Displays message and removes selected part from associated parts table.
     *
     * Displays error message if no part is selected.
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
     * Replaces product in inventory and loads MainScreenController.
     *
     * Displayed error messages if empty and/or invalid values.
     *
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void saveModifyProductButton(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
            String name = productNameText.getText();
            Double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productInventoryText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if (name.isEmpty()) {
                AlartMessage.displayAlertAdd(5);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    assocParts.forEach((part) -> {
                        newProduct.addAssociatedPart(part);
                    });

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    mainScreen(event);
                }
            }
        } catch (IOException | NumberFormatException e){
            AlartMessage.displayAlertAdd(1);
        }
    }
     /**
     * Displays message and loads MainScreenController.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void cancelModifyProductButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want cancel changes and return to the main screen?");
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
     * Initializes the controller class  and populates text fields with product selected in MainScreenController.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      selectedProduct = MainScreenController.getProductToModify();
        assocParts = selectedProduct.getAllAssociatedParts();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartTableView.setItems(assocParts);

        productIdText.setText(String.valueOf(selectedProduct.getId()));
        productNameText.setText(selectedProduct.getName());
        productInventoryText.setText(String.valueOf(selectedProduct.getStock()));
        productPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxText.setText(String.valueOf(selectedProduct.getMax()));
        productMinText.setText(String.valueOf(selectedProduct.getMin()));
    }    
    
}
