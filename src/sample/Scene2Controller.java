package sample;

import Backend.Address;
import Backend.InvalidPESELException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene2Controller {
    @FXML
    private ImageView logo;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField creationNameField;
    @FXML
    private TextField creationSurnameField;
    @FXML
    private TextField creationPESELField;
    @FXML
    private TextField creationStreetField;
    @FXML
    private TextField creationHouseNumberField;
    @FXML
    private TextField creationCityField;


    public void switchToScene1(ActionEvent e) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void displayError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input");
        alert.setContentText(text);
        alert.showAndWait();
    }

    public Boolean areAllFieldsFilled() {
        if(creationNameField.getText().equals(""))  return false;
        if(creationSurnameField.getText().equals(""))  return false;
        if(creationPESELField.getText().equals(""))  return false;
        if(creationCityField.getText().equals(""))  return false;
        if(creationStreetField.getText().equals(""))  return false;
        if(creationHouseNumberField.getText().equals(""))  return false;
        return true;
    }

    public void confirm(ActionEvent e) throws IOException {
        try {
            if(!areAllFieldsFilled()) throw new EmptyBoxException("All fields are mandatory!");
            Address address = new Address(creationStreetField.getText(),
                    Integer.parseInt(creationHouseNumberField.getText()), creationCityField.getText());
            Main.bank.openAccount(creationPESELField.getText(), creationNameField.getText(),
                    creationSurnameField.getText(),address);
            Main.dataFile.saveData();
        } catch (InvalidPESELException | EmptyBoxException invalidPESELException) {
            displayError(invalidPESELException.getMessage());
            return;
        }
        catch (NumberFormatException numberFormatException) {
            displayError("Non-numeric road number value");
            return;
        }
        switchToScene1(e);
    }
    // Rotates the logo
    private int degrees = 0;
    public void easterEgg(ActionEvent e) {
        logo.setRotate(degrees+=360/6);
    }

}
