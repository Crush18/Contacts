package org.hyperskill.contacts;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Contacts implements Serializable {
    private static final long serialVersionUID = 1L;

    ArrayList<Record> records = new ArrayList<>();

    transient Scanner scanner = new Scanner(System.in);

    Contacts() {
    }

    public void addRecord() {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();

        Record record = "person".equals(type) ? new Person() : new Organization();

        String[] fields = record.getFields().split(", ");
        for (String field: fields) {
            System.out.printf("Enter the %s: ", field);
            String value = scanner.nextLine();
            record.setField(field, value);
        }
        this.records.add(record);
        System.out.println("The record added.");

    }

    public void removeRecord(Record record) {
        for (Record rec: records) {
            if (rec == record) {
                this.records.remove(rec);
                break;
            }
        }
        System.out.println("The record removed!");
    }



    public void countRecords() {
        System.out.printf("The Phone Book has %d records.\n", this.records.size());
    }

    public void printContacts() {
        int counter = 1;
        for (Record item : this.records) {
            System.out.print(counter + ". " + item.getShortInfo() + "\n");
            counter++;
        }
    }

    public void getInfo() {
        if (this.records.size() == 0) {
            System.out.println("No records to get info!");
            return ;
        }
        printContacts();
        System.out.print("Enter index to show info: ");
        int recNum = scanner.nextInt() - 1;
        this.records.get(recNum).printInfo();
    }

    public ArrayList<Record> searchRecords() {
        System.out.print("Enter search query: ");
        String query = "(?i).*" + scanner.nextLine() + ".*";
        Pattern pattern = Pattern.compile(query);
        ArrayList <Record> searchResult = new ArrayList<>();
        for (Record record: records) {
            if (pattern.matcher(record.getFullInfo()).matches()) {
                searchResult.add(record);
            }
        }
        System.out.printf("Found %d result%s: \n", searchResult.size(), searchResult.size() > 1 ? "s" : "");
        int count = 1;
        for (Record record: searchResult) {
            System.out.printf("%d. %s\n", count, record.getShortInfo());
            count++;
        }
        return searchResult;
    }
}