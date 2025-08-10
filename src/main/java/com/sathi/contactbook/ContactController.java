package com.sathi.contactbook;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

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
    private TextField searchTextField;

    @FXML
    private Button saveButton;
    @FXML
    private VBox mainActionsVBox;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox addContactFormVBox;

    @FXML
    private TableColumn<Contact,String> nameColumn;
    @FXML
    private TableColumn<Contact,String> phoneColumn;
    @FXML
    private TableColumn<Contact,String> emailColumn;


    private final ContactBook contactBook = new ContactBook();
    private ObservableList<Contact> masterContactList;

    @FXML
    public void initialize() {
//        This don't work for Records     -----> It Expects the method getName.
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().phoneNumber()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));

        try {
            contactBook.loadFile("contacts");
        } catch (IOException e) {
            System.out.println("EXceptions: "+e);
        }

        masterContactList = FXCollections.observableArrayList(contactBook.getAllContacts());

        FilteredList<Contact> filteredData = new FilteredList<>(masterContactList,p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(contact -> {
                if(newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();

                if(contact.name().toLowerCase().contains(lowerCaseFilter))
                    return true;
                return false;
            });
        });
        contactTableView.setItems(filteredData);

        deleteButton.setDisable(true);

        contactTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            deleteButton.setDisable(newSelection == null);
        });
    }

    @FXML
    public void onSaveButtonClick(){
        String name = nameTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();

        if(name.isBlank() || phone.isBlank() || email.isBlank()){
            Alert fieldError = new Alert(Alert.AlertType.ERROR);
            fieldError.setTitle("Incomplete Form");
            fieldError.setHeaderText("Required fields are missing");
            fieldError.setContentText("Please fill out all fields");
            fieldError.showAndWait();
            return;
        }
        Contact newContact = new Contact(name,phone,email);
        if(contactBook.addContact(newContact)){

            masterContactList.add(newContact);

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
        hideAddContactForm();
    }

    @FXML
    public void showAddContactForm() {
        mainActionsVBox.setVisible(false);
        mainActionsVBox.setManaged(false);
        addContactFormVBox.setVisible(true);
        addContactFormVBox.setManaged(true);
    }

    @FXML
    public void hideAddContactForm() {
        addContactFormVBox.setVisible(false);
        addContactFormVBox.setManaged(false);
        mainActionsVBox.setVisible(true);
        mainActionsVBox.setManaged(true);
    }

    @FXML
    public void onDeleteButtonClick() throws IOException{
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Delete Contact");
        alert.setHeaderText("Are you sure want to delete this contact?");
        alert.setContentText(selectedContact.name()+"\n"+selectedContact.phoneNumber());

        deleteAlert.setTitle("Delete Contact");
        deleteAlert.setHeaderText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            contactBook.removeContact(selectedContact.name());
            masterContactList.remove(selectedContact);
            contactBook.saveFile("contacts");
            deleteAlert.setContentText("Contact deleted successfully");
            deleteAlert.showAndWait();
        }
        else{
            System.out.println("Deletion Cancelled");
        }

    }
}
