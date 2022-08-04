package org.hyperskill.contacts;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner userInput = new Scanner(System.in);
        String action;

        String filename = "phonebook.db";

        Contacts phoneBook = new Contacts(); //(Contacts) SerializationUtils.deserialize(filename);
        CurrentState currentState = new CurrentState();

        File file = new File(filename);
        if (file.exists()) {
            try {
                phoneBook = (Contacts) SerializationUtils.deserialize(filename);
                System.out.println("open " + file.getName());
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        while (true) {
            currentState.showHintMessage();
            action = userInput.nextLine();
            if (action.equals("exit")) { break; }
            switch (action) {
                case "add":
                    phoneBook.addRecord();
                    saveData(phoneBook, filename);
                    break;
                case "list":
                    phoneBook.printContacts();
                    currentState.searchList = phoneBook.records;
                    currentState.setCurrentState("list");
                    break;
                case "search":
                case "again":
                    currentState.searchList = phoneBook.searchRecords();
                    currentState.setCurrentState("search");
                    break;
                case "count":
                    phoneBook.countRecords();
                    break;
                case "menu":
                case "back":
                    currentState.setCurrentState("menu");
                    currentState.searchList = null;
                    break;
                case "edit":
                    currentState.getCurrentRecord().editRecord();
                    saveData(phoneBook, filename);
                    break;
                case "delete":
                    phoneBook.removeRecord(currentState.getCurrentRecord());
                    currentState.setCurrentRecord(null);
                    saveData(phoneBook, filename);
                    break;
                default:
                    if (action.matches("[0-9]+")) {
                        if (currentState.getCurrentState().equals("search")) {
                            currentState.setCurrentRecord(currentState.searchList.get(Integer.parseInt(action) - 1));
                        } else {
                            currentState.setCurrentRecord(phoneBook.records.get(Integer.parseInt(action) - 1));
                        }
//                        currentState.setCurrentRecord(currentState.searchList.get(Integer.parseInt(action) - 1));
                        currentState.setCurrentState("record");
                        currentState.getCurrentRecord().printInfo();
                    }
            }
            System.out.println();
        }
    }

    private static void saveData(Object obj, String fileName){
        try{
            SerializationUtils.serialize(obj, fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

