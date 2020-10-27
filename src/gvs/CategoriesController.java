package gvs;

import ds.Category;
import ds.ToDoList;
import ds.Expenses;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {

    ToDoList tdl = null;
    Expenses v = new Expenses(0);

    @FXML private TableView categoryTable;
    @FXML private TableColumn<Category, String> categoryTitleColumn;
    @FXML private TextField categoryTitle;
    @FXML private TextField addValue;
    public void setToDoListCategory(ToDoList t) {
        tdl = t;
        fillTable();
    }

    public void fillTable() {
        if (tdl != null) {
            categoryTable.getItems().clear();
            categoryTable.getItems().addAll(tdl.getAllUserCategories());
        }
    }

    public void addCategory(ActionEvent v) {
        String title= categoryTitle.getText();
        tdl.addCategory(title);
        fillTable();
        categoryTitle.setText("");
    }

    public void addTask(){
        String value = addValue.getText();
        int tvalue=Integer.parseInt(value);
        //int Value= v.addValue(tvalue);
        v.setValue(tvalue);
        //System.out.println(v.getValue());
        addValue.setText("");
    }
    public void valueAlert(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("VALUE");
        alert.setHeaderText("Full Value of Your Categories is: ");
        int val = v.getValue();
        String value = Integer.toString(val);
        alert.setContentText(value);
        alert.showAndWait();
    }


    public void changeCategoryTitle(TableColumn.CellEditEvent editedCell) {
        Category personSelected = (Category) categoryTable.getSelectionModel().getSelectedItem();
        personSelected.setTitle(editedCell.getNewValue().toString());
    }

    public void deleteCategory(){
        ObservableList<Category> selectedRows, allCategories;
        allCategories = categoryTable.getItems();
        selectedRows = categoryTable.getSelectionModel().getSelectedItems();
        allCategories.removeAll(selectedRows);
        for (Category category : selectedRows) {
            allCategories.remove(category);
            tdl.deleteCategory(category.getId());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Category, String> categoryIdColumn = new TableColumn<>("ID");
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("id"));
        categoryTitleColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("title"));

        categoryTable.getColumns().clear();
        categoryTable.getColumns().add(categoryIdColumn);
        categoryTable.getColumns().add(categoryTitleColumn);

        categoryTable.setEditable(true);
        categoryTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        categoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

}
