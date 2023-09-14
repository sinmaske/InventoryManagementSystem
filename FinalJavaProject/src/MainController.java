import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Label fgtPwd;

    @FXML
    private Label mismatchError;

    @FXML
    private TextField pwd;

    @FXML
    private TextField username;

    @FXML
    void initialize() {
        // Initialize the recordList when the controller is initialized
        recordList = new ArrayList<>();
    }


    private ArrayList<UserCredentials> recordList = new ArrayList<>();

    public void setRecordList(ArrayList<UserCredentials> recordList) {
        this.recordList = recordList;
    } 

    private void loadUserCredentials() {
        
        try (BufferedReader reader = new BufferedReader(new FileReader("Login.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    recordList.add(new UserCredentials(username, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnCreateNewAcc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createAcc.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Creating Account");
            stage.setScene(new Scene(root));
            stage.show();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     @FXML
    void OnLogin(ActionEvent event) throws IOException {

        this.loadUserCredentials();
        String enteredUsername = username.getText();
        String enteredPassword = pwd.getText();

        boolean loggedIn = false;

        for (UserCredentials credentials : recordList) {
            if (credentials.getUsername().equals(enteredUsername) && credentials.getPassword().equals(enteredPassword)) {
                loggedIn = true;
                break;
            }
        }
    
        if (loggedIn) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = loader.load();
                // Get the DashboardController and set the username
                DashboardController dashboardController = loader.getController();
                dashboardController.setUserName(enteredUsername);
                dashboardController.initialize();

                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root));
                stage.show();
    
                // Close the login window
                Stage loginStage = (Stage) username.getScene().getWindow();
                loginStage.close();
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mismatchError.setText("Invalid username or password.");
            mismatchError.setTextFill(Color.RED);
        }
    }

    @FXML
    void onForgotPwd(ActionEvent event) {
    String message = "An Email with instructions to reset\nyour password has been sent.";
    showInformationAlert("Password Reset", message);
}

    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

