package model;

public abstract class Person {
    private String name;
    private String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public abstract String getInfo();

    // Getters and setters for name and id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
package model;

public class Student extends Person {
    private String major;

    public Student(String name, String id, String major) {
        super(name, id);
        this.major = major;
    }

    @Override
    public String getInfo() {
        return "Student Name: " + getName() + ", ID: " + getId() + ", Major: " + major;
    }

    // Getters and setters for major
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StudentController {

    public void addStudent(TextField nameField, TextField idField, TextField majorField) {
        String name = nameField.getText();
        String id = idField.getText();
        String major = majorField.getText();

        if (name.isEmpty() || id.isEmpty() || major.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        Student student = new Student(name, id, major);
        // Save student data to a text file
        saveStudentData(student);
    }

    private void saveStudentData(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/StudentData.txt", true))) {
            writer.write(student.getInfo());
            writer.newLine();
        } catch (IOException e) {
            showError("Error saving student data.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package application;

import controller.StudentController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentApp extends Application {

    private StudentController controller;

    public StudentApp() {
        controller = new StudentController();
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField majorField = new TextField();
        majorField.setPromptText("Major");

        Button addButton = new Button("Add Student");
        addButton.setOnAction(e -> controller.addStudent(nameField, idField, majorField));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameField, idField, majorField, addButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
