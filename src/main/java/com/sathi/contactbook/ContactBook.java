package com.sathi.contactbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

public class ContactBook {
    private final HashMap<String, Contact> contactBook = new HashMap<>();

    private static final String USER_HOME = System.getProperty("user.home");
    private Path folderPath = Paths.get(USER_HOME,".ContactBook");

    public boolean addContact(Contact contact) {
        if (contactBook.containsKey(contact.name())) {
            return false;
        }
        else {
            contactBook.put(contact.name(), contact);
            return true;
        }
    }

    public void removeContact(String name){
        contactBook.remove(name);
    }

    public Collection<Contact> getAllContacts(){
        return contactBook.values();
    }

    public void saveFile(String filename) throws IOException {
            createAppDataFolder();
//          Creating file if not exists
            Path filepath = folderPath.resolve(filename + ".csv");
            if (Files.notExists(filepath)) Files.createFile(filepath);

            StringBuilder sb = new StringBuilder();
            for (Contact contact : contactBook.values()) {
                sb.append(contact.name()).append(',');
                sb.append(contact.phoneNumber()).append(',');
                sb.append(contact.email()).append('\n');
            }
            Files.writeString(filepath, sb);
//            System.out.println("Contacts Saved Successfully.");
    }
    public void loadFile(String filename) throws IOException{
        Path filePath = folderPath.resolve(filename+".csv");
        if(Files.notExists(filePath)) return;
        BufferedReader reader = Files.newBufferedReader(filePath);
        String line;
        contactBook.clear();
        while((line=reader.readLine())!=null){
            String[] details = line.split(",");
            String name = details[0];
            String phone = details[1];
            String email = details[2];
            contactBook.put(name,new Contact(name,phone,email));
        }
        reader.close();
    }
    private void createAppDataFolder() throws IOException{
        if(Files.notExists(folderPath)) Files.createDirectories(folderPath);

        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("windows")){
            Files.setAttribute(folderPath,"dos:hidden",true);
        }

    }
}