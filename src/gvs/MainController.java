package gvs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import ds.ToDoList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
    @FXML private MenuBar menubar;
    @FXML private PieChart userS;
    @FXML private AreaChart categorieS;

    ToDoList tdl = null;

    public void setToDoList(ToDoList t) {
        tdl = t;
        showUserStat();
        showCategoryStat();
    }

    public void showUserStat() {
        if (tdl != null) {
            int[] count = tdl.getUserCount();
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Persons -" + count[0], count[0]),
                            new PieChart.Data("Companies -" + count[1], count[1]));
            userS.setTitle("User statistics");
            userS.setData(pieChartData);
        }
    }

    public void showCategoryStat() {
        if (tdl != null) {
            XYChart.Series seriesMay = new XYChart.Series();
            seriesMay.setName("Tasks per category");
            int[][] a = tdl.getCategoryNumber();
            for (int[] row : a) {
                seriesMay.getData().add(new XYChart.Data(row[0], row[1]));
            }
            categorieS.getData().addAll(seriesMay);
        }
    }
    
    public void close(ActionEvent e) {
        Platform.exit();
    }

    public void importFile(ActionEvent b) {
        Scanner file = null;
        try{
            file = new Scanner(new File("src/users.txt"));
            while (file.hasNext()) {
                String line = file.nextLine();
                String[] elements = line.split(";");
                tdl.registerPerson(elements[1], elements[2], elements[3], elements[4], elements[5]);
            }
            Alert alertImport = new Alert(AlertType.INFORMATION);
            alertImport.setTitle("Import file");
            alertImport.setHeaderText("Success!");
            alertImport.setContentText("You successfully imported your file");
            alertImport.showAndWait();
        }catch (Exception e) {
            Alert importFailed = new Alert(AlertType.WARNING);
            importFailed.setTitle("Import file");
            importFailed.setHeaderText("Failed to read data");
            importFailed.setContentText("Error while reading data");
            importFailed.showAndWait();
        }finally {
            if(file != null) {
                file.close();
            }
        }
    }

    public void exportFile(ActionEvent a) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/data.tdl")));
            out.writeObject(tdl);
            out.close();
            Alert alertExport = new Alert(AlertType.INFORMATION);
            alertExport.setTitle("Export file");
            alertExport.setHeaderText("Success!");
            alertExport.setContentText("You successfully exported your file");
            alertExport.showAndWait();
        }catch (Exception e) {
            Alert exportFailed = new Alert(AlertType.WARNING);
            exportFailed.setTitle("Export file");
            exportFailed.setHeaderText("Failed to write data");
            exportFailed.setContentText("Something went wrong");
            exportFailed.showAndWait();
        }
    }

    public void openUserManager(ActionEvent q) throws Exception{
        FXMLLoader load = new FXMLLoader(getClass().getResource("Users.fxml"));
        Parent root = load.load();
        UsersController con = load.getController();
        con.setToDoList(tdl);

        Scene scene = new Scene(root);
        Stage newS = new Stage();
        newS.setTitle("User management");
        newS.setScene(scene);
        newS.show();
    }

    public void openCategoryManager(ActionEvent w) throws Exception{
        FXMLLoader load = new FXMLLoader(getClass().getResource("Categories.fxml"));
        Parent root = load.load();
        CategoriesController cont = load.getController();
        cont.setToDoListCategory(tdl);

        Scene scene = new Scene(root);
        Stage newS = new Stage();
        newS.setTitle("Category management");
        newS.setScene(scene);
        newS.show();
    }

    public void openAbout(ActionEvent q) throws Exception{
        FXMLLoader load = new FXMLLoader(getClass().getResource("About.fxml"));
        Parent root = load.load();
        AboutController ab = load.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
