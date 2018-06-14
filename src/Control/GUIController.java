package Control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class GUIController {
    public TableView tableView;

    //public ListView<CheckBox> testi;
    private ObservableList<ObservableList> tableData = FXCollections.observableArrayList();

    public HBox mainMenuButtons;
    
    public Button historiaMedycznaButton;
    public Button wizytyButton;
    public Button ankietyButton;

    public Button modyfikujOsobyButton;

    public VBox tabeleOsoby;
    public Button pracownicyButton;
    public GridPane pracownicyMenu;
    public ListView pracownicyList;
    public TextField pracownicyImieField;
    public TextField pracownicyNazwiskoField;
    public TextField pracownicyPeselField;
    public Button pracownicyInsertButton;
    public Button pracownicyDeleteButton;
    public Button closePracownicyMenuButton;

    public Button pacjenciButton;
    public GridPane pacjenciMenu;
    public Button closePacjenciMenuButton;
    public TextField pacjenciImieField;
    public TextField pacjenciNazwiskoField;
    public TextField pacjenciPeselField;
    public TextField pacjenciNrPaszportuField;
    public TextField pacjenciDataUrodzeniaField;
    public TextField pacjenciPlecField;
    public Button pacjenciInsertButton;
    public ListView pacjenciList;
    public Button pacjenciDeleteButton;

    public Button modyfikujRelacjeButton;
    public VBox tabeleRelacje;
    public Button pracownicyRoleButton;
    public Button lekarzeSpecjalizacjeButton;
    public Button LPKButton;

    public Button modyfikujMetadaneButton;
    public VBox tabeleMetadane;
    public Button roleButton;
    public Button specjalizacjeButton;
    public Button celeWizytButton;
    public Button wydarzeniaMedyczneButton;


    @FXML
    public void showTabeleOsoby() {
        tabeleOsoby.setVisible(true);
        tabeleOsoby.toFront();
        //comboBoxTest();
    }

    @FXML
    public void hideTabeleOsoby() {
        tabeleOsoby.setVisible(false);
    }

    @FXML
    public void showTabeleRelacje() {
        tabeleRelacje.setVisible(true);
        tabeleRelacje.toFront();
    }

    @FXML
    public void hideTabeleRelacje() {
        tabeleRelacje.setVisible(false);
    }

    @FXML
    public void showTabeleMetadane() {
        tabeleMetadane.setVisible(true);
        tabeleMetadane.toFront();
    }

    @FXML
    public void hideTabeleMetadane() {
        tabeleMetadane.setVisible(false);
    }

    @FXML
    public void showPracownicyMenu() {
        pracownicyMenu.setVisible(true);
    }

    @FXML
    public void hidePracownicyMenu() {
        pracownicyMenu.setVisible(false);
    }

    @FXML
    public void showPacjenciMenu() {
        pacjenciMenu.setVisible(true);
    }

    @FXML
    public void hidePacjenciMenu() {
        pacjenciMenu.setVisible(false);
    }

    /*public void comboBoxTest() {
        ArrayList<CheckBox> arr = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arr.add(new CheckBox());
            arr.get(i).setText("panek");
        }

        ObservableList<CheckBox> obs = FXCollections.observableArrayList(arr);

        testi.setItems(obs);
        testi.setMaxHeight(100);
    }*/

    public void showTable(ResultSet rows, Set<String> columns) {
        try {
            tableView.getItems().clear();
            tableView.getColumns().clear();
            tableData.clear();
            for (int i = 0; i < rows.getMetaData().getColumnCount(); i++) {
                final int j = i;
                if (!columns.contains(rows.getMetaData().getColumnName(i + 1))) {
                    continue;
                }
                TableColumn col = new TableColumn(rows.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
            }
            while (rows.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rows.getMetaData().getColumnCount(); i++) {
                    row.add(rows.getString(i));
                }
                tableData.add(row);

            }
            tableView.setItems(tableData);
        } catch (SQLException e) {}
    }

}
