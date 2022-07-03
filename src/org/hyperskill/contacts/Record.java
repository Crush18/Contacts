package org.hyperskill.contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Record {

    private LocalDateTime creationDate;
    private LocalDateTime editionDate;
    private String number = "";
    private boolean isInitialized = false;

    final private String template =
            "^(?i)([+](\\w )?([(]?\\w{2,}[)]?)?|[(]\\w{2,}[)]|\\w{1,}|\\w{2,}[ -][(]\\w{2,}[)])([ -][0-9a-z]{2,})?([ -][0-9a-z]{2,})?([ -][0-9a-z]{2,})?$";
    Pattern pattern = Pattern.compile(template);

    Record() {
        creationDate = LocalDateTime.now().withSecond(0).withNano(0);
        editionDate = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public void setEditionDate() {
        this.editionDate = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public LocalDateTime getEditionDate() {
        return this.editionDate;
    }

    private boolean checkNumber(String number) {
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public void setNumber (String number) {
        if (checkNumber(number)) {
            this.number = number;
        } else {
            this.number = "[no number]";
            System.out.println("Wrong number format!");
        }
    }

    public String getNumber() {
        return this.number;
    }

    public void editRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Select a field (%s): ", this.getFields());
        String field = scanner.nextLine();
        System.out.printf("Enter %s: ", field);
        String value = scanner.nextLine();
        this.setField(field, value);
        this.setEditionDate();
        System.out.println("Saved");
        this.printInfo();
    }

    public boolean hasNumber() {
        return !this.getNumber().equals("");
    }

    protected final void init(String number, LocalDateTime creationDate, LocalDateTime editionDate) {
        if (isInitialized) {
            throw new IllegalStateException("Cannot call init twice.");
        }
        this.number = number;
        this.creationDate = creationDate;
        this.editionDate = editionDate;
        this.isInitialized = true;
    }

    abstract public String getShortInfo();

    abstract public String getFullInfo();

    abstract public void printInfo();

    abstract public String getFields();

    abstract public void setField(String field, String value);

    abstract public String getField(String field);

}
