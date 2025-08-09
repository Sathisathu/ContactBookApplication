package com.sathi.contactbook;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;

public class ContactController {
    @FXML
    private TableView<Contact> contactTableView;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button addButton;
    @FXML
    private TableColumn<Contact,String> nameColumn;
    @FXML
    private TableColumn<Contact,String> phoneColumn;
    @FXML
    private TableColumn<Contact,String> emailColumn;


    private final ContactBook contactBook = new ContactBook();

    @FXML
    public void initialize() {
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        // For the nameColumn, get the value by calling contact.name()
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().name()));

        // For the phoneColumn, get the value by calling contact.phoneNumber()
        phoneColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().phoneNumber()));

        // For the emailColumn, get the value by calling contact.email()
        emailColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().email()));

        try {
            contactBook.loadFile("contacts");
        } catch (IOException e) {
            System.out.println("EXceptions: "+e);
        }
         contactTableView.getItems().setAll(contactBook.getAllContacts());
    }

    @FXML
    public void onAddButtonClick(){
        String name = nameTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();

        if(name.isBlank() || phone.isBlank() || email.isBlank()){
            System.out.println("All fields are required.");
            return;
        }
        Contact newContact = new Contact(name,phone,email);
        if(contactBook.addContact(newContact)){

            contactTableView.getItems().add(newContact);

            nameTextField.clear();
            phoneTextField.clear();
            emailTextField.clear();
            try{
                contactBook.saveFile("contacts");
            } catch (IOException e) {
                System.out.println("EXCEPTIONS: "+e.getLocalizedMessage());
            }
        }else{
            System.out.println("Contact already exists.");
        }
    }

}
