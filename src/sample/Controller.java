package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView logo;
    @FXML
    private Text text;

    // Scene switchers
    public void switchToScene2(ActionEvent e) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }
    public void switchToScene3(ActionEvent e) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("scene3.fxml"));
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Rotates the logo
    private int degrees = 0;
    public void easterEgg(ActionEvent e) {
            logo.setRotate(degrees+=360/6);
    }

    public void exit(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Leaving application");
        alert.setHeaderText("You're about to leave the application");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Stage stage = (Stage) logo.getScene().getWindow();
            stage.close();
        }
    }

    // For testing purposes
    public void displayAccount(ActionEvent e) {
        this.text.setText(Main.bank.getAccount(0).toString());
    }
}
