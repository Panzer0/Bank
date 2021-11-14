package sample;

import Backend.BankMoney;
import Backend.InvalidTransactionException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Scene4Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene3(ActionEvent e) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("scene3.fxml"));
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    @FXML
    private ImageView logo;
    @FXML
    private Text IDtext;
    @FXML
    private Text fullNameText;
    @FXML
    private Text PESELText;
    @FXML
    private Text addressText;
    @FXML
    private Text balanceText;


    public void openDepositDialog(ActionEvent e) throws InvalidTransactionException, IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit money");
        dialog.setContentText("How much would you like to deposit?");

        Optional<String> result = dialog.showAndWait();
        try{
            if (result.isPresent()){
                if(result.get().isEmpty()) throw new EmptyBoxException("All fields are mandatory!");
                Main.bank.getAccount(Main.currentID).depositMoney(new BankMoney(result.get()));
            }
        }
        catch(InvalidTransactionException | NumberFormatException | EmptyBoxException exception) {
            displayError(exception.getMessage());
        }
        refreshProfile();
    }
    public void openWithdrawDialog(ActionEvent e) throws InvalidTransactionException, IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit money");
        dialog.setContentText("How much would you like to deposit?");

        Optional<String> result = dialog.showAndWait();
        try {
            if (result.isPresent()) {
                if(result.get().isEmpty()) throw new EmptyBoxException("All fields are mandatory!");
                Main.bank.getAccount(Main.currentID).withdrawMoney(new BankMoney(result.get()));
            }
        }
        catch(InvalidTransactionException | NumberFormatException | EmptyBoxException exception) {
            displayError(exception.getMessage());
        }
        refreshProfile();
    }
    public void openTransferDialog(ActionEvent e) throws InvalidTransactionException {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Transfer money");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();

        TextField count = new TextField();
        count.setPromptText("Enter amount");
        TextField to = new TextField();
        to.setPromptText("Enter ID");

        gridPane.add(new Label("Amount:"), 0, 0);
        gridPane.add(count, 0, 1);
        gridPane.add(new Label("Recipient:"), 1, 0);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(count.getText(), to.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            try {
                if(pair.getValue().isEmpty()) {
                    throw new EmptyBoxException("All fields are mandatory!");
                }
                if(pair.getKey().isEmpty()) {
                    throw new EmptyBoxException("All fields are mandatory!");
                }
                Main.bank.transferMoney(Main.bank.getAccount(Main.currentID),
                        Main.bank.getAccount(Integer.parseInt(pair.getValue())), new BankMoney(pair.getKey()));
                this.refreshProfile();
            } catch (InvalidTransactionException | IOException | IllegalArgumentException | EmptyBoxException exception) {
                displayError(exception.getMessage());
            }
        });
    }


    public void closeAccount(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account removal");
        alert.setHeaderText("You're about to delete the account");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Main.bank.closeAccount(Main.currentID);
            Main.dataFile.saveData();
            switchToScene3(e);
        }
    }






    public void updateIDText() {
        IDtext.setText("Account #" + Main.bank.getAccount(Main.currentID).getID());
    }
    public void updateFullNameText() {
        fullNameText.setText("Owner:\t" + Main.bank.getAccount(Main.currentID).getName() + " " + Main.bank.getAccount(Main.currentID).getSurname());
    }
    public void updatePESEL() {
        PESELText.setText("PESEL:\t" + Main.bank.getAccount(Main.currentID).getPESEL());
    }
    public void updateAddress() {
        addressText.setText("Address:\t" + Main.bank.getAccount(Main.currentID).getDisplayAddress());
    }
    public void updateBalance() {
        balanceText.setText("Balance:\t" + Main.bank.getAccount(Main.currentID).getDisplayBalance());
    }

    public void refreshProfile() throws IOException {
        Main.dataFile.saveData();
        updateIDText();
        updateFullNameText();
        updatePESEL();
        updateAddress();
        updateBalance();
    }


    public void displayError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input");
        alert.setContentText(text);
        alert.showAndWait();
    }


    // Rotates the logo
    private int degrees = 0;
    public void easterEgg(ActionEvent e) {
        logo.setRotate(degrees+=360/6);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
