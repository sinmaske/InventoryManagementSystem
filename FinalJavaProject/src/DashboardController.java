import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label lblHeading;

    @FXML
    private Label UserTxt;

    @FXML
    private Label lblSubheading;

    @FXML
    private Label comingSoon;

    private String userName;

    public void setUserName(String enteredUsername) {
        userName = enteredUsername;
    }

    @FXML
    void initialize() {
        UserTxt.setText(userName); // Set the username in the UserTxt label
    }


    @FXML
    void OnAllProductd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AllProducts.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("All Products");
            stage.setScene(new Scene(root));
            stage.show();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnLowStockItems(ActionEvent event) {
        comingSoon.setText("Coming Soon");
        comingSoon.setTextFill(Color.BLUE);
    }

    @FXML
    void OnProfit(ActionEvent event) {
        comingSoon.setText("Coming Soon");
    }

    @FXML
    void OnSales(ActionEvent event) {
        comingSoon.setText("Coming Soon");
    }

}
