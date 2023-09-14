import java.io.*;
import java.util.ArrayList;

import java.util.Scanner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class AllProductsController {

    @FXML
    private TextArea TxtField;

    @FXML
    private Label lblTotalProducts;

    @FXML
    private Label lblPrompt;

    @FXML
    private Label lblError;

    @FXML
    private TextField userInput;

    @FXML
    private TextField addDelete;

    private ArrayList<String> groceryList = new ArrayList<>();

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            // Set focus on the userInput TextField when the window is opened
            userInput.requestFocus();
        });
    }

    @FXML
    void OnClear(ActionEvent event) {
        lblError.setText(""); 
        TxtField.clear();
    }

    @FXML
void OnRead(ActionEvent event) {
    lblError.setText(""); 
    String userInputValue = userInput.getText().toLowerCase();
    String fileName = userInputValue + ".txt";

    try {
        File file = new File(fileName);
        if (!file.exists()) {
            TxtField.setText("File not found.");
            return;
        }

        TxtField.clear(); // Clear the field before displaying the file content

        // Read the file content
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(file);
        groceryList.clear(); // Clear the existing list before updating
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            content.append(line).append("\n");
            groceryList.add(line); // Add each line to the list
        }
        scanner.close();

        TxtField.setText(content.toString());

    } catch (FileNotFoundException e) {
        TxtField.setText("File not found.");
    }
}
    

@FXML
void OnSave(ActionEvent event) {
    lblError.setText("");
    String userInputValue = userInput.getText().toLowerCase();
    String fileName = userInputValue + ".txt";

    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirm Save");
    confirmationAlert.setHeaderText("Confirm Data Save");
    confirmationAlert.setContentText("Are you sure you want to save the data?");
    
    confirmationAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            try {
                FileWriter output = new FileWriter(new File(fileName));
                PrintWriter writer = new PrintWriter(output);

                for (String listItem : groceryList) {
                    writer.println(listItem); // Write each item to the file
                }

                writer.close();

                lblError.setText("Data saved successfully");
                lblError.setTextFill(Color.GREEN);
            } catch (IOException e) {
                lblError.setText("Error saving data");
                lblError.setTextFill(Color.RED);
            }
        }
    });
}

@FXML
    void OnAdd(ActionEvent event) {
        String item = addDelete.getText().trim();
        if (!item.isEmpty()) {
            groceryList.add(item);
            addDelete.clear();
            lblError.setText("");
        }
    }

    @FXML
    void OnDelete(ActionEvent event) {
    String item = addDelete.getText().trim();
    if (!item.isEmpty()) {
        if (groceryList.contains(item)) {
            groceryList.remove(item); // Remove from the ArrayList
            addDelete.clear();
            lblError.setText("");

            lblError.setText("Item deleted");
            lblError.setTextFill(Color.GREEN);
        } else {
            lblError.setText("Item not found in the list");
            lblError.setTextFill(Color.RED);
        }
    } else {
        lblError.setText("Please enter an item to delete");
        lblError.setTextFill(Color.RED);
    }
}
}






