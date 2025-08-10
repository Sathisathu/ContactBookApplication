# Contact Book Application

A simple and responsive desktop application for managing personal contacts, built with Java and JavaFX.

## Features

* **View Contacts:** Displays all contacts in a clean, resizable table.
* **Add Contacts:** A dedicated form to add new contacts with a name, phone number, and email.
* **Delete Contacts:** Select and delete contacts with a confirmation dialog to prevent accidental deletion.
* **Search Contacts:** Search and filter the contacts by their name
* **Persistent Storage:** All contacts are automatically saved to a `data/contacts.csv` file and loaded on startup.
* **Responsive UI:** The application window and layout resize gracefully.

## Technologies Used

* **Java 21**
* **JavaFX 21:** For the graphical user interface.
* **Maven:** For project build and dependency management.

## How to Run the Project

To set up and run this project on your local machine, follow these steps:

1.  **Prerequisites:**
    * Java Development Kit (JDK) 21 or newer.
    * JavaFX SDK 21 or newer.
    * Apache Maven.


2.  **Clone the Repository:**
    ```sh
    git clone https://github.com/Sathisathu/ContactBookApplication
    cd ContactBookApplication
    ```

3.  **Run with Maven:**
    The project uses the `javafx-maven-plugin` to run. Use the following command in your terminal from the project's root directory:
    ```sh
    mvn javafx:run
    ```

---
