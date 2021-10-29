package controller;

import controller.AlartMessage;
import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
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
 * FXML Controller class that provides control logic for the add part screen of the application.
 *
 * @author Martha Weldeyesus
 */
public class AddPartController implements Initializable {

     /**
     * Label for the machine ID/companyName part.
     */
    @FXML
    private Label idCompanyName;

    /**
     * Radio button for in-house.
     */
    @FXML
    private RadioButton inHouseRadioButton;

    /**
     * Radio button for outsourced.
     */
    @FXML
    private RadioButton outsourcedRadioButton;

    /**
     * Text field for part name.
     */
    @FXML
    private TextField partNameText;

    /**
     * Text field for part inventory.
     */
    @FXML
    private TextField partInventoryText;

    /**
     * Text field for part price.
     */
    @FXML
    private TextField partPriceText;

    /**
     * Text field for part max.
     */
    @FXML
    private TextField partMaxText;

    /**
     * Text field for machine ID/company name part.
     */
    @FXML
    private TextField partIdNameText;

    /**
     * Text field for part minimum level.
     */
    @FXML
    private TextField partMinText;
    
     /**
     * Sets machine ID/company name label to "Machine ID".
     *
     * @param event In-house radio button action.
     */
    @FXML
    void inHouseRadioAddPart(ActionEvent event) {

        idCompanyName.setText("Machine ID");
    }

     /**
     * Sets machine ID/company name label to "Company Name".
     *
     * @param event Outsourced radio button.
     */
    @FXML
    void outsourcedRadioAddPart(ActionEvent event) {

        idCompanyName.setText("Company Name");
    }
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
     * Adds new part to MainScreenController by using the inventory.
     * check the text fields if there is empty or invalid values.
     *
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void saveAddPart(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (name.isEmpty()) {
                AlartMessage.displayAlertAdd(5);
            } else {
                if (minCheck(min, max) && inventoryCheck(min, max, stock)) {

                    if (inHouseRadioButton.isSelected()) {
                        try {
                            machineId = Integer.parseInt(partIdNameText.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
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
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        mainScreen(event);
                    }
                }
            }
        } catch(IOException | NumberFormatException e) {
            AlartMessage.displayAlertAdd(1);
        }
    }
 /**
     * Return to MainScreen Page.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void cancelAddPart(ActionEvent event) throws IOException {

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
     * @param event MainScreen button action.
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
     * Initializes the controller class and set InHouse radio button to true.
     * 
     * @param url 
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHouseRadioButton.setSelected(true);
    }    
    
}
