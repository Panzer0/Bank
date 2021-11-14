package sample;

import Backend.Bank;
import Backend.DataFile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static Bank bank;
    static int currentID;
    static DataFile dataFile;

    public void initBank() throws IOException {
        dataFile = new DataFile("data.txt");        // Read data
        bank = dataFile.extractBank();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            initBank();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            bank = new Bank();
        }
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        stage.setTitle("Bankiwi");
        stage.getIcons().add(new Image("Kiwi.png"));
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
