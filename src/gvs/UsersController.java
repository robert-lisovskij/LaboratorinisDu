package gvs;

import ds.Company;
import ds.Person;
import ds.User;
import ds.ToDoList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class UsersController implements Initializable{

    ToDoList tdl = null;

    @FXML private TableView userTable;
    @FXML private TableColumn<User, String> nameColumn, surnameColumn, titleColumn;
    @FXML private TextField lLogin, lPass, lName, lSurname, pTitle, pLogin, pPass;

    public void setToDoList(ToDoList t) {
        tdl = t;
        fillTable();
    }

    public void addPerson(ActionEvent z) {
        String login = lLogin.getText();
        String pass = lPass.getText();
        String name = lName.getText();
        String surname = lSurname.getText();
        tdl.registerPerson(login, pass, name, surname, "-");
        fillTable();
        //userTable.getSelectionModel().
        lLogin.setText("");
        lPass.setText("");
        lName.setText("");
        lSurname.setText("");
    }

    public void addCompany(ActionEvent x) {
        String login = pLogin.getText();
        String pass = pPass.getText();
        String title = pTitle.getText();
        tdl.registerCompany(login, pass, title, "-", "-");
        fillTable();
        pLogin.setText("");
        pPass.setText("");
        pTitle.setText("");
    }

    public void fillTable() {
        if (tdl != null) {
            userTable.getItems().clear();
            userTable.getItems().addAll(tdl.getAllActiveUsers());
        }
    }

    public void changeName(TableColumn.CellEditEvent editedCell) {
        Person personSelected = (Person) userTable.getSelectionModel().getSelectedItem();
        personSelected.setName(editedCell.getNewValue().toString());
    }

    public void changeSurname(TableColumn.CellEditEvent editedCell) {
        Person personSelected = (Person) userTable.getSelectionModel().getSelectedItem();
        personSelected.setSurname(editedCell.getNewValue().toString());
    }

    public void changeTitle(TableColumn.CellEditEvent editedCell) {
        Company companySelected = (Company) userTable.getSelectionModel().getSelectedItem();
        companySelected.setTitle(editedCell.getNewValue().toString());
    }

    public void deleteUser(){
        ObservableList<User> selectedRows, allPeople;
        allPeople = userTable.getItems();
        selectedRows = userTable.getSelectionModel().getSelectedItems();
        for (User user : selectedRows) {
            allPeople.remove(user);
            tdl.disableUser(user.getId());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<User, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("title"));

        userTable.getColumns().clear();
        userTable.getColumns().add(idColumn);
        userTable.getColumns().add(usernameColumn);
        userTable.getColumns().add(nameColumn);
        userTable.getColumns().add(surnameColumn);
        userTable.getColumns().add(titleColumn);

        userTable.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
