package gvs;

import ds.ToDoList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class NewFXMain extends Application {
    static ToDoList todo = new ToDoList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("src/data.tdl")));
            todo = (ToDoList) in.readObject();
            in.close();
        }catch (Exception e){
            System.out.println("Failed to load data");
            todo.registerPerson("admin", "admin", "admin", "admin", "-");
            todo.registerPerson("person", "person", "person", "person", "-");
            todo.registerCompany("company", "company", "company", "-", "-");
            todo.login("admin", "admin");
            todo.addCategory("Project1");
            todo.addCategory("Project2");
            todo.addCategory("Project3");
            todo.addTask("task1", "description1", 1);
            todo.addTask("task2", "description2", 1);
            todo.addTask("task3", "description3", 3);
        }

        FXMLLoader load = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = load.load();
        LoginController con = load.getController();
        con.setToDoList(todo);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        todo.registerPerson("admin", "admin", "person", "personal", "-");
        launch(args);
    }
}