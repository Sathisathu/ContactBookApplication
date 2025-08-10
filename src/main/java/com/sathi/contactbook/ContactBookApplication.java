package com.sathi.contactbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactBookApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ContactBookApplication.class.getResource("/contactView.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Contact Book");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }
    public static void main(String[] args){
        launch();
    }
}
