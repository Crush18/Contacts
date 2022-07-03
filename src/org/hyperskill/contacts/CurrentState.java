package org.hyperskill.contacts;

import java.util.ArrayList;

public class CurrentState {
    /**
     * Implements different submenus for main menu: search, list, record
     */
    final String[] states = {"menu", "search", "list", "record"};
    private String currentState;
    private Record currentRecord = null;
    ArrayList<Record> searchList = null;

    CurrentState() {
        currentState = states[0];
    }

    public void setCurrentRecord(Record currentRecord) {
        this.currentRecord = currentRecord;
    }

    public Record getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void showHintMessage() {
        switch (currentState) {
            case "menu":
                System.out.print("[menu] Enter action (add, list, search, count, exit): ");
                break;
            case "search":
                System.out.print("[search] Enter action ([number], back, again): ");
                break;
            case "list":
                System.out.print("[list] Enter action ([number], back): ");
                break;
            case "record":
                System.out.print("[record] Enter action (edit, delete, menu): ");
        }
    }

}
