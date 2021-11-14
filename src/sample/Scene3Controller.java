package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene3Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView logo;
    @FXML
    private ListView<String> listView;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField textField;

    // Scene switchers
    public void switchToScene1(ActionEvent e) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }
    public void switchToScene4(ActionEvent e) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("scene4.fxml"));
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Converts the contents of a listView entry to its ID
    public int listEntryToID(String entry) {
        return Integer.parseInt(entry.split(". ")[0]);
    }

    // Open the selected profile
    public void goToProfile(ActionEvent e) throws IOException {
        if(!listView.getSelectionModel().isEmpty()) {
            Main.currentID = listEntryToID(listView.getSelectionModel().getSelectedItem());
            switchToScene4(e);
        }
    }

    // Applies a filter to the listView
    public void updateListView(ActionEvent e) {
        switch (choiceBox.getValue()) {
            case "-" -> listView.setItems(Main.bank.toIDList());
            case "Name" -> listView.setItems(Main.bank.toIDListWhereName(textField.getText()));
            case "Surname" -> listView.setItems(Main.bank.toIDListWhereSurname(textField.getText()));
            case "PESEL" -> listView.setItems(Main.bank.toIDListWherePESEL(textField.getText()));
            case "City" -> listView.setItems(Main.bank.toIDListWhereCity(textField.getText()));
            case "Street" -> listView.setItems(Main.bank.toIDListWhereStreet(textField.getText()));
        }
    }

    // Initialises the listView, which lists matching accounts
    public void initListView() {
        listView.setItems(Main.bank.toIDList());
    }

   // Initialises the choiceBox, which holds possible filter types
    public void initChoiceBox() {
        this.choiceBox.getItems().add("-");
        this.choiceBox.getItems().add("Name");
        this.choiceBox.getItems().add("Surname");
        this.choiceBox.getItems().add("PESEL");
        this.choiceBox.getItems().add("City");
        this.choiceBox.getItems().add("Street");

        this.choiceBox.setValue("-");
    }
    // Rotates the logo
    private int degrees = 0;
    public void easterEgg(ActionEvent e) {
        logo.setRotate(degrees+=360/6);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initListView();
        initChoiceBox();
    }
}
