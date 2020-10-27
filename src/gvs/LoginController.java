package gvs;

import ds.ToDoList;
import ds.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML private TextField loginUsername, loginPassword;

    ToDoList todo = new ToDoList();

    public void setToDoList(ToDoList t) {
        todo = t;
    }

    public void login(ActionEvent ae) throws IOException {
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        try {
            User u = NewFXMain.todo.login(username, password);
            Scene scene = loginUsername.getScene();
            FXMLLoader load = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = load.load();
            MainController con = load.getController();
            con.setToDoList(todo);
            scene.setRoot(root);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Login");
            alert.setHeaderText("Wrong username or password");
            alert.setContentText("Try again");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
