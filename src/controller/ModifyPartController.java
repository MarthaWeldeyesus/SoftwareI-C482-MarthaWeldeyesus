package controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

/**
 * FXML Controller class that provides control logic for the modify part screen of the application.
 *
 * @author Martha Weldeyesus
 */
public class ModifyPartController implements Initializable {

    /**
     * Selected part from the MainScreenController.
     */
    private Part selectedPart;

    /**
     * machine ID/company name label for the part.
     */
    @FXML
    private Label partIdNameLabel;

    /**
     * radio button for in-house.
     */
    @FXML
    private RadioButton inHouseRadioButton;

    /**
     * Radio button for outsourced.
     */
    @FXML
    private RadioButton outsourcedRadioButton;

    /**
     * Text field for part ID.
     */
    @FXML
    private TextField partIdText;

    /**
     * Text field for part name.
     */
    @FXML
    private TextField partNameText;

    /**
     * Text field  inventory level.
     */
    @FXML
    private TextField partInventoryText;

    /**
     * Text field for part price.
     */
    @FXML
    private TextField partPriceText;

    /**
     * Text field for maximum level.
     */
    @FXML
    private TextField partMaxText;

    /**
     * Text field machine ID/company name.
     */
    @FXML
    private TextField partIdNameText;

    /**
     * Text field for minimum level.
     */
    @FXML
    private TextField partMinText;  
    
     /**
     * Sets machine ID/company name label to "Machine ID".
     *
     * @param event In-house radio button action.
     */
    @FXML
    void inHouseRadioButtonModifyPart(ActionEvent event) {

        partIdNameLabel.setText("Machine ID");
    }

    /**
     * Sets machine ID/company name label to "Company Name".
     *
     * @param event Outsourced radio button.
     */
    @FXML
    void outsourcedRadioButtonModifyPart(ActionEvent event) {

        partIdNameLabel.setText("Company Name");
    }
     /**
     * check that min is greater than 0 and less than max.
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
     * check that inventory level is equal too or between min and max.
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
     * Replaces part in inventory and loads MainScreenController.
     *
     * displayed error messages if text field empty and/or invalid values.
     *
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (minValid(min, max) && inventoryValid(min, max, stock)) {

                if (inHouseRadioButton.isSelected()) {
                    try {
                        machineId = Integer.parseInt(partIdNameText.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAddSuccessful = true;
                    } catch (NumberFormatException e) {
                        AlartMessage.displayAlertAdd(2);
                    }
                }

                if (outsourcedRadioButton.isSelected()) {
                    companyName = partIdNameText.getText();
                    OutSourced newOutsourcedPart = new OutSourced(id, name, price, stock, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAddSuccessful = true;
                }

                if (partAddSuccessful) {
                    Inventory.deletePart(selectedPart);
                    mainScreen(event);
                }
            }
        } catch(IOException | NumberFormatException e) {
            AlartMessage.displayAlertAdd(1);
        }
    } 
    
 /**
     * Displays confirmation dialog and loads MainScreenController.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

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
     * Initializes the controller class and populates text fields with part selected in MainScreenController.
     * @param url 
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
         selectedPart = MainScreenController.getPartToModify();

        if (selectedPart instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            partIdNameLabel.setText("Machine ID");
            partIdNameText.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof OutSourced){
            outsourcedRadioButton.setSelected(true);
            partIdNameLabel.setText("Company Name");
            partIdNameText.setText(((OutSourced) selectedPart).getCompanyName());
        }

        partIdText.setText(String.valueOf(selectedPart.getId()));
        partNameText.setText(selectedPart.getName());
        partInventoryText.setText(String.valueOf(selectedPart.getStock()));
        partPriceText.setText(String.valueOf(selectedPart.getPrice()));
        partMaxText.setText(String.valueOf(selectedPart.getMax()));
        partMinText.setText(String.valueOf(selectedPart.getMin()));

    }
}
