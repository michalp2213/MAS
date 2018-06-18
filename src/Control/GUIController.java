package Control;

import Model.Tables;
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
import javafx.util.Pair;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GUIController {
    public TableView tableView;
    public VBox tableButtons;
    public Button rolePracownikowButton;
    public Button specjalizacjeLekarzyButton;
    public Button wizytyPlanowaneButton;
    public Button wizytyOdbyteButton;
    public Button skierowaniaButton;
    public VBox pracownicyChoiceMenu;
    public Button showPracownicyTableButton;
    public Button modifyPracownicyTableButton;
    public VBox pacjenciChoiceMenu;
    public Button showPacjenciTableButton;
    public Button modifyPacjenciTableButton;
    public VBox rolePracownikowChoiceMenu;
    public Button showRolePracownikowTableButton;
    public Button modifyRolePracownikowTableButton;
    public VBox specjalizacjeLekarzyChoiceMenu;
    public Button showSpecjalizacjeLekarzyTableButton;
    public Button modifySpecjalizacjeLekarzyTableButton;
    public VBox LPKChoiceMenu;
    public Button showLPKTableButton;
    public Button modifyLPKTableButton;
    public VBox wizytyPlanowaneChoiceMenu;
    public Button showWizytyPlanowaneTableButton;
    public Button modifyWizytyPlanowaneTableButton;
    public VBox wizytyOdbyteChoiceMenu;
    public Button showWizytyOdbyteTableButton;
    public Button modifyWizytyOdbyteTableButton;
    public VBox skierowaniaChoiceMenu;
    public Button showSkierowaniaTableButton;
    public Button modifySkierowaniaTableButton;
    public VBox historiaMedycznaChoiceMenu;
    public Button showHistoriaMedycznaTableButton;
    public Button modifyHistoriaMedycznaTableButton;
    public VBox ankietyChoiceMenu;
    public Button showAnkietyTableButton;
    public Button modifyAnkietyTableButton;
    public VBox roleChoiceMenu;
    public Button showRoleTableButton;
    public Button modifyRoleTableButton;
    public VBox specjalizacjeChoiceMenu;
    public Button showSpecjalizacjeTableButton;
    public Button modifySpecjalizacjeTableButton;
    public VBox wydarzeniaMedyczneChoiceMenu;
    public Button showWydarzeniaMedyczneTableButton;
    public Button modifyWydarzeniaMedyczneTableButton;
    public VBox celeWizytChoiceMenu;
    public Button showCeleWizytTableButton;
    public Button modifyCeleWizytTableButton;
    public GridPane wizytyPlanowaneMenu;
    public Button closeWizytyPlanowaneButton;
    public ComboBox wizytyPlanowanePacjenciComboBox;
    public ComboBox wizytyPlanowaneLekarzeComboBox;
    public ComboBox wizytyPlanowaneCelComboBox;
    public ComboBox wizytyPlanowaneSpecjalizacjeComboBox;
    public TextField wizytyPlanowaneDataField;
    public TextField wizytyPlanowaneSzacowanyCzasField;
    public Button wizytyPlanowaneInsertButton;
    public Button wizytyPlanowaneUpdateButton;
    public ListView wizytyPlanowaneList;
    public Button wizytyPlanowaneDeleteButton;
    public GridPane wizytyOdbyteMenu;
    public Button closeWizytyOdbyteButton;
    public ComboBox wizytyOdbytePacjenciComboBox;
    public ComboBox wizytyOdbyteLekarzeComboBox;
    public ComboBox wizytyOdbyteCelComboBox;
    public ComboBox wizytyOdbyteSpecjalizacjeComboBox;
    public TextField wizytyOdbyteDataField;
    public TextField wizytyOdbyteCzasTrwaniaField;
    public Button wizytyOdbyteInsertButton;
    public ComboBox wizytyOdbyteBox;
    public ComboBox wizytyPlanowaneBox;
    public Button wizytyOdbyteUpdateButton;
    public ListView wizytyOdbyteList;
    public Button wizytyOdbyteDeleteButton;
    public GridPane skierowaniaMenu;
    public Button closeSkierowaniaButton;
    public ComboBox skierowaniaWizytyComboBox;
    public ComboBox skierowaniaSpecjalizacjeComboBox;
    public ComboBox skierowaniaCeleComboBox;
    public TextField skierowaniaOpisDataField;
    public Button skierowaniaInsertButton;
    public ComboBox skierowaniaBox;
    public Button skierowaniaUpdateButton;
    public ListView skierowaniaList;
    public Button skierowaniaDeleteButton;
    public GridPane historiaMedycznaMenu;
    public Button closeHistoriaMedycznaButton;
    public ComboBox historiaMedycznaPacjenciComboBox;
    public ComboBox historiaMedycznaWydarzeniaComboBox;
    public ComboBox historiaMedycznaWizytyComboBox;
    public TextField historiaMedycznaOdDataField;
    public TextField historiaMedycznaDoDataField;
    public Button historiaMedycznaInsertButton;
    public ComboBox historiaMedycznaBox;
    public Button historiaMedycznaUpdateButton;
    public ListView historiaMedycznaList;
    public Button historiaMedycznaDeleteButton;
    public GridPane ankietyMenu;
    public Button closeAnkietyButton;
    public ComboBox ankietyLekarzeComboBox;
    public TextField ankietyDataDataField;
    public ComboBox ankietyUprzejmoscComboBox;
    public ComboBox ankietyOpanowanieComboBox;
    public ComboBox ankietyInformacyjnosComboBox;
    public ComboBox ankietyDokladnoscBadanComboBox;
    public Button ankietyInsertButton;
    public ComboBox ankietyBox;
    public Button ankietyUpdateButton;
    public ListView ankietyList;
    public Button ankietyDeleteButton;

    private ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();

    public Button historiaMedycznaButton;
    public Button wizytyButton;
    public Button ankietyButton;

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
    public Button pracownicyUpdateButton;
    public ComboBox pracownicyBox;

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
    public Button pacjenciUpdateButton;
    public ComboBox pacjenciBox;

    public GridPane pracownicyRoleMenu;
    public ComboBox pracownicyRolePracownicyComboBox;
    public Button closePracownicyRoleButton;
    public ListView pracownicyRoleRoleList;
    public Button pracownicyRoleDeleteButton;
    public ComboBox pracownicyRoleRoleComboBox;
    public Button pracownicyRoleInsertButton;

    public GridPane lekarzeSpecjalizacjeMenu;
    public ComboBox lekarzeSpecjalizacjeLekarzeComboBox;
    public Button closeLekarzeSpecjalizacjeButton;
    public ListView lekarzeSpecjalizacjeRoleList;
    public Button lekarzeSpecjalizacjeDeleteButton;
    public ComboBox lekarzeSpecjalizacjeRoleComboBox;
    public Button lekarzeSpecjalizacjeInsertButton;

    public Button LPKButton;
    public GridPane LPKMenu;
    public Button closeLPKButton;
    public ComboBox LPKPacjenciComboBox;
    public ComboBox LPKLekarzeComboBox;
    public Button LPKUpdateButton;
    public ListView LPKList;

    public Button roleButton;
    public GridPane roleMenu;
    public Button closeRoleMenuButton;
    public TextField roleNazwaField;
    public Button roleInsertButton;
    public ComboBox roleBox;
    public Button roleUpdateButton;
    public ListView roleList;
    public Button roleDeleteButton;

    public Button specjalizacjeButton;
    public Button specjalizacjeDeleteButton;
    public ListView specjalizacjeList;
    public Button specjalizacjeUpdateButton;
    public ComboBox specjalizacjeBox;
    public Button specjalizacjeInsertButton;
    public TextField specjalizacjeNazwaField;
    public Button closeSpecjalizacjeMenuButton;
    public GridPane specjalizacjeMenu;

    public Button celeWizytButton;
    public GridPane celeWizytMenu;
    public Button closeCeleWizytMenuButton;
    public TextField celeWizytNazwaField;
    public Button celeWizytInsertButton;
    public ComboBox celeWizytBox;
    public Button celeWizytUpdateButton;
    public ListView celeWizytList;
    public Button celeWizytDeleteButton;

    public Button wydarzeniaMedyczneButton;
    public GridPane wydarzeniaMedyczneMenu;
    public Button closeWydarzeniaMedyczneMenuButton;
    public TextField wydarzeniaMedyczneNazwaField;
    public ComboBox wydarzeniaMedyczneBox;
    public Button wydarzeniaMedyczneInsertButton;
    public Button wydarzeniaMedyczneUpdateButton;
    public ListView wydarzeniaMedyczneList;
    public Button wydarzeniaMedyczneDeleteButton;

    @FXML
    public void showPracownicyChoiceMenu() {
        pracownicyChoiceMenu.setVisible(true);
        pracownicyChoiceMenu.toFront();
    }

    @FXML
    public void hidePracownicyChoiceMenu() {
        pracownicyChoiceMenu.setVisible(false);
    }

    @FXML
    public void showPacjenciChoiceMenu() {
        pacjenciChoiceMenu.setVisible(true);
        pacjenciChoiceMenu.toFront();
    }

    @FXML
    public void hidePacjenciChoiceMenu() {
        pacjenciChoiceMenu.setVisible(false);
    }

    @FXML
    public void showRolePracownikowChoiceMenu() {
        rolePracownikowChoiceMenu.setVisible(true);
        rolePracownikowChoiceMenu.toFront();
    }

    @FXML
    public void hideRolePracownikowChoiceMenu() {
        rolePracownikowChoiceMenu.setVisible(false);
    }

    @FXML
    public void showSpecjalizacjeLekarzyChoiceMenu() {
        specjalizacjeLekarzyChoiceMenu.setVisible(true);
        specjalizacjeLekarzyChoiceMenu.toFront();
    }

    @FXML
    public void hideSpecjalizacjeLekarzyChoiceMenu() {
        specjalizacjeLekarzyChoiceMenu.setVisible(false);
    }

    @FXML
    public void showLPKChoiceMenu() {
        LPKChoiceMenu.setVisible(true);
        LPKChoiceMenu.toFront();
    }

    @FXML
    public void hideLPKChoiceMenu() {
        LPKChoiceMenu.setVisible(false);
    }

    @FXML
    public void showWizytyPlanowaneChoiceMenu() {
        wizytyPlanowaneChoiceMenu.setVisible(true);
        wizytyPlanowaneChoiceMenu.toFront();
    }

    @FXML
    public void hideWizytyPlanowaneChoiceMenu() {
        wizytyPlanowaneChoiceMenu.setVisible(false);
    }

    @FXML
    public void showWizytyOdbyteChoiceMenu() {
        wizytyOdbyteChoiceMenu.setVisible(true);
        wizytyOdbyteChoiceMenu.toFront();
    }

    @FXML
    public void hideWizytyOdbyteChoiceMenu() {
        wizytyOdbyteChoiceMenu.setVisible(false);
    }

    @FXML
    public void showSkierowaniaChoiceMenu() {
        skierowaniaChoiceMenu.setVisible(true);
        skierowaniaChoiceMenu.toFront();
    }

    @FXML
    public void hideSkierowaniaChoiceMenu() {
        skierowaniaChoiceMenu.setVisible(false);
    }

    @FXML
    public void showHistoriaMedycznaChoiceMenu() {
        historiaMedycznaChoiceMenu.setVisible(true);
        historiaMedycznaChoiceMenu.toFront();
    }

    @FXML
    public void hideHistoriaMedycznaChoiceMenu() {
        historiaMedycznaChoiceMenu.setVisible(false);
    }

    @FXML
    public void showAnkietyChoiceMenu() {
        ankietyChoiceMenu.setVisible(true);
        ankietyChoiceMenu.toFront();
    }

    @FXML
    public void hideAnkietyChoiceMenu() {
        ankietyChoiceMenu.setVisible(false);
    }

    @FXML
    public void showRoleChoiceMenu() {
        roleChoiceMenu.setVisible(true);
        roleChoiceMenu.toFront();
    }

    @FXML
    public void hideRoleChoiceMenu() {
        roleChoiceMenu.setVisible(false);
    }

    @FXML
    public void showSpecjalizacjeChoiceMenu() {
        specjalizacjeChoiceMenu.setVisible(true);
        specjalizacjeChoiceMenu.toFront();
    }

    @FXML
    public void hideSpecjalizacjeChoiceMenu() {
        specjalizacjeChoiceMenu.setVisible(false);
    }

    @FXML
    public void showWydarzeniaMedyczneChoiceMenu() {
        wydarzeniaMedyczneChoiceMenu.setVisible(true);
        wydarzeniaMedyczneChoiceMenu.toFront();
    }

    @FXML
    public void hideWydarzeniaMedyczneChoiceMenu() {
        wydarzeniaMedyczneChoiceMenu.setVisible(false);
    }

    @FXML
    public void showCeleWizytChoiceMenu() {
        celeWizytChoiceMenu.setVisible(true);
        celeWizytChoiceMenu.toFront();
    }

    @FXML
    public void hideCeleWizytChoiceMenu() {
        celeWizytChoiceMenu.setVisible(false);
    }

    @FXML
    public void showPracownicyMenu() {
        hideAllMenus();
        pracownicyMenu.setVisible(true);
        pracownicyMenu.toFront();
    }

    @FXML
    public void hidePracownicyMenu() {
        pracownicyMenu.setVisible(false);
    }

    @FXML
    public void showPacjenciMenu() {
        hideAllMenus();
        pacjenciMenu.setVisible(true);
        pacjenciMenu.toFront();
    }

    @FXML
    public void hidePacjenciMenu() {
        pacjenciMenu.setVisible(false);
    }

    @FXML
    public void showPracownicyRoleMenu() {
        hideAllMenus();
        pracownicyRoleMenu.setVisible(true);
        pracownicyRoleMenu.toFront();
    }

    @FXML
    public void hidePracownicyRoleMenu() {
        pracownicyRoleMenu.setVisible(false);
    }

    @FXML
    public void showLekarzeSpecjalizacjeMenu() {
        hideAllMenus();
        lekarzeSpecjalizacjeMenu.setVisible(true);
        lekarzeSpecjalizacjeMenu.toFront();
    }

    @FXML
    public void hideLekarzeSpecjalizacjeMenu() {
        lekarzeSpecjalizacjeMenu.setVisible(false);
    }

    @FXML
    public void showLPKMenu() {
        hideAllMenus();
        LPKMenu.setVisible(true);
        LPKMenu.toFront();
    }

    @FXML
    public void hideLPKMenu() {
        LPKMenu.setVisible(false);
    }

    @FXML
    public void showWizytyPlanowaneMenu() {
        hideAllMenus();
        wizytyPlanowaneMenu.setVisible(true);
        wizytyPlanowaneMenu.toFront();
    }

    @FXML
    public void hideWizytyPlanowaneMenu() {
        wizytyPlanowaneMenu.setVisible(false);
    }

    @FXML
    public void showWizytyOdbyteMenu() {
        hideAllMenus();
        wizytyOdbyteMenu.setVisible(true);
        wizytyOdbyteMenu.toFront();
    }

    @FXML
    public void hideWizytyOdbyteMenu() {
        wizytyOdbyteMenu.setVisible(false);
    }

    @FXML
    public void showSkierowaniaMenu() {
        hideAllMenus();
        skierowaniaMenu.setVisible(true);
        skierowaniaMenu.toFront();
    }

    @FXML
    public void hideSkierowaniaMenu() {
        skierowaniaMenu.setVisible(false);
    }

    @FXML
    public void showHistoriaMedycznaMenu() {
        hideAllMenus();
        historiaMedycznaMenu.setVisible(true);
        historiaMedycznaMenu.toFront();
    }

    @FXML
    public void hideHistoriaMedycznaMenu() {
        historiaMedycznaMenu.setVisible(false);
    }

    @FXML
    public void showAnkietyMenu() {
        hideAllMenus();
        ankietyMenu.setVisible(true);
        ankietyMenu.toFront();
    }

    @FXML
    public void hideAnkietyMenu() {
        ankietyMenu.setVisible(false);
    }

    @FXML
    public void showRoleMenu() {
        hideAllMenus();
        roleMenu.setVisible(true);
        roleMenu.toFront();
    }

    @FXML
    public void hideRoleMenu() {
        roleMenu.setVisible(false);
    }

    @FXML
    public void showSpecjalizacjeMenu() {
        hideAllMenus();
        specjalizacjeMenu.setVisible(true);
        specjalizacjeMenu.toFront();
    }

    @FXML
    public void hideSpecjalizacjeMenu() {
        specjalizacjeMenu.setVisible(false);
    }

    @FXML
    public void showCeleWizytMenu() {
        hideAllMenus();
        celeWizytMenu.setVisible(true);
        celeWizytMenu.toFront();
    }

    @FXML
    public void hideCeleWizytMenu() {
        celeWizytMenu.setVisible(false);
    }

    @FXML
    public void showWydarzeniaMedyczneMenu() {
        hideAllMenus();
        wydarzeniaMedyczneMenu.setVisible(true);
        wydarzeniaMedyczneMenu.toFront();
    }

    @FXML
    public void hideWydarzeniaMedyczneMenu() {
        wydarzeniaMedyczneMenu.setVisible(false);
    }

    public void hideAllMenus() {
        hidePracownicyMenu();
        hidePracownicyChoiceMenu();
        hidePacjenciMenu();
        hidePacjenciChoiceMenu();
        hidePracownicyRoleMenu();
        hideRolePracownikowChoiceMenu();
        hideLekarzeSpecjalizacjeMenu();
        hideSpecjalizacjeLekarzyChoiceMenu();
        hideLPKMenu();
        hideLPKChoiceMenu();
        hideWizytyPlanowaneMenu();
        hideWizytyPlanowaneChoiceMenu();
        hideWizytyOdbyteMenu();
        hideWizytyOdbyteChoiceMenu();
        hideSkierowaniaMenu();
        hideSkierowaniaChoiceMenu();
        hideHistoriaMedycznaMenu();
        hideHistoriaMedycznaChoiceMenu();
        hideAnkietyMenu();
        hideAnkietyChoiceMenu();
        hideRoleMenu();
        hideRoleChoiceMenu();
        hideSpecjalizacjeMenu();
        hideSpecjalizacjeChoiceMenu();
        hideWydarzeniaMedyczneMenu();
        hideWydarzeniaMedyczneChoiceMenu();
        hideCeleWizytMenu();
        hideCeleWizytChoiceMenu();
    }

    @FXML
    public void showTablePracownicy() {
        hideAllMenus();
        showTable(Tables.pracownicy.getContents(), null);
    }

    @FXML
    public void showTablePacjenci() {
        hideAllMenus();
        showTable(Tables.pacjenci.getContents(), null);
    }

    @FXML
    public void showTablePracownicyRole() {
        hideAllMenus();
        showTable(Tables.pracownicy_role.getContents(), null);
    }

    @FXML
    public void showTableLekarzeSpecjalizacje() {
        hideAllMenus();
        showTable(Tables.lekarze_specjalizacje.getContents(), null);
    }

    @FXML
    public void showTableLPK() {
        hideAllMenus();
        showTable(Tables.pacjenci_lpk.getContents(), null);
    }

    @FXML
    public void showTableWizytyPlanowane() {
        hideAllMenus();
        showTable(Tables.wizyty_planowane.getContents(), null);
    }

    @FXML
    public void showTableWizytyOdbyte() {
        hideAllMenus();
        showTable(Tables.wizyty_odbyte.getContents(), null);
    }

    @FXML
    public void showTableSkierowania() {
        hideAllMenus();
        showTable(Tables.skierowania.getContents(), null);
    }

    @FXML
    public void showTableHistoriaMedyczna() {
        hideAllMenus();
        showTable(Tables.historia_medyczna.getContents(), null);
    }

    @FXML
    public void showTableAnkiety() {
        hideAllMenus();
        showTable(Tables.ankiety_lekarze.getContents(), null);
    }

    @FXML
    public void showTableRole() {
        hideAllMenus();
        showTable(Tables.role.getContents(), null);
    }

    @FXML
    public void showTableSpecjalizacje() {
        hideAllMenus();
        showTable(Tables.specjalizacje.getContents(), null);
    }

    @FXML
    public void showTableWydarzeniaMedyczne() {
        hideAllMenus();
        showTable(Tables.wydarzenia_medyczne.getContents(), null);
    }

    @FXML
    public void showTableCeleWizyt() {
        hideAllMenus();
        showTable(Tables.cele_wizyty.getContents(), null);
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

    public void showTable(ArrayList<ArrayList<String>> rows, Set<String> columns) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableData.clear();
        int i = 0;
        for (String colName : rows.get(0)) {
            if (columns != null && !columns.contains(colName)) {
                continue;
            }
            TableColumn col = new TableColumn(colName);
            col.prefWidthProperty().bind(tableView.widthProperty().divide(
                    columns != null ? columns.size() : rows.get(0).size()
            ).subtract(i == 0 ? 20 : 0));
            int j = i ;
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j) == null ? "NULL" : param.getValue().get(j).toString());
                }
            });

            tableView.getColumns().addAll(col);
            i++;
        }
        for (int j = 1; j < rows.size(); j++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int k = 0; k < rows.get(j).size(); k++) {
                if (columns != null && !columns.contains(rows.get(0).get(k))) {
                    continue;
                }
                row.add(rows.get(j).get(k));
            }
            tableData.add(row);
        }
        tableView.setItems(tableData);
        tableView.refresh();
    }

    public void updateListView(ListView view, ArrayList<ArrayList<String>> tab) {
        view.getItems().clear();
        for (int i = 1; i < tab.size(); i++) {
            //view.getItems().add(new Pair<>(tab.get(i), new CheckBox()));
            CheckBox b = new CheckBox();
            b.setText(tab.get(i).toString());
            view.getItems().add(b);
        }
        view.refresh();
    }

    public String[] toStringArray(String s){
        return s.substring(1, s.length() - 1).split(", ");
    }
}
