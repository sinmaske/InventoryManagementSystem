import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class CreateAccController {

    @FXML
    private TextField RePwdTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private Label pwdError;

    @FXML
    private TextField pwdTxt;

    @FXML
    private TextField usernameTxt;

    private ArrayList<UserCredentials> recordList = new ArrayList<>() ;

    private boolean isValidEmployeeID(String employeeID) {
        try {
            Integer.parseInt(employeeID);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    void OnCreateAcc(ActionEvent event) throws IOException {
    String enteredUsername = usernameTxt.getText();
    String enteredPassword = pwdTxt.getText();
    String reenteredPassword = RePwdTxt.getText();
    String enteredEmployeeID = idTxt.getText();

    if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || reenteredPassword.isEmpty()) {
        showErrorAlert("Please fill in all fields.");
        return;
    }

    if (!enteredPassword.equals(reenteredPassword)) {
        showErrorAlert("Passwords do not match.");
        return;
    }

    for (UserCredentials credentials : recordList) {
        if (credentials.getUsername().equals(enteredUsername)) {
            showErrorAlert("Username already exists.");
            return;
        }
    }

    if (!PasswordValidator.validatePassword(enteredPassword)) {
        showErrorAlert("Password must contain a special character, an uppercase letter, a number, and be greater than 5 characters.");
        return;
    }

    if (!isValidEmployeeID(enteredEmployeeID)) {
        showErrorAlert("Employee ID must be a valid integer.");
        return;
    }

    pwdError.setText("Account created Successfully");
    pwdError.setTextFill(Color.GREEN);
    this.addDataToArrayList();
    this.addDataToFile();
}

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void addDataToArrayList(){
        recordList.add(new UserCredentials(usernameTxt.getText(),pwdTxt.getText()));
    }

    public void addDataToFile() throws IOException {
        FileWriter output = new FileWriter(new File("Login.txt"), true);
        PrintWriter writer = new PrintWriter(output);
    
        UserCredentials newCredentials = recordList.get(recordList.size() - 1);
        writer.printf("%s,%s%n", newCredentials.getUsername(), newCredentials.getPassword());
    
        writer.close();
    }
    


    
}
