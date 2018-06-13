package Control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class GUIController {
    public HBox mainMenuButtons;
    
    public Button historiaMedycznaButton;
    public Button wizytyButton;
    public Button ankietyButton;

    public Button modyfikujOsobyButton;
    public VBox tabeleOsoby;
    public Button pracownicyButton;
    public Button pacjenciButton;

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

    public TableView tableView;
    private ObservableList<ObservableList> tableData = FXCollections.observableArrayList();

    @FXML
    public void showTabeleOsoby() {
        tabeleOsoby.setVisible(true);
        tabeleOsoby.toFront();
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
