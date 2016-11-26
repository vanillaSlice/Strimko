package lowe.mike.strimko.controller;

import static javafx.application.Platform.exit;
import static lowe.mike.strimko.model.PuzzleFileHandler.listPuzzleFileNames;
import static lowe.mike.strimko.model.PuzzleFileHandler.syncPuzzlesToUserDirectory;

import java.io.IOException;
import java.util.Set;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Type;

public final class Controller extends Application {
	private Stage primaryStage;
	private HBox viewPane;
	@FXML
	private ComboBox<Type> typeComboBox;
	@FXML
	private ComboBox<Difficulty> difficultyComboBox;
	@FXML
	private ComboBox<String> puzzleComboBox;
	@FXML
	private Button loadButton;
	@FXML
	private Button resetButton;
	@FXML
	private Button quitButton;
	@FXML
	private Button createButton;
	@FXML
	private Button saveButton;
	@FXML
	private ToggleButton solutionButton;
	@FXML
	private ToggleButton pencilMarksButton;
	@FXML
	private Button hintButton;
	@FXML
	private ToggleButton solveCellButton;
	@FXML
	private GridPane gridPane;
	@FXML
	private TitledPane numbersPane;
	@FXML
	private TitledPane streamsPane;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Strimko");
		this.primaryStage.setOnCloseRequest(e -> exit());
		this.primaryStage.setResizable(false);

		syncPuzzlesToUserDirectory();
		loadViewPane();
		showViewPane();
	}

	private void loadViewPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Controller.class.getResource("/view/View.fxml"));
		viewPane = (HBox) loader.load();
	}

	private void showViewPane() {
		Scene scene = new Scene(viewPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	private void initialize() {
		initializeMainControls();
		initializeCreateControls();
		initializePlayControls();
		initializeGrid();
		initializeNumberControls();
		initializeStreamControls();
	}

	private void initializeMainControls() {
		typeComboBox.getItems().addAll(Type.values());
		difficultyComboBox.getItems().addAll(Difficulty.values());
		loadButton.setDisable(true);
		resetButton.setDisable(true);
	}

	private void initializeCreateControls() {
		saveButton.setDisable(true);
	}

	private void initializePlayControls() {
		disablePlayControls();
	}

	private void initializeGrid() {
		gridPane.setVisible(false);
	}

	private void initializeNumberControls() {
		numbersPane.setVisible(false);
	}

	private void initializeStreamControls() {
		streamsPane.setVisible(false);
	}

	@FXML
	private void typeComboBoxAction() {
		loadPuzzleNames();
	}

	@FXML
	private void difficultyComboBoxAction() {
		loadPuzzleNames();
	}

	private void loadPuzzleNames() {
		Set<String> puzzleNames = listPuzzleFileNames(typeComboBox.getValue(), difficultyComboBox.getValue());
		puzzleComboBox.getItems().clear();
		puzzleComboBox.getItems().addAll(puzzleNames);
	}

	@FXML
	private void puzzleComboBoxAction() {
		loadButton.setDisable(puzzleComboBox.getValue() == null);
	}

	@FXML
	private void loadButtonAction() {
		// load grid

		gridPane.setVisible(true);
		resetButton.setDisable(false);
		saveButton.setDisable(true);
		enablePlayControls();
		untogglePlayControls();
	}

	private void disablePlayControls() {
		disablePlayControls(true);
	}

	private void enablePlayControls() {
		disablePlayControls(false);
	}

	private void disablePlayControls(boolean disable) {
		solutionButton.setDisable(disable);
		pencilMarksButton.setDisable(disable);
		hintButton.setDisable(disable);
		solveCellButton.setDisable(disable);
	}

	private void untogglePlayControls() {
		solutionButton.setSelected(false);
		pencilMarksButton.setSelected(false);
		solveCellButton.setSelected(false);
	}

	@FXML
	private void resetButtonAction() {
		// get mode and reset grid accordingly
		System.out.println("reset");
	}

	@FXML
	private void quitButtonAction() {
		exit();
	}

	@FXML
	private void createButtonAction() {
		resetButton.setDisable(false);
		saveButton.setDisable(false);
		disablePlayControls();
		untogglePlayControls();
	}

	@FXML
	private void saveButtonAction() {
		// sort this
		System.out.println("saved");
	}

	@FXML
	private void solutionButtonAction() {
		// untoggle and disable other cells
		System.out.println("solution");
	}

	@FXML
	private void pencilMarksButtonAction() {
		System.out.println("pencil marks");
	}

	@FXML
	private void hintButtonAction() {
		System.out.println("hint");
	}

	@FXML
	private void solveCellButtonAction() {
		System.out.println("solve cell");
	}
}