package com.sathi.contactbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ContactBookApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ContactBookApplication.class.getResource("/contactView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(),1024,700);
        stage.setTitle("Contact Book");
        try{
            Image appImage = new Image(getClass().getResourceAsStream("/icons/appIcon.png"));
            stage.getIcons().add(appImage);
        }catch (Exception e){
            System.err.println("Error in setting app icons");
        }
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){
        launch();
    }
}
