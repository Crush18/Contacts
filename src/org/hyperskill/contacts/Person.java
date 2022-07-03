package org.hyperskill.contacts;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Person extends Record implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private String birthDate;
    private String gender;

    public Person() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setBirthDate(String birthDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        boolean valid = true;
        try {
            this.birthDate = LocalDate.parse(birthDate, dateTimeFormatter).toString();
        }catch (DateTimeException e) {
            valid = false;
        }
        if ((!valid) || birthDate.isEmpty()) {
            this.birthDate = "[no data]";
            System.out.println("Bad birth date!");
        }
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setGender(String gender) {
        if (gender.equals("M") || gender.equals("F")) {
            this.gender = gender;
        } else {
            System.out.println("Bad gender!");
            this.gender = "[no data]";
        }
    }

    public String getGender() {
        return this.gender;
    }

    @Override
    public String getShortInfo() {
        return getName()+ " " + getSurname();
    }

    @Override
    public String getFullInfo() {
        return getName()+ " " + getSurname() + " " + getBirthDate() + " " + getGender() + " " + getNumber();
    }

    @Override
    public void printInfo() {
        System.out.println("Name: " + this.getName());
        System.out.println("Surname: " + this.getSurname());
        System.out.println("Birth date: " + this.getBirthDate());
        System.out.println("Gender: " + this.getGender());
        System.out.println("Number: " + this.getNumber());
        System.out.println("Time created: " + this.getCreationDate());
        System.out.println("Time last edit: " + this.getEditionDate());
    }

    @Override
    public String getFields() {
        return "name, surname, birth, gender, number";
    }

    @Override
    public void setField(String field, String value) {
        switch (field) {
            case "name":
                setName(value);
                break;
            case "surname":
                setSurname(value);
                break;
            case "birth":
                setBirthDate(value);
                break;
            case "gender":
                setGender(value);
                break;
            case "number":
                setNumber(value);
                break;
        }
    }

    @Override
    public String getField(String field) {
        switch (field) {
            case "name":
                return getName();
            case "surname":
                return getSurname();
            case "birth":
                return getBirthDate();
            case "gender":
                return getGender();
            case "number":
                return getNumber();
            default:
                return getName();
        }
    }

    private void readObject(ObjectInputStream ois) throws Exception{
        ois.defaultReadObject();
        String number = (String) ois.readObject();
        LocalDateTime creationData = (LocalDateTime) ois.readObject();
        LocalDateTime editionData = (LocalDateTime) ois.readObject();
        super.init(number, creationData, editionData);
    }

    private void writeObject(ObjectOutputStream oos) throws Exception{
        oos.defaultWriteObject();
        oos.writeObject(super.getNumber());
        oos.writeObject(super.getCreationDate());
        oos.writeObject(super.getEditionDate());
    }
}
