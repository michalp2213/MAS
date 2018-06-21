package Control;

import Model.Database;
import Model.Tables;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
    public Button wizytyOdbyteInsertButton;
    public ComboBox wizytyOdbyteBox;
    public ComboBox wizytyPlanowaneBox;
    public Button wizytyOdbyteUpdateButton;
    public ListView wizytyOdbyteList;
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
    public TextField pracownicyZatrudnionyOdField;
    public TextField pracownicyZatrudnionyDoField;
    public ComboBox wizytyOdbytePlanowaneComboBox;
    public TextField wizytyOdbytePlanowaneCzasField;
    public ComboBox wizytyOdbyteOdbyteComboBox;
    public TextField wizytyOdbyteOdbyteCzasField;
    public Label errorMessage;

    private ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();

    public Button historiaMedycznaButton;
    public Button ankietyButton;

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

    private void hideAllMenus() {
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
        try {
        hideAllMenus();
        showTable(Tables.pracownicy.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTablePacjenci() {
        try {
        hideAllMenus();
        showTable(Tables.pacjenci.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTablePracownicyRole() {
        try {
        hideAllMenus();
        showTable(Tables.pracownicy_role.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableLekarzeSpecjalizacje() {
        try {
        hideAllMenus();
        showTable(Tables.lekarze_specjalizacje.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableLPK() {
        try {
        hideAllMenus();
        showTable(Tables.pacjenci_lpk.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableWizytyPlanowane() {
        try {
        hideAllMenus();
        showTable(Tables.wizyty_planowane.getContents(5));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableWizytyOdbyte() {
        try {
        hideAllMenus();
        showTable(Tables.wizyty_odbyte.getContents(5));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableSkierowania() {
        try {
        hideAllMenus();
        showTable(Tables.skierowania.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableHistoriaMedyczna() {
        try {
        hideAllMenus();
        showTable(Tables.historia_medyczna.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableAnkiety() {
        try {
        hideAllMenus();
        showTable(Tables.ankiety_lekarze.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableRole() {
        try {
            hideAllMenus();
            showTable(Tables.role.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableSpecjalizacje() {
        try {
            hideAllMenus();
            showTable(Tables.specjalizacje.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableWydarzeniaMedyczne() {
        try {
            hideAllMenus();
            showTable(Tables.wydarzenia_medyczne.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void showTableCeleWizyt() {
        try {
            hideAllMenus();
            showTable(Tables.cele_wizyty.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updatePracownicyMenuVolatile() {
        try {
            updateListView(pracownicyList, Tables.pracownicy.getContents(1));
            updateComboBox(pracownicyBox, Tables.pracownicy.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updatePracownicyMenu() {
        updatePracownicyMenuVolatile();
    }

    private void updatePacjenciMenuVolatile() {
        try {
            updateListView(pacjenciList, Tables.pacjenci.getContents(1));
            updateComboBox(pacjenciBox, Tables.pacjenci.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
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
        try {
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
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updatePracownicyRoleMenu() {
        try {
            pracownicyRoleRoleList.getItems().clear();
            updateComboBox(pracownicyRolePracownicyComboBox, filterTable(Tables.pracownicy.getContents(1), "id_pracownika", "imie", "nazwisko"));
            updateComboBox(pracownicyRoleRoleComboBox, filterTable(Tables.role.getContents(2), "nazwa"));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateLekarzeSpecjalizacjeMenuVolatile() {
        updateLekarzeSpecjalizacjeMenuListView();
    }

    @FXML
    private void updateLekarzeSpecjalizacjeMenuListView() {
        try {
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
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateLekarzeSpecjalizacjeMenu() {
        try {
            lekarzeSpecjalizacjeRoleList.getItems().clear();
            updateComboBox(lekarzeSpecjalizacjeLekarzeComboBox, getSmallLekarze());
            updateComboBox(lekarzeSpecjalizacjeRoleComboBox, filterTable(Tables.specjalizacje.getContents(2), "nazwa"));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateLPKMenuVolatile() {
        try {
            updateListView(LPKList, Tables.pacjenci_lpk.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateLPKMenu() {
        updateLPKMenuVolatile();
        updateComboBox(LPKLekarzeComboBox, getSmallLekarze());
        updateComboBox(LPKPacjenciComboBox, getSmallPacjenci());
    }

    @FXML
    private void updateWizytyPlanowaneMenuSpecjalizacjeComboBox() {
        if (wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem() == null ) {
            return;
        }
        String id_lek = toStringArray(wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0];
        updateComboBox(wizytyPlanowaneSpecjalizacjeComboBox, getSpecLekarza(id_lek));
    }

    private void updateWizytyPlanowaneMenuVolatile() {
        try {
            updateListView(wizytyPlanowaneList, Tables.wizyty_planowane.getContents(6));
            updateComboBox(wizytyPlanowaneBox, Tables.wizyty_planowane.getContents(6));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateWizytyPlanowaneMenu() {
        try {
            updateWizytyPlanowaneMenuVolatile();
            updateComboBox(wizytyPlanowaneCelComboBox, filterTable(Tables.cele_wizyty.getContents(2), "nazwa"));
            updateComboBox(wizytyPlanowaneLekarzeComboBox, getSmallLekarze());
            updateComboBox(wizytyPlanowanePacjenciComboBox, getSmallPacjenci());
            updateComboBox(wizytyPlanowaneSpecjalizacjeComboBox, filterTable(Tables.specjalizacje.getContents(2), "nazwa"));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateWizytyOdbyteMenuVolatile() {
        try {
            updateComboBox(wizytyOdbytePlanowaneComboBox, Tables.wizyty_planowane.getContents(6));
            updateComboBox(wizytyOdbyteOdbyteComboBox, Tables.wizyty_odbyte.getContents(6));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateWizytyOdbyteMenu() {
        updateWizytyOdbyteMenuVolatile();
    }

    @FXML
    private void updateWizytyOdbytePlanowaneCzasField() {
        if (wizytyOdbytePlanowaneComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        wizytyOdbytePlanowaneCzasField.setText(
                toStringArray(wizytyOdbytePlanowaneComboBox.getSelectionModel().getSelectedItem().toString())[6]
        );
    }

    @FXML
    private void updateWizytyOdbyteOdbyteCzasField() {
        if (wizytyOdbyteOdbyteComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        wizytyOdbyteOdbyteCzasField.setText(
                toStringArray(wizytyOdbyteOdbyteComboBox.getSelectionModel().getSelectedItem().toString())[6]
        );
    }

    private void updateSkierowaniaMenuVolatile() {
        try {
            updateListView(skierowaniaList, Tables.skierowania.getContents(1));
            updateComboBox(skierowaniaBox, Tables.skierowania.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateSkierowaniaMenu() {
        try {
            updateSkierowaniaMenuVolatile();
            updateComboBox(skierowaniaCeleComboBox, filterTable(Tables.cele_wizyty.getContents(1), "nazwa"));
            updateComboBox(skierowaniaSpecjalizacjeComboBox, filterTable(Tables.specjalizacje.getContents(1), "nazwa"));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateHistoriaMedycznaMenuVolatile() {
        try {
            updateListView(historiaMedycznaList, Tables.historia_medyczna.getContents(1));
            updateComboBox(historiaMedycznaBox, Tables.historia_medyczna.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateHistoriaMedycznaMenu() {
        try {
            updateHistoriaMedycznaMenuVolatile();
            updateComboBox(historiaMedycznaPacjenciComboBox, getSmallPacjenci());
            updateComboBox(historiaMedycznaWydarzeniaComboBox, filterTable(Tables.wydarzenia_medyczne.getContents(2), "nazwa"));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateAnkietyMenuVolatile() {
        try {
            updateListView(ankietyList, Tables.ankiety_lekarze.getContents(1));
            updateComboBox(ankietyBox, Tables.ankiety_lekarze.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
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
        try {
            updateListView(roleList, Tables.role.getContents(1));
            updateComboBox(roleBox, Tables.role.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateRoleMenu() {
        updateRoleMenuVolatile();
    }

    private void updateSpecjalizacjeMenuVolatile() {
        try {
            updateListView(specjalizacjeList, Tables.specjalizacje.getContents(1));
            updateComboBox(specjalizacjeBox, Tables.specjalizacje.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateSpecjalizacjeMenu() {
        updateSpecjalizacjeMenuVolatile();
    }

    private void updateWydarzeniaMedyczneMenuVolatile() {
        try {
            updateListView(wydarzeniaMedyczneList, Tables.wydarzenia_medyczne.getContents(1));
            updateComboBox(wydarzeniaMedyczneBox, Tables.wydarzenia_medyczne.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateWydarzeniaMedyczneMenu() {
        updateWydarzeniaMedyczneMenuVolatile();
    }

    private void updateCeleWizytMenuVolatile() {
        try {
            updateListView(celeWizytList, Tables.cele_wizyty.getContents(1));
            updateComboBox(celeWizytBox, Tables.cele_wizyty.getContents(1));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateCeleWizytMenu() {
        updateCeleWizytMenuVolatile();
    }

    @FXML
    private void updateRankingiMenuOpcjeBox() {
        try {
        if (rankingiTypBox.getSelectionModel().getSelectedItem() == null) {
            rankingiOpcjeBox.setVisible(false);
            return;
        }
        String type = rankingiTypBox.getSelectionModel().getSelectedItem().toString();
        if (type.equals("Alfabetyczny")) {
            rankingiOpcjeBox.setVisible(false);
        } else if (type.equals("Według średniej")) {
            updateComboBox(rankingiOpcjeBox, filterTable(Tables.specjalizacje.getContents(2), "nazwa"));
            rankingiOpcjeBox.setVisible(true);
        } else {
            ObservableList<String> opcjeItems = FXCollections.observableArrayList();
            opcjeItems.addAll("uprzejmość", "opanowanie", "informacyjność", "dokładność badań");
            rankingiOpcjeBox.setItems(opcjeItems);
            rankingiOpcjeBox.setVisible(true);
        }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void updateRankingiMenu() {
        ObservableList<String> typItems = FXCollections.observableArrayList();
        typItems.addAll("Alfabetyczny", "Według średniej", "Według cechy");
        rankingiTypBox.setItems(typItems);
    }

    @FXML
    private void showRankingPressed() {
        try {
            if (rankingiTypBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz typ rankingu");
                return;
            }
            switch (rankingiTypBox.getSelectionModel().getSelectedItem().toString()) {
                case "Alfabetyczny":
                    showTable(Tables.ankiety_lekarze.alphabeticRanking(
                            toEmptyString(rankingiOdField.getText()),
                            toEmptyString(rankingiDoField.getText())
                    ));
                    break;
                case "Według średniej":
                    if (rankingiOpcjeBox.getSelectionModel().getSelectedItem() == null) {
                        return;
                    }
                    showTable(Tables.ankiety_lekarze.bestAvg(
                            toEmptyString(rankingiOdField.getText()),
                            toEmptyString(rankingiDoField.getText()),
                            specNameToId(rankingiOpcjeBox.getSelectionModel().getSelectedItem().toString().substring(1,
                                    rankingiOpcjeBox.getSelectionModel().getSelectedItem().toString().length() - 1))
                    ));
                    break;
                case "Według cechy":
                    if (rankingiOpcjeBox.getSelectionModel().getSelectedItem() == null) {
                        return;
                    }
                    showTable(Tables.ankiety_lekarze.bestIn(
                            toEmptyString(rankingiOdField.getText()),
                            toEmptyString(rankingiDoField.getText()),
                            rankingiOpcjeBox.getSelectionModel().getSelectedItem().toString()
                    ));
                    break;
            }
            hideRankingiMenu();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pracownicyMenuInsertPressed() {
        try {
            String imie = toEmptyString(pracownicyImieField.getText());
            String nazwisko = toEmptyString(pracownicyNazwiskoField.getText());
            String pesel = toEmptyString(pracownicyPeselField.getText());
            Tables.pracownicy.insertItem(imie,
                    nazwisko,
                    pesel
            );
            updatePracownicyMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pracownicyMenuUpdatePressed() {
        try {
            if (pracownicyBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz pracownika");
                return;
            }
            String imie = toEmptyString(pracownicyImieField.getText());
            String nazwisko = toEmptyString(pracownicyNazwiskoField.getText());
            String pesel = toEmptyString(pracownicyPeselField.getText());
            String zatrudniony_od = toEmptyString(pracownicyZatrudnionyOdField.getText());
            String zatrudniony_do = toEmptyString(pracownicyZatrudnionyDoField.getText());
            String[] fields = toStringArray(pracownicyBox.getSelectionModel().getSelectedItem().toString());
            Tables.pracownicy.updateItem(fields[0],
                    imie == null ? fields[1] : imie,
                    nazwisko == null ? fields[2] : nazwisko,
                    pesel == null ? fields[3] : pesel,
                    zatrudniony_od == null ? fields[4] : zatrudniony_od,
                    zatrudniony_do == null ? fields[5] : zatrudniony_do
            );
            updatePracownicyMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pracownicyMenuDeletePressed() {
        try {
            for (Object o : pracownicyList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.pracownicy.deleteItem(toStringArray(b.getText())[0]);
                }
            }
            updatePracownicyMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pacjenciMenuInsertPressed() {
        try {
            String imie = toEmptyString(pacjenciImieField.getText());
            String nazwisko = toEmptyString(pacjenciNazwiskoField.getText());
            String pesel = toEmptyString(pacjenciPeselField.getText());
            String nr_paszportu = toEmptyString(pacjenciNrPaszportuField.getText());
            String data_ur = toEmptyString(pacjenciDataUrodzeniaField.getText());
            String plec = toNullString(pacjenciPlecField.getSelectionModel().getSelectedItem());
            Tables.pacjenci.insertItem(imie,
                    nazwisko,
                    pesel,
                    nr_paszportu,
                    data_ur,
                    plec
            );
            updatePacjenciMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pacjenciMenuUpdatePressed() {
        try {
            if (pacjenciBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz pacjenta");
                return;
            }
            String imie = toEmptyString(pacjenciImieField.getText());
            String nazwisko = toEmptyString(pacjenciNazwiskoField.getText());
            String pesel = toEmptyString(pacjenciPeselField.getText());
            String nr_paszportu = toEmptyString(pacjenciNrPaszportuField.getText());
            String data_ur = toEmptyString(pacjenciDataUrodzeniaField.getText());
            String plec = toNullString(pacjenciPlecField.getSelectionModel().getSelectedItem());
            String[] fields = toStringArray(pacjenciBox.getSelectionModel().getSelectedItem().toString());
            Tables.pacjenci.updateItem(fields[0],
                    imie == null ? fields[1] : imie,
                    nazwisko == null ? fields[2] : nazwisko,
                    pesel == null ? fields[3] : pesel,
                    nr_paszportu == null ? fields[4] : nr_paszportu,
                    data_ur == null ? fields[5] : data_ur,
                    plec == null ? fields[6] : plec
            );
            updatePacjenciMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pacjenciMenuDeletePressed() {
        try {
            for (Object o : pacjenciList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.pacjenci.deleteItem(toStringArray(b.getText())[0]);
                }
            }
            updatePacjenciMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pracownicyRoleMenuInsertPressed() {
        try {
            if (pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem() == null ||
                    pracownicyRoleRoleComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz pracownika i rolę");
                return;
            }
            String chosenRole = pracownicyRoleRoleComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                    pracownicyRoleRoleComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
            String id_prac = toStringArray(pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem().toString())[0];
            Tables.pracownicy_role.insertItem(chosenRole, id_prac);
            updatePracownicyRoleMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void pracownicyRoleMenuDeletePressed() {
        try {
            if (pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz pracownika");
                return;
            }
            String id_prac = toStringArray(pracownicyRolePracownicyComboBox.getSelectionModel().getSelectedItem().toString())[0];
            for (Object o : pracownicyRoleRoleList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    String chosenRole = b.getText().substring(1, b.getText().length() - 1);
                    Tables.pracownicy_role.deleteItem(chosenRole, id_prac);
                }
            }
            updatePracownicyRoleMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void lekarzeSpecjalizacjeMenuInsertPressed() {
        try {
            if (lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem() == null ||
                    lekarzeSpecjalizacjeRoleComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz lekarza i specjalizację");
                return;
            }
            String chosenRole = lekarzeSpecjalizacjeRoleComboBox.getSelectionModel().getSelectedItem().toString().substring(1,
                    lekarzeSpecjalizacjeRoleComboBox.getSelectionModel().getSelectedItem().toString().length() - 1);
            String id_lek = toStringArray(lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0];
            Tables.lekarze_specjalizacje.insertItem(chosenRole, id_lek);
            updateLekarzeSpecjalizacjeMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void lekarzeSpecjalizacjeMenuDeletePressed() {
        try {
            if (lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz lekarza");
                return;
            }
            String id_lek = toStringArray(lekarzeSpecjalizacjeLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0];
            for (Object o : lekarzeSpecjalizacjeRoleList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    String chosenRole = b.getText().substring(1, b.getText().length() - 1);
                    Tables.lekarze_specjalizacje.deleteItem(chosenRole, id_lek);
                }
            }
            updateLekarzeSpecjalizacjeMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void LPKMenuInsertPressed() {
        try {
            if (LPKPacjenciComboBox.getSelectionModel().getSelectedItem() == null ||
                    LPKLekarzeComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz pacjenta i lekarza");
                return;
            }
            String id_pac = toStringArray(LPKPacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0];
            String id_lek = toStringArray(LPKLekarzeComboBox.getSelectionModel().getSelectedItem().toString())[0];
            Tables.pacjenci_lpk.insertItem(id_pac, id_lek);
            updateLPKMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void LPKMenuDeletePressed() {
        try {
            for (Object o : LPKList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    String id_pac = toStringArray(b.getText())[0];
                    String data = toStringArray(b.getText())[2];
                    Tables.pacjenci_lpk.deleteItem(id_pac, data);
                }
            }
            updateLPKMenuVolatile();
        } catch (SQLException e ) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void wizytyPlanowaneMenuInsertPressed() {
        try {
            if (wizytyPlanowanePacjenciComboBox.getSelectionModel().getSelectedItem() == null ||
                    wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem() == null ||
                    wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz pacjenta, cel i specjalizację");
                return;
            }
            String id_pac = toStringArray(toNullString(wizytyPlanowanePacjenciComboBox.getSelectionModel().getSelectedItem()))[0];
            String id_lek = toNullString(wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem());
            String cel = cutBraces(toNullString(wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem()));
            String spec = cutBraces(toNullString(wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem()));
            String data = toEmptyString(wizytyPlanowaneDataField.getText());
            Tables.wizyty_planowane.insertItem(id_pac,
                    cel,
                    spec,
                    data
            );
            updateWizytyPlanowaneMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void wizytyPlanowaneMenuUpdatePressed() {
        try {
            if (wizytyPlanowaneBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz wizytę");
                return;
            }
            String id_pac = toNullString(wizytyPlanowanePacjenciComboBox.getSelectionModel().getSelectedItem());
            String id_lek = toNullString(wizytyPlanowaneLekarzeComboBox.getSelectionModel().getSelectedItem());
            String cel = cutBraces(toNullString(wizytyPlanowaneCelComboBox.getSelectionModel().getSelectedItem()));
            String spec = cutBraces(toNullString(wizytyPlanowaneSpecjalizacjeComboBox.getSelectionModel().getSelectedItem()));
            String data = toEmptyString(wizytyPlanowaneDataField.getText());
            String interval = toEmptyString(wizytyPlanowaneSzacowanyCzasField.getText());
            String[] fields = toStringArray(wizytyPlanowaneBox.getSelectionModel().getSelectedItem().toString());
            Tables.wizyty_planowane.updateItem(fields[0],
                    id_pac == null ? fields[1] : id_pac,
                    id_lek == null ? fields[2] : id_lek,
                    cel == null ? celNameToId(fields[3]) : celNameToId(cel),
                    spec == null ? specNameToId(fields[4]) : specNameToId(spec),
                    data == null ? fields[5] : data,
                    interval == null ? fields[6] : interval
            );
            updateWizytyPlanowaneMenuVolatile();
        } catch (SQLException e ) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void wizytyPlanowaneMenuDeletePressed() {
        try {
            for (Object o : wizytyPlanowaneList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    String id_wiz = toStringArray(b.getText())[0];
                    Tables.wizyty_planowane.deleteItem(id_wiz);
                }
            }
            updateWizytyPlanowaneMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void wizytyOdbyteMenuUpdatePressed() {
        try {
            if (wizytyOdbyteOdbyteComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz wizytę");
                return;
            }
            String czas = toEmptyString(wizytyOdbyteOdbyteCzasField.getText());
            String[] fields = toStringArray(wizytyOdbyteOdbyteComboBox.getSelectionModel().getSelectedItem().toString());
            Tables.wizyty_odbyte.updateItem(fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4],
                    fields[5],
                    czas == null ? fields[6] : czas
            );
            updateWizytyOdbyteMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void wizytyOdbyteMenuInsertPressed() {
        try {
            if (wizytyOdbytePlanowaneComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz wizytę");
                return;
            }
            String id_wiz = toStringArray(wizytyOdbytePlanowaneComboBox.getSelectionModel().getSelectedItem().toString())[0];
            String czas = wizytyOdbytePlanowaneCzasField.getText();
            Tables.wizyty_planowane.moveToWizytyOdbyte(id_wiz, czas);
            updateWizytyOdbyteMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void skierowaniaMenuInsertPressed() {
        try {
            if (skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null ||
                    skierowaniaCeleComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz specjalizację i cel");
                return;
            }
            String wiz_id = toEmptyString(skierowaniaWizytyComboBox.getText());
            String spec_id = specNameToId(cutBraces(toNullString(skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem())));
            String cel_id = celNameToId(cutBraces(toNullString(skierowaniaCeleComboBox.getSelectionModel().getSelectedItem())));
            String opis = toEmptyString(skierowaniaOpisDataField.getText());
            Tables.skierowania.insertItem(wiz_id,
                    spec_id,
                    cel_id,
                    opis
            );
            updateSkierowaniaMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void skierowaniaMenuUpdatePressed() {
        try {
            if (skierowaniaBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz skierowanie");
                return;
            }
            String wiz_id = toEmptyString(skierowaniaWizytyComboBox.getText());
            String spec_id = skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem() == null ? null :
                    specNameToId(cutBraces(toNullString(skierowaniaSpecjalizacjeComboBox.getSelectionModel().getSelectedItem())));
            String cel_id = celNameToId(cutBraces(toNullString(skierowaniaCeleComboBox.getSelectionModel().getSelectedItem())));
            String opis = toEmptyString(skierowaniaOpisDataField.getText());
            String[] fields = toStringArray(skierowaniaBox.getSelectionModel().getSelectedItem().toString());
            Tables.skierowania.updateItem(fields[0],
                    wiz_id == null ? fields[1] : wiz_id,
                    spec_id == null ? fields[2] : spec_id,
                    cel_id == null ? fields[3] : cel_id,
                    opis == null ? fields[4] : opis
            );
            updateSkierowaniaMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void skierowaniaMenuDeletePressed() {
        try {
            for (Object o : skierowaniaList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.skierowania.deleteItem(toStringArray(b.getText())[0]);
                }
            }
            updateSkierowaniaMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void historiaMedycznaMenuInsertPressed() {
        try {
            if (historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem() == null ||
                    historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz pacjenta i wydarzenie");
                return;
            }
            String pacjent_id = toStringArray(historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0];
            String wydarzenie_id = toStringArray(historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem().toString())[0];
            String from = toEmptyString(historiaMedycznaOdDataField.getText());
            String to = toEmptyString(historiaMedycznaDoDataField.getText());
            String id_wizyty = toEmptyString(historiaMedycznaWizytyComboBox.getText());
            Tables.historia_medyczna.insertItem(pacjent_id,
                    wydarzenie_id,
                    id_wizyty,
                    from,
                    to
            );
            updateHistoriaMedycznaMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void historiaMedycznaMenuUpdatePressed() {
        try {
            if (historiaMedycznaBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz wpis historii");
                return;
            }
            String[] fields = toStringArray(historiaMedycznaBox.getSelectionModel().getSelectedItem().toString());
            String pacjent_id = historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem() == null ? null :
                    toStringArray(historiaMedycznaPacjenciComboBox.getSelectionModel().getSelectedItem().toString())[0];
            String wydarzenie_id = historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem() == null ? null :
                    toStringArray(historiaMedycznaWydarzeniaComboBox.getSelectionModel().getSelectedItem().toString())[0];
            String from = toEmptyString(historiaMedycznaOdDataField.getText());
            String to = toEmptyString(historiaMedycznaDoDataField.getText());
            String id_wizyty = toEmptyString(historiaMedycznaWizytyComboBox.getText());
            Tables.historia_medyczna.updateItem(fields[0],
                    fields[1],
                    fields[3],
                    pacjent_id == null ? fields[0] : pacjent_id,
                    wydarzenie_id == null ? fields[1] : wydarzenie_id,
                    id_wizyty == null ? fields[2] : id_wizyty,
                    from == null ? fields[3] : from,
                    to == null ? fields[4] : to
            );
            updateSkierowaniaMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void historiaMedycznaMenuDeletePressed() {
        try {
            for (Object o : historiaMedycznaList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    String[] fields = toStringArray(b.getText());
                    Tables.historia_medyczna.deleteItem(fields[0], fields[1], fields[3]);
                }
            }
            updateSkierowaniaMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void ankietyMenuInsertPressed() {
        try {
            String id_lekarza = toNullString(ankietyLekarzeComboBox.getSelectionModel().getSelectedItem());
            String data = toEmptyString(ankietyDataDataField.getText());
            String up = toNullString(ankietyUprzejmoscComboBox.getSelectionModel().getSelectedItem());
            String op = toNullString(ankietyOpanowanieComboBox.getSelectionModel().getSelectedItem());
            String inf = toNullString(ankietyInformacyjnoscComboBox.getSelectionModel().getSelectedItem());
            String dok = toNullString(ankietyDokladnoscBadanComboBox.getSelectionModel().getSelectedItem());
            Tables.ankiety_lekarze.insertItem(id_lekarza,
                    data,
                    up,
                    op,
                    inf,
                    dok
            );
            updateAnkietyMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void ankietyMenuUpdatePressed() {
        try {
            if (ankietyBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz ankietę");
                return;
            }
            String id_lekarza = toNullString(ankietyLekarzeComboBox.getSelectionModel().getSelectedItem());
            String data = toEmptyString(ankietyDataDataField.getText());
            String up = toNullString(ankietyUprzejmoscComboBox.getSelectionModel().getSelectedItem());
            String op = toNullString(ankietyOpanowanieComboBox.getSelectionModel().getSelectedItem());
            String inf = toNullString(ankietyInformacyjnoscComboBox.getSelectionModel().getSelectedItem());
            String dok = toNullString(ankietyDokladnoscBadanComboBox.getSelectionModel().getSelectedItem());
            String[] fields = toStringArray(ankietyBox.getSelectionModel().getSelectedItem().toString());
            Tables.ankiety_lekarze.updateItem(fields[0],
                    id_lekarza == null ? fields[1] : id_lekarza,
                    data == null ? fields[2] : data,
                    up == null ? fields[3] : up,
                    op == null ? fields[4] : op,
                    inf == null ? fields[5] : inf,
                    dok == null ? fields[6] : dok
            );
            updateAnkietyMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void ankietyMenuDeletePressed() {
        try {
            for (Object o : ankietyList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.ankiety_lekarze.deleteItem(toStringArray(b.getText())[0]);
                }
            }
            updateAnkietyMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void roleMenuInsertPressed() {
        try {
            Tables.role.insertItem(toEmptyString(roleNazwaField.getText()));
            updateRoleMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void roleMenuUpdatePressed() {
        try {
            if (roleBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz rolę");
                return;
            }
            String[] fields = toStringArray(roleBox.getSelectionModel().getSelectedItem().toString());
            Tables.role.updateItem(fields[1],
                    roleNazwaField.getText()
            );
            updateRoleMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void roleMenuDeletePressed() {
        try {
            for (Object o : roleList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.role.deleteItem(toStringArray(b.getText())[1]);
                }
            }
            updateRoleMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void specjalizacjeMenuInsertPressed() {
        try {
            Tables.specjalizacje.insertItem(toEmptyString(specjalizacjeNazwaField.getText()));
            updateSpecjalizacjeMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void specjalizacjeMenuUpdatePressed() {
        try {
            if (specjalizacjeBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz specjalizację");
                return;
            }
            String[] fields = toStringArray(specjalizacjeBox.getSelectionModel().getSelectedItem().toString());
            Tables.specjalizacje.updateItem(fields[1],
                    specjalizacjeNazwaField.getText()
            );
            updateSpecjalizacjeMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void specjalizacjeMenuDeletePressed() {
        try {
            for (Object o : specjalizacjeList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.specjalizacje.deleteItem(toStringArray(b.getText())[1]);
                }
            }
            updateSpecjalizacjeMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void wydarzeniaMedyczneMenuInsertPressed() {
        try {
            Tables.wydarzenia_medyczne.insertItem(toEmptyString(wydarzeniaMedyczneNazwaField.getText()));
            updateWydarzeniaMedyczneMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void wydarzeniaMedyczneMenuUpdatePressed() {
        try {
            if (wydarzeniaMedyczneBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz wydarzenie");
                return;
            }
            String[] fields = toStringArray(wydarzeniaMedyczneBox.getSelectionModel().getSelectedItem().toString());
            Tables.wydarzenia_medyczne.updateItem(fields[1],
                    wydarzeniaMedyczneNazwaField.getText()
            );
            updateWydarzeniaMedyczneMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void wydarzeniaMedyczneMenuDeletePressed() {
        try {
            for (Object o : wydarzeniaMedyczneList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.wydarzenia_medyczne.deleteItem(toStringArray(b.getText())[1]);
                }
            }
            updateWydarzeniaMedyczneMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void celeWizytMenuInsertPressed() {
        try {
            Tables.cele_wizyty.insertItem(toEmptyString(celeWizytNazwaField.getText()));
            updateCeleWizytMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void celeWizytMenuUpdatePressed() {
        try {
            if (celeWizytBox.getSelectionModel().getSelectedItem() == null) {
                showError("Wybierz cel");
                return;
            }
            String[] fields = toStringArray(celeWizytBox.getSelectionModel().getSelectedItem().toString());
            Tables.cele_wizyty.updateItem(fields[1],
                    celeWizytNazwaField.getText()
            );
            updateCeleWizytMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void celeWizytMenuDeletePressed() {
        try {
            for (Object o : celeWizytList.getItems()) {
                CheckBox b = (CheckBox) o;
                if (b.isSelected()) {
                    Tables.cele_wizyty.deleteItem(toStringArray(b.getText())[1]);
                }
            }
            updateCeleWizytMenuVolatile();
        } catch (SQLException e) {
            showError(e.getMessage());
        }
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

    private void showTable(ArrayList<ArrayList<String>> rows) {
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

    private void updateListView(ListView view, ArrayList<ArrayList<String>> tab) {
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

    private void updateComboBox(ComboBox box, ArrayList<ArrayList<String>> tab) {
        box.getItems().clear();
        if (tab != null) {
            for (int i = 1; i < tab.size(); i++) {
                box.getItems().add(tab.get(i).toString());
            }
        }
    }

    private String[] toStringArray(String s) {
        return s.substring(1, s.length() - 1).split(", ");
    }

    private ArrayList<ArrayList<String>> getSmallLekarze() {
        try {
            return Database.executeQuery("SELECT id_pracownika, imie, nazwisko from pracownicy p " +
                    "where p.id_pracownika in ( " +
                    "select pr.id_pracownika " +
                    "from pracownicy_role pr " +
                    "where pr.id_roli = ( " +
                    "select r.id_roli " +
                    "from role r " +
                    "where r.nazwa = 'Lekarz'))" +
                    "order by p.id_pracownika;");
        } catch (SQLException e) {
            showError(e.getMessage());
            return null;
        }
    }

    private ArrayList<ArrayList<String>> getSmallPacjenci() {
        try {
            return filterTable(Tables.pacjenci.getContents(1), "id_pacjenta", "imie", "nazwisko");
        } catch (SQLException e){
            showError(e.getMessage());
            return null;
        }
    }

    private ArrayList<ArrayList<String>> getSpecLekarza(String id_lek) {
        try {
            return Database.executeQuery(
                    "select s.nazwa " +
                            "from lekarze_specjalizacje ls " +
                            "join specjalizacje s " +
                            "on ls.id_specjalizacji = s.id_specjalizacji " +
                            "where ls.id_lekarza = " + id_lek +
                            " order by s.nazwa;"
            );
        } catch (SQLException e) {
            showError(e.getMessage());
            return null;
        }
    }
    private String celNameToId(String cel) {
        try {
            if (cel == null) {
                return null;
            }
            return Database.executeQuery(
                    "select c.id_celu " +
                            "from cele_wizyty c " +
                            "where c.nazwa = '" +
                            cel +
                            "';"
            ).get(1).get(0);
        } catch (SQLException e) {
            showError(e.getMessage());
            return null;
        }
    }

    private String specNameToId(String spec) {
        try {
            if (spec == null) {
                return null;
            }
            return Database.executeQuery(
                    "select s.id_specjalizacji " +
                            "from specjalizacje s " +
                            "where s.nazwa = '" +
                            spec + "'" +
                            ";"
            ).get(1).get(0);
        } catch (SQLException e) {
            showError(e.getMessage());
            return null;
        }
    }

    public String wydarzenieNameToId(String wydarzenie) {
        try {
            if (wydarzenie == null) {
                return null;
            }
            return Database.executeQuery(
                    "select w.id_wydarzenia " +
                            "from wydarzenia_medyczne w " +
                            "where w.nazwa = " +
                            wydarzenie +
                            ";"
            ).get(1).get(0);
        } catch (SQLException e) {
            showError(e.getMessage());
            return null;
        }
    }

    private String toNullString(Object o) {
        return o == null ? null : o.toString();
    }

    private String toEmptyString(String s) {
        return s.equals("") ? null : s;
    }

    private String cutBraces(String s) {
        if (s == null){
            return null;
        }
        return s.substring(1, s.length() - 1);
    }

    private void showError(String mess) {
        new Thread(() -> {
            Platform.runLater(() ->errorMessage.setWrapText(true));
            Platform.runLater(() ->errorMessage.setText(mess));
            Platform.runLater(() -> errorMessage.setVisible(true));
            try {
                Thread.sleep(2000 + 50*mess.length());
            } catch (InterruptedException e) {
                Platform.runLater(() -> errorMessage.setVisible(false));
                return;
            }
            Platform.runLater(() -> errorMessage.setVisible(false));
        }).start();
    }
}
