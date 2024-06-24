package com.example.cringecoding.Services;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class OtherUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(OtherUtils.class.getResource(fxmlFile));
            root = loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }

        if (root != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } else {
            showAlert("Error", "Unable to load the scene.");
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
