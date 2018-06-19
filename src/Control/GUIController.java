package Control;

import Model.Database;
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
import org.postgresql.util.PGInterval;

import java.awt.event.ActionEvent;
import java.sql.Date;
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
    public TextField skierowaniaWizytyComboBox;
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
    public TextField historiaMedycznaWizytyComboBox;
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
    public ComboBox ankietyInformacyjnoscComboBox;
    public ComboBox ankietyDokladnoscBadanComboBox;
    public Button ankietyInsertButton;
    public ComboBox ankietyBox;
    public Button ankietyUpdateButton;
    public ListView ankietyList;
    public Button ankietyDeleteButton;
    public Button LPKDeleteButton;
    public Button rankingiButton;
    public GridPane rankingiMenu;
    public Button closeRankingiMenuButton;
    public TextField rankingiOdField;
    public TextField rankingiDoField;
    public ComboBox rankingiTypBox;
    public ComboBox rankingiOpcjeBox;
    public Button showRankingButton;

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
    public ComboBox pacjenciPlecField;
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
        updatePracownicyMenu();
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
        updatePacjenciMenu();
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
        updatePracownicyRoleMenu();
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
        updateLekarzeSpecjalizacjeMenu();
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
        updateLPKMenu();
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
        updateWizytyPlanowaneMenu();
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
        updateWizytyOdbyteMenu();
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
        updateSkierowaniaMenu();
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
        updateHistoriaMedycznaMenu();
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
        updateAnkietyMenu();
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
        updateRoleMenu();
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
        updateSpecjalizacjeMenu();
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
        updateCeleWizytMenu();
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
        updateWydarzeniaMedyczneMenu();
        wydarzeniaMedyczneMenu.setVisible(true);
        wydarzeniaMedyczneMenu.toFront();
    }

    @FXML
    public void hideWydarzeniaMedyczneMenu() {
        wydarzeniaMedyczneMenu.setVisible(false);
    }

    @FXML
    public void showRankingiMenu() {
        hideAllMenus();
        updateRankingiMenu();
        rankingiMenu.setVisible(true);
        rankingiMenu.toFront();
    }

    @FXML
    public void hideRankingiMenu() {
        rankingiMenu.setVisible(false);
        rankingiOpcjeBox.setVisible(false);
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
        hideRankingiMenu();
    }

    @FXML
    public void showTablePracownicy() {
        hideAllMenus();
        showTable(Tables.pracownicy.getContents());
    }

    @FXML
    public void showTablePacjenci() {
        hideAllMenus();
        showTable(Tables.pacjenci.getContents());
    }

    @FXML
    public void showTablePracownicyRole() {
        hideAllMenus();
        showTable(Tables.pracownicy_role.getContents());
    }

    @FXML
    public void showTableLekarzeSpecjalizacje() {
        hideAllMenus();
        showTable(Tables.lekarze_specjalizacje.getContents());
    }

    @FXML
    public void showTableLPK() {
        hideAllMenus();
        showTable(Tables.pacjenci_lpk.getContents());
    }

    @FXML
    public void showTableWizytyPlanowane() {
        hideAllMenus();
        showTable(Tables.wizyty_planowane.getContents());
    }

    @FXML
    public void showTableWizytyOdbyte() {
        hideAllMenus();
        showTable(Tables.wizyty_odbyte.getContents());
    }

    @FXML
    public void showTableSkierowania() {
        hideAllMenus();
        showTable(Tables.skierowania.getContents());
    }

    @FXML
    public void showTableHistoriaMedyczna() {
        hideAllMenus();
        showTable(Tables.historia_medyczna.getContents());
    }

    @FXML
    public void showTableAnkiety() {
        hideAllMenus();
        showTable(Tables.ankiety_lekarze.getContents());
    }

    @FXML
    public void showTableRole() {
        hideAllMenus();
        showTable(Tables.role.getContents());
    }

    @FXML
    public void showTableSpecjalizacje() {
        hideAllMenus();
        showTable(Tables.specjalizacje.getContents());
    }

    @FXML
    public void showTableWydarzeniaMedyczne() {
        hideAllMenus();
        showTable(Tables.wydarzenia_medyczne.getContents());
    }

    @FXML
    public void showTableCeleWizyt() {
        hideAllMenus();
        showTable(Tables.cele_wizyty.getContents());
    }

    private void updatePracownicyMenuVolatile() {
        updateListView(pracownicyList, Tables.pracownicy.getContents());
        updateComboBox(pracownicyBox, Tables.pracownicy.getContents());
    }

    private void updatePracownicyMenu() {
        updatePracownicyMenuVolatile();
    }

    private void updatePacjenciMenuVolatile() {
        updateListView(pacjenciList, Tables.pacjenci.getContents());
        updateComboBox(pacjenciBox, Tables.pacjenci.getContents());
    }

    private void updatePacjenciMenu() {
        updatePacjenciMenuVolatile();
        ObservableList<String> l = FXCollections.observableArrayList();
        l.addAll("M", "F");
        pacjenciPlecField.setItems(l);
    }

    private void updatePracownicyRoleMenuVolatile() {
        updatePracownicyRoleMenuListView();
    }

    @FXML
    private void updatePracownicyRoleMenuListView() {
        if (pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem() != null) {
            ArrayList<ArrayList<String>> roles = Database.executeQuery(
                    "select r.nazwa " +
                            "from role r " +
                            "where r.id_roli in ( " +
                            "select pr.id_roli " +
                            "from pracownicy_role pr " +
                            "where pr.id_pracownika = " +
                            toStringArray(pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem().toString())[0] +
                            ");"
            );
            updateListView(pracownicyRoleRoleList, roles);
        }
    }

    private void updatePracownicyRoleMenu() {
        pracownicyRoleRoleList.getItems().clear();
        updateComboBox(pracownicyRolePracownicyComboBox, filterTable(Tables.pracownicy.getContents(), "id_pracownika", "imie", "nazwisko"));
        updateComboBox(pracownicyRoleRoleComboBox, filterTable(Tables.role.getContents(), "nazwa"));
    }

    private void updateLekarzeSpecjalizacjeMenuVolatile() {
        updateLekarzeSpecjalizacjeMenuListView();
    }

    @FXML
    private void updateLekarzeSpecjalizacjeMenuListView() {
        if (lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem() != null) {
            ArrayList<ArrayList<String>> roles = Database.executeQuery(
                    "select s.nazwa " +
                            "from specjalizacje s " +
                            "where s.id_specjalizacji in ( " +
                            "select ls.id_specjalizacji " +
                            "from lekarze_specjalizacje ls " +
                            "where ls.id_lekarza = " +
                            toStringArray(lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0] +
                            ");"
            );
            updateListView(lekarzeSpecjalizacjeRoleList, roles);
        }
    }

    private void updateLekarzeSpecjalizacjeMenu() {
        lekarzeSpecjalizacjeRoleList.getItems().clear();
        updateComboBox(lekarzeSpecjalizacjeLekarzeComboBox, getSmallLekarze());
        updateComboBox(lekarzeSpecjalizacjeRoleComboBox, filterTable(Tables.specjalizacje.getContents(), "nazwa"));
    }

    private void updateLPKMenuVolatile() {
        updateListView(LPKList, Tables.pacjenci_lpk.getContents());
    }

    private void updateLPKMenu() {
        updateLPKMenuVolatile();
        updateComboBox(LPKLekarzeComboBox, getSmallLekarze());
        updateComboBox(LPKPacjenciComboBox, getSmallPacjenci());
    }

    private void updateWizytyPlanowaneMenuVolatile() {
        updateListView(wizytyPlanowaneList, Tables.wizyty_planowane.getContents());
        updateComboBox(wizytyPlanowaneBox, Tables.wizyty_planowane.getContents());
    }

    private void updateWizytyPlanowaneMenu() {
        updateWizytyPlanowaneMenuVolatile();
        updateComboBox(wizytyPlanowaneCelComboBox, filterTable(Tables.cele_wizyty.getContents(), "nazwa"));
        updateComboBox(wizytyPlanowaneLekarzeComboBox, getSmallLekarze());
        updateComboBox(wizytyPlanowanePacjenciComboBox, getSmallPacjenci());
        updateComboBox(wizytyPlanowaneSpecjalizacjeComboBox, filterTable(Tables.specjalizacje.getContents(), "nazwa"));
    }

    private void updateWizytyOdbyteMenuVolatile() {
        updateListView(wizytyOdbyteList, Tables.wizyty_odbyte.getContents());
        updateComboBox(wizytyOdbyteBox, Tables.wizyty_odbyte.getContents());
    }

    private void updateWizytyOdbyteMenu() {
        updateWizytyOdbyteMenuVolatile();
        updateComboBox(wizytyOdbyteCelComboBox, filterTable(Tables.cele_wizyty.getContents(), "nazwa"));
        updateComboBox(wizytyOdbyteLekarzeComboBox, getSmallLekarze());
        updateComboBox(wizytyOdbytePacjenciComboBox, getSmallPacjenci());
        updateComboBox(wizytyOdbyteSpecjalizacjeComboBox, filterTable(Tables.specjalizacje.getContents(), "nazwa"));
    }

    private void updateSkierowaniaMenuVolatile() {
        updateListView(skierowaniaList, Tables.skierowania.getContents());
        updateComboBox(skierowaniaBox, Tables.skierowania.getContents());
    }

    private void updateSkierowaniaMenu() {
        updateSkierowaniaMenuVolatile();
        updateComboBox(skierowaniaCeleComboBox, filterTable(Tables.cele_wizyty.getContents(), "nazwa"));
        updateComboBox(skierowaniaSpecjalizacjeComboBox, filterTable(Tables.specjalizacje.getContents(), "nazwa"));
    }

    private void updateHistoriaMedycznaMenuVolatile() {
        updateListView(historiaMedycznaList, Tables.historia_medyczna.getContents());
        updateComboBox(historiaMedycznaBox, Tables.historia_medyczna.getContents());
    }

    private void updateHistoriaMedycznaMenu() {
        updateHistoriaMedycznaMenuVolatile();
        updateComboBox(historiaMedycznaPacjenciComboBox, getSmallPacjenci());
        updateComboBox(historiaMedycznaWydarzeniaComboBox, filterTable(Tables.wydarzenia_medyczne.getContents(), "nazwa"));
    }

    private void updateAnkietyMenuVolatile() {
        updateListView(ankietyList, Tables.ankiety_lekarze.getContents());
        updateComboBox(ankietyBox, Tables.ankiety_lekarze.getContents());
    }

    private void updateAnkietyMenu() {
        updateAnkietyMenuVolatile();
        updateComboBox(ankietyLekarzeComboBox, getSmallLekarze());
        ObservableList<Integer> vals = FXCollections.observableArrayList();
        vals.addAll(1, 2, 3, 4, 5);
        ankietyDokladnoscBadanComboBox.setItems(vals);
        ankietyInformacyjnoscComboBox.setItems(vals);
        ankietyOpanowanieComboBox.setItems(vals);
        ankietyUprzejmoscComboBox.setItems(vals);
    }

    private void updateRoleMenuVolatile() {
        updateListView(roleList, Tables.role.getContents());
        updateComboBox(roleBox, Tables.role.getContents());
    }

    private void updateRoleMenu() {
        updateRoleMenuVolatile();
    }

    private void updateSpecjalizacjeMenuVolatile() {
        updateListView(specjalizacjeList, Tables.specjalizacje.getContents());
        updateComboBox(specjalizacjeBox, Tables.specjalizacje.getContents());
    }

    private void updateSpecjalizacjeMenu() {
        updateSpecjalizacjeMenuVolatile();
    }

    private void updateWydarzeniaMedyczneMenuVolatile() {
        updateListView(wydarzeniaMedyczneList, Tables.wydarzenia_medyczne.getContents());
        updateComboBox(wydarzeniaMedyczneBox, Tables.wydarzenia_medyczne.getContents());
    }

    private void updateWydarzeniaMedyczneMenu() {
        updateWydarzeniaMedyczneMenuVolatile();
    }

    private void updateCeleWizytMenuVolatile() {
        updateListView(celeWizytList, Tables.cele_wizyty.getContents());
        updateComboBox(celeWizytBox, Tables.cele_wizyty.getContents());
    }

    private void updateCeleWizytMenu() {
        updateCeleWizytMenuVolatile();
    }

    @FXML
    private void updateRankingiMenuOpcjeBox() {
        if (rankingiTypBox.getSelectionModel().getSelectedItem() == null) {
            rankingiOpcjeBox.setVisible(false);
            return;
        }
        String type = rankingiTypBox.getSelectionModel().getSelectedItem().toString();
        if (type.equals("Alfabetyczny")) {
            rankingiOpcjeBox.setVisible(false);
        } else if (type.equals("Według średniej")) {
            updateComboBox(rankingiOpcjeBox, filterTable(Tables.specjalizacje.getContents(), "nazwa"));
            rankingiOpcjeBox.setVisible(true);
        } else {
            ObservableList<String> opcjeItems = FXCollections.observableArrayList();
            opcjeItems.addAll("uprzejmość", "opanowanie", "informacyjność", "dokładność badań");
            rankingiOpcjeBox.setItems(opcjeItems);
            rankingiOpcjeBox.setVisible(true);
        }
    }

    private void updateRankingiMenu() {
        ObservableList<String> typItems = FXCollections.observableArrayList();
        typItems.addAll("Alfabetyczny", "Według średniej", "Według cechy");
        rankingiTypBox.setItems(typItems);
    }

    @FXML
    private void showRankingPressed() {
        if (rankingiTypBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        switch(rankingiTypBox.getSelectionModel().getSelectedItem().toString()) {
            case "Alfabetyczny":
                showTable(Tables.ankiety_lekarze.alphabeticRanking(
                        rankingiOdField.getText().equals("") ? null : Date.valueOf(rankingiOdField.getText()),
                        rankingiDoField.getText().equals("") ? null : Date.valueOf(rankingiDoField.getText())-
                ));
                break;
            case "Według średniej":
                if (rankingiOpcjeBox.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                showTable(Tables.ankiety_lekarze.bestAvg(
                        rankingiOdField.getText().equals("") ? null : Date.valueOf(rankingiOdField.getText()),
                        rankingiDoField.getText().equals("") ? null : Date.valueOf(rankingiDoField.getText()),
                        specNameToId(rankingiOpcjeBox.getSelectionModel().getSelectedItem().toString().substring(1,
                                rankingiOpcjeBox.getSelectionModel().getSelectedItem().toString().length() - 1))
                ));
                break;
            case "Według cechy":
                if (rankingiOpcjeBox.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                showTable(Tables.ankiety_lekarze.bestIn(
                        rankingiOdField.getText().equals("") ? null : Date.valueOf(rankingiOdField.getText()),
                        rankingiDoField.getText().equals("") ? null : Date.valueOf(rankingiDoField.getText()),
                        rankingiOpcjeBox.getSelectionModel().getSelectedItem().toString()
                ));
                break;
        }
        hideRankingiMenu();
    }

    @FXML
    private void pracownicyMenuInsertPressed() {
        Tables.pracownicy.insertItem(pracownicyImieField.getText(),
                pracownicyNazwiskoField.getText(),
                pracownicyPeselField.getText());
        updatePracownicyMenuVolatile();
    }

    @FXML
    private void pracownicyMenuUpdatePressed() {
        if (pracownicyBox.getSelectionModel().getSelectedItem() != null) {
            String[] fields = toStringArray(pracownicyBox.getSelectionModel().getSelectedItem().toString());
            Tables.pracownicy.updateItem(Integer.valueOf(fields[0]),
                    pracownicyImieField.getText().equals("") ? fields[1] : pracownicyImieField.getText(),
                    pracownicyNazwiskoField.getText().equals("") ? fields[2] : pracownicyNazwiskoField.getText(),
                    pracownicyPeselField.getText().equals("") ? fields[3] : pracownicyPeselField.getText()
            );
            updatePracownicyMenuVolatile();
        }
    }

    @FXML
    private void pracownicyMenuDeletePressed() {
        for (Object o : pracownicyList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.pacjenci.deleteItem(Integer.valueOf(toStringArray(b.getText())[0]));
            }
        }
        updatePracownicyMenuVolatile();
    }

    @FXML
    private void pacjenciMenuInsertPressed() {
        Tables.pacjenci.insertItem(pacjenciImieField.getText(),
                pacjenciNazwiskoField.getText(),
                pacjenciPeselField.getText(),
                pacjenciNrPaszportuField.getText(),
                Date.valueOf(pacjenciDataUrodzeniaField.getText()),
                pacjenciPlecField.getSelectionModel().getSelectedItem() == null ? null : pacjenciPlecField.getSelectionModel().getSelectedItem().toString());
        updatePacjenciMenuVolatile();
    }

    @FXML
    private void pacjenciMenuUpdatePressed() {
        if (pacjenciBox.getSelectionModel().getSelectedItem() != null) {
            String[] fields = toStringArray(pacjenciBox.getSelectionModel().getSelectedItem().toString());
            Tables.pacjenci.updateItem(Integer.valueOf(fields[0]),
                    pacjenciImieField.getText().equals("") ? fields[1] : pacjenciImieField.getText(),
                    pacjenciNazwiskoField.getText().equals("") ? fields[2] : pacjenciNazwiskoField.getText(),
                    pacjenciPeselField.getText().equals("") ? fields[3] : pacjenciPeselField.getText(),
                    pacjenciNrPaszportuField.getText().equals("") ? fields[4] : pacjenciNrPaszportuField.getText(),
                    Date.valueOf(pacjenciDataUrodzeniaField.getText()),
                    pacjenciPlecField.getSelectionModel().getSelectedItem() == null ? null : pacjenciPlecField.getSelectionModel().getSelectedItem().toString()

            );
            updatePacjenciMenuVolatile();
        }
    }

    @FXML
    private void pacjenciMenuDeletePressed() {
        for (Object o : pacjenciList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.pacjenci.deleteItem(Integer.valueOf(toStringArray(b.getText())[0]));
            }
        }
        updatePacjenciMenuVolatile();
    }

    @FXML
    private void pracownicyRoleMenuInsertPressed() {
        if (pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem() == null ||
                pracownicyRoleRoleComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String chosenRole = pracownicyRoleRoleComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                pracownicyRoleRoleComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        Integer id_prac = Integer.valueOf(
                toStringArray(pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Tables.pracownicy_role.insertItem(chosenRole, id_prac);
        updatePracownicyRoleMenuVolatile();
    }

    @FXML
    private void pracownicyRoleMenuDeletePressed() {
        if (pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Integer id_prac = Integer.valueOf(
                toStringArray(pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        for (Object o : pracownicyRoleRoleList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                String chosenRole = b.getText().substring(1, b.getText().length() - 1);
                Tables.pracownicy_role.deleteItem(chosenRole, id_prac);
            }
        }
        updatePracownicyRoleMenuVolatile();
    }

    @FXML
    private void lekarzeSpecjalizacjeMenuInsertPressed() {
        if (lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem() == null ||
                lekarzeSpecjalizacjeRoleComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String chosenRole = lekarzeSpecjalizacjeRoleComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                lekarzeSpecjalizacjeRoleComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        Integer id_lek = Integer.valueOf(
                toStringArray(lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Tables.lekarze_specjalizacje.insertItem(chosenRole, id_lek);
        updateLekarzeSpecjalizacjeMenuVolatile();
    }

    @FXML
    private void lekarzeSpecjalizacjeMenuDeletePressed() {
        if (lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Integer id_lek = Integer.valueOf(
                toStringArray(lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        for (Object o : lekarzeSpecjalizacjeRoleList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                String chosenRole = b.getText().substring(1, b.getText().length() - 1);
                Tables.lekarze_specjalizacje.deleteItem(chosenRole, id_lek);
            }
        }
        updateLekarzeSpecjalizacjeMenuVolatile();
    }

    @FXML
    private void LPKMenuInsertPressed() {
        if (LPKPacjenciComboBox.getSelectionModel().getSelectedItem() == null ||
                LPKLekarzeComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Integer id_pac = Integer.valueOf(
                toStringArray(LPKPacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Integer id_lek = Integer.valueOf(
                toStringArray(LPKLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Tables.pacjenci_lpk.insertItem(id_pac, id_lek);
        updateLPKMenuVolatile();
    }

    @FXML
    private void LPKMenuDeletePressed() {
        for (Object o : LPKList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Integer id_pac = Integer.valueOf(
                        toStringArray(b.getText())[0]);
                Date data = Date.valueOf(
                        toStringArray(b.getText())[2]);
                Tables.pacjenci_lpk.deleteItem(id_pac, data);
            }
        }
        updateLPKMenuVolatile();
    }

    @FXML
    private void wizytyPlanowaneMenuInsertPressed() {
        if (wizytyPlanowanePacjenciComboBox.getSelectionModel().getSelectedItem() == null ||
                wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem() == null ||
                wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Integer id_pac = Integer.valueOf(
                toStringArray(wizytyPlanowanePacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Integer id_lek = wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem() == null ? null : Integer.valueOf(
                toStringArray(wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        String cel = wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        String spec = wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        Date data = Date.valueOf(wizytyPlanowaneDataField.getText());
        Tables.wizyty_planowane.insertItem(id_pac, cel, spec, data);
        updateWizytyPlanowaneMenuVolatile();
    }

    @FXML
    private void wizytyPlanowaneMenuUpdatePressed() {
        if (wizytyPlanowaneBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(wizytyPlanowaneBox.getSelectionModel().getSelectedItem().toString());
        Integer id_pac = wizytyPlanowanePacjenciComboBox.getSelectionModel().getSelectedItem() == null ? null : Integer.valueOf(
                toStringArray(wizytyPlanowanePacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Integer id_lek = wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem() == null ? null : Integer.valueOf(
                toStringArray(wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        String cel = wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem() == null ? null :
                wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        String spec = wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null ? null :
                wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        Date data = wizytyPlanowaneDataField.getText().equals("") ? null : Date.valueOf(wizytyPlanowaneDataField.getText());
        try {
            PGInterval interval = wizytyPlanowaneSzacowanyCzasField.getText() == null ? null : new PGInterval(wizytyPlanowaneSzacowanyCzasField.getText());
            Tables.wizyty_planowane.updateItem(Integer.valueOf(fields[0]),
                    id_pac == null ? Integer.valueOf(fields[1]) : id_pac,
                    id_lek == null ? Integer.valueOf(fields[2]) : id_lek,
                    cel == null ? celNameToId(fields[3]) : celNameToId(cel),
                    spec == null ? specNameToId(fields[4]) : specNameToId(spec),
                    data == null ? Date.valueOf(fields[5]) : data,
                    interval == null ? new PGInterval(fields[6]) : interval
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateWizytyPlanowaneMenuVolatile();
    }

    @FXML
    public void wizytyPlanowaneMenuDeletePressed() {
        for (Object o : wizytyPlanowaneList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Integer id_wiz = Integer.valueOf(
                        toStringArray(b.getText())[0]);
                Tables.wizyty_planowane.deleteItem(id_wiz);
            }
        }
        updateWizytyPlanowaneMenuVolatile();
    }

    @FXML
    public void wizytyOdbyteMenuUpdatePressed() {
        if (wizytyOdbyteBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(wizytyOdbyteBox.getSelectionModel().getSelectedItem().toString());
        Integer id_pac = wizytyOdbytePacjenciComboBox.getSelectionModel().getSelectedItem() == null ? null : Integer.valueOf(
                toStringArray(wizytyOdbytePacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Integer id_lek = wizytyOdbyteLekarzeComboBox.getSelectionModel().getSelectedItem() == null ? null : Integer.valueOf(
                toStringArray(wizytyOdbyteLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        String cel = wizytyOdbyteCelComboBox.getSelectionModel().getSelectedItem() == null ? null :
                wizytyOdbyteCelComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        wizytyOdbyteCelComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        String spec = wizytyOdbyteSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null ? null :
                wizytyOdbyteSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        wizytyOdbyteSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
        Date data = wizytyOdbyteDataField.getText().equals("") ? null : Date.valueOf(wizytyOdbyteDataField.getText());
        try {
            PGInterval interval = wizytyOdbyteCzasTrwaniaField.getText() == null ? null : new PGInterval(wizytyOdbyteCzasTrwaniaField.getText());
            Tables.wizyty_odbyte.updateItem(Integer.valueOf(fields[0]),
                    id_pac == null ? Integer.valueOf(fields[1]) : id_pac,
                    id_lek == null ? Integer.valueOf(fields[2]) : id_lek,
                    cel == null ? celNameToId(fields[3]) : celNameToId(cel),
                    spec == null ? specNameToId(fields[4]) : specNameToId(spec),
                    data == null ? Date.valueOf(fields[5]) : data,
                    interval == null ? new PGInterval(fields[6]) : interval
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateWizytyOdbyteMenuVolatile();
    }

    @FXML
    private void skierowaniaMenuInsertPressed() {
        if (skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null ||
                skierowaniaCeleComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Tables.skierowania.insertItem(Integer.valueOf(skierowaniaWizytyComboBox.getText()),
                specNameToId(skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().length() - 1)),
                celNameToId(skierowaniaCeleComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        skierowaniaCeleComboBox.getSelectionModel().getSelectedItem().toString().length() - 1)),
                skierowaniaOpisDataField.getText()
        );
        updateSkierowaniaMenuVolatile();
    }

    @FXML
    private void skierowaniaMenuUpdatePressed() {
        if (skierowaniaBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(skierowaniaBox.getSelectionModel().getSelectedItem().toString());
        Tables.skierowania.updateItem(Integer.valueOf(fields[0]),
                skierowaniaWizytyComboBox.getText().equals("") ? Integer.valueOf(fields[1]) : Integer.valueOf(skierowaniaWizytyComboBox.getText()),
                skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null ? specNameToId(fields[2]) : specNameToId(skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().length() - 1)),
                skierowaniaCeleComboBox.getSelectionModel().getSelectedItem() == null ? celNameToId(fields[3]) : celNameToId(skierowaniaCeleComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        skierowaniaCeleComboBox.getSelectionModel().getSelectedItem().toString().length() - 1)),
                skierowaniaOpisDataField.getText().equals("") ? fields[4] : skierowaniaOpisDataField.getText()
        );
        updateSkierowaniaMenuVolatile();
    }

    @FXML
    private void skierowaniaMenuDeletePressed() {
        for (Object o : skierowaniaList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.skierowania.deleteItem(Integer.valueOf(toStringArray(b.getText())[0]));
            }
        }
        updateSkierowaniaMenuVolatile();
    }

    @FXML
    private void historiaMedycznaMenuInsertPressed() {
        //todo
        /*if (historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem() == null ||
                historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Integer pacjent_id = Integer.valueOf(toStringArray(historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Integer wydarzenie_id = wydarzenieNameToId(historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem().toString().length() - 1));
        Date from = Date.valueOf(historiaMedycznaOdDataField.getText());
        Date to = historiaMedycznaDoDataField.getText().equals("") ? null : Date.valueOf(historiaMedycznaDoDataField.getText());
        Integer id_wizyty = historiaMedycznaWizytyComboBox.getText().equals("") ? null : Integer.valueOf(historiaMedycznaWizytyComboBox.getText());
        Tables.historia_medyczna.insertItem(pacjent_id,
                wydarzenie_id,
                from,
                to,
                id_wizyty
        );
        updateHistoriaMedycznaMenuVolatile();*/
    }

    @FXML
    private void historiaMedycznaMenuUpdatePressed() {
        //todo
        /*if (historiaMedycznaBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(historiaMedycznaBox.getSelectionModel().getSelectedItem().toString());
        Integer pacjent_id = historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem() == null ? Integer.valueOf(
                toStringArray(historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0]);
        Integer wydarzenie_id = wydarzenieNameToId(historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem().toString().length() - 1));
        Date from = Date.valueOf(historiaMedycznaOdDataField.getText());
        Date to = historiaMedycznaDoDataField.getText().equals("") ? null : Date.valueOf(historiaMedycznaDoDataField.getText());
        Integer id_wizyty = historiaMedycznaWizytyComboBox.getText().equals("") ? null : Integer.valueOf(historiaMedycznaWizytyComboBox.getText());
        Tables.skierowania.updateItem(Integer.valueOf(fields[0]),
                skierowaniaWizytyComboBox.getText().equals("") ? Integer.valueOf(fields[1]) : Integer.valueOf(skierowaniaWizytyComboBox.getText()),
                skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null ? specNameToId(fields[2]) : specNameToId(skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem().toString().length() - 1)),
                skierowaniaCeleComboBox.getSelectionModel().getSelectedItem() == null ? celNameToId(fields[3]) : celNameToId(skierowaniaCeleComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                        skierowaniaCeleComboBox.getSelectionModel().getSelectedItem().toString().length() - 1)),
                skierowaniaOpisDataField.getText().equals("") ? fields[4] : skierowaniaOpisDataField.getText()
        );
        updateSkierowaniaMenuVolatile();*/
    }

    @FXML
    private void historiaMedycznaMenuDeletePressed() {
        //todo
        /*for (Object o : skierowaniaList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.skierowania.deleteItem(Integer.valueOf(toStringArray(b.getText())[0]));
            }
        }
        updateSkierowaniaMenuVolatile();*/
    }

    @FXML
    private void ankietyMenuInsertPressed() {
        if (ankietyLekarzeComboBox.getSelectionModel().getSelectedItem() == null ||
                ankietyDataDataField.getText().equals("")) {
            return;
        }
        Integer id_lekarza = Integer.valueOf(
                toStringArray(ankietyLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]
        );
        Date data = Date.valueOf(ankietyDataDataField.getText());
        Integer up = ankietyUprzejmoscComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyUprzejmoscComboBox.getSelectionModel().getSelectedItem().toString());
        Integer op = ankietyOpanowanieComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyOpanowanieComboBox.getSelectionModel().getSelectedItem().toString());
        Integer inf = ankietyInformacyjnoscComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyInformacyjnoscComboBox.getSelectionModel().getSelectedItem().toString());
        Integer dok = ankietyDokladnoscBadanComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyDokladnoscBadanComboBox.getSelectionModel().getSelectedItem().toString());
        Tables.ankiety_lekarze.insertItem(id_lekarza,
                data,
                up,
                op,
                inf,
                dok
        );
        updateAnkietyMenuVolatile();
    }

    @FXML
    private void ankietyMenuUpdatePressed() {
        if (ankietyBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Integer id_lekarza = ankietyLekarzeComboBox.getSelectionModel().getSelectedItem() == null ? null : Integer.valueOf(
                toStringArray(ankietyLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0]
        );
        Date data = ankietyDataDataField.getText().equals("") ? null : Date.valueOf(ankietyDataDataField.getText());
        Integer up = ankietyUprzejmoscComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyUprzejmoscComboBox.getSelectionModel().getSelectedItem().toString());
        Integer op = ankietyOpanowanieComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyOpanowanieComboBox.getSelectionModel().getSelectedItem().toString());
        Integer inf = ankietyInformacyjnoscComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyInformacyjnoscComboBox.getSelectionModel().getSelectedItem().toString());
        Integer dok = ankietyDokladnoscBadanComboBox.getSelectionModel().getSelectedItem() == null ? null :
                Integer.valueOf(ankietyDokladnoscBadanComboBox.getSelectionModel().getSelectedItem().toString());
        String[] fields = toStringArray(ankietyBox.getSelectionModel().getSelectedItem().toString());
        //todo
        /*Tables.ankiety_lekarze.updateItem(Integer.valueOf(fields[0]),
                id_lekarza == null ? Integer.valueOf(fields[1]) : id_lekarza,
                data == null ? Date.valueOf(fields[2]) : data,
                up == null ? Integer.valueOf(fields[3]) : up,
                op == null ? Integer.valueOf(fields[4]) : op,
                inf == null ? Integer.valueOf(fields[5]) : inf,
                dok == null ? Integer.valueOf(fields[6]) : dok
        );*/
        updateAnkietyMenuVolatile();
    }

    @FXML
    private void ankietyMenuDeletePressed() {
        for (Object o : ankietyList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.ankiety_lekarze.deleteItem(Integer.valueOf(toStringArray(b.getText())[0]));
            }
        }
        updateAnkietyMenuVolatile();
    }

    @FXML
    private void roleMenuInsertPressed() {
        Tables.role.insertItem(roleNazwaField.getText());
        updateRoleMenuVolatile();
    }

    @FXML
    private void roleMenuUpdatePressed() {
        if (roleBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(roleBox.getSelectionModel().getSelectedItem().toString());
        Tables.role.updateItem(fields[1],
                roleNazwaField.getText()
        );
        updateRoleMenuVolatile();
    }

    @FXML
    private void roleMenuDeletePressed() {
        for (Object o : roleList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.role.deleteItem(toStringArray(b.getText())[1]);
            }
        }
        updateRoleMenuVolatile();
    }

    @FXML
    private void specjalizacjeMenuInsertPressed() {
        Tables.specjalizacje.insertItem(specjalizacjeNazwaField.getText());
        updateSpecjalizacjeMenuVolatile();
    }

    @FXML
    private void specjalizacjeMenuUpdatePressed() {
        if (specjalizacjeBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(specjalizacjeBox.getSelectionModel().getSelectedItem().toString());
        Tables.specjalizacje.updateItem(fields[1],
                specjalizacjeNazwaField.getText()
        );
        updateSpecjalizacjeMenuVolatile();
    }

    @FXML
    private void specjalizacjeMenuDeletePressed() {
        for (Object o : specjalizacjeList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.specjalizacje.deleteItem(toStringArray(b.getText())[1]);
            }
        }
        updateSpecjalizacjeMenuVolatile();
    }

    @FXML
    private void wydarzeniaMedyczneMenuInsertPressed() {
        Tables.wydarzenia_medyczne.insertItem(wydarzeniaMedyczneNazwaField.getText());
        updateWydarzeniaMedyczneMenuVolatile();
    }

    @FXML
    private void wydarzeniaMedyczneMenuUpdatePressed() {
        if (wydarzeniaMedyczneBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(wydarzeniaMedyczneBox.getSelectionModel().getSelectedItem().toString());
        Tables.wydarzenia_medyczne.updateItem(fields[1],
                wydarzeniaMedyczneNazwaField.getText()
        );
        updateWydarzeniaMedyczneMenuVolatile();
    }

    @FXML
    private void wydarzeniaMedyczneMenuDeletePressed() {
        for (Object o : wydarzeniaMedyczneList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.wydarzenia_medyczne.deleteItem(toStringArray(b.getText())[1]);
            }
        }
        updateWydarzeniaMedyczneMenuVolatile();
    }

    @FXML
    private void celeWizytMenuInsertPressed() {
        Tables.cele_wizyty.insertItem(celeWizytNazwaField.getText());
        updateCeleWizytMenuVolatile();
    }

    @FXML
    private void celeWizytMenuUpdatePressed() {
        if (celeWizytBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] fields = toStringArray(celeWizytBox.getSelectionModel().getSelectedItem().toString());
        System.out.println(celeWizytNazwaField.getText() + " " + fields[1]);
        Tables.cele_wizyty.updateItem(fields[1],
                celeWizytNazwaField.getText()
        );
        updateCeleWizytMenuVolatile();
    }

    @FXML
    private void celeWizytMenuDeletePressed() {
        for (Object o : celeWizytList.getItems()) {
            CheckBox b = (CheckBox) o;
            if (b.isSelected()) {
                Tables.cele_wizyty.deleteItem(toStringArray(b.getText())[1]);
            }
        }
        updateCeleWizytMenuVolatile();
    }

    private ArrayList<ArrayList<String>> filterTable(ArrayList<ArrayList<String>> rows, String... cols) {
        Set<String> chosenColumns = new HashSet<>();
        for (String s : cols) {
            chosenColumns.add(s);
        }
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        toReturn.add(new ArrayList<>());
        for (String colName : rows.get(0)) {
            if (chosenColumns.contains(colName)) {
                toReturn.get(0).add(colName);
            }
        }
        for (int i = 1; i < rows.size(); i++) {
            toReturn.add(new ArrayList<>());
            for (int j = 0; j < rows.get(i).size(); j++) {
                if (chosenColumns.contains(rows.get(0).get(j))) {
                    toReturn.get(i).add(rows.get(i).get(j));
                }
            }
        }
        return toReturn;
    }

    public void showTable(ArrayList<ArrayList<String>> rows) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableData.clear();
        int i = 0;
        for (String colName : rows.get(0)) {
            TableColumn col = new TableColumn(colName);
            col.prefWidthProperty().bind(tableView.widthProperty().divide(
                    rows.get(0).size()).subtract(i == 0 ? 20 : 0));
            int j = i;
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
                row.add(rows.get(j).get(k));
            }
            tableData.add(row);
        }
        tableView.setItems(tableData);
        tableView.refresh();
    }

    public void updateListView(ListView view, ArrayList<ArrayList<String>> tab) {
        view.getItems().clear();
        if (tab != null) {
            for (int i = 1; i < tab.size(); i++) {
                //view.getItems().add(new Pair<>(tab.get(i), new CheckBox()));
                CheckBox b = new CheckBox();
                b.setText(tab.get(i).toString());
                view.getItems().add(b);
            }
        }
        view.refresh();
    }

    public void updateComboBox(ComboBox box, ArrayList<ArrayList<String>> tab) {
        box.getItems().clear();
        if (tab != null) {
            for (int i = 1; i < tab.size(); i++) {
                box.getItems().add(tab.get(i).toString());
            }
        }
    }

    public String[] toStringArray(String s) {
        return s.substring(1, s.length() - 1).split(", ");
    }

    public ArrayList<ArrayList<String>> getSmallLekarze() {
        return Database.executeQuery("SELECT id_pracownika, imie, nazwisko from pracownicy p " +
                "where p.id_pracownika in ( " +
                "select pr.id_pracownika " +
                "from pracownicy_role pr " +
                "where pr.id_roli = ( " +
                "select r.id_roli " +
                "from role r " +
                "where r.nazwa = 'Lekarz'));");
    }

    public ArrayList<ArrayList<String>> getSmallPacjenci() {
        return filterTable(Tables.pacjenci.getContents(), "id_pacjenta", "imie", "nazwisko");
    }

    public Integer celNameToId(String cel) {
        return Integer.valueOf(Database.executeQuery(
                "select c.id_celu " +
                        "from cele c " +
                        "where c.nazwa = " +
                        cel +
                        ";"
        ).get(1).get(0));
    }

    public Integer specNameToId(String spec) {
        return Integer.valueOf(Database.executeQuery(
                "select s.id_specjalizacji " +
                        "from specjalizacje s " +
                        "where s.nazwa = '" +
                        spec + "'" +
                        ";"
        ).get(1).get(0));
    }

    public Integer wydarzenieNameToId(String wydarzenie) {
        return Integer.valueOf(Database.executeQuery(
                "select w.id_wydarzenia " +
                        "from wydarzenia_medyczne w " +
                        "where w.nazwa = " +
                        wydarzenie +
                        ";"
        ).get(1).get(0));
    }
}
