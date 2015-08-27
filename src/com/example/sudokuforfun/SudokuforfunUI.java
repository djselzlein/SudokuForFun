package com.example.sudokuforfun;

import javax.servlet.annotation.WebServlet;

import com.example.sudokuforfun.gameboard.Board;
import com.example.sudokuforfun.gameboard.CEValueChangeListener;
import com.example.sudokuforfun.gameboard.CellElement;
import com.example.sudokuforfun.gameboard.DropNumberHandler;
import com.example.sudokuforfun.inputPuzzle.ChangeLevelListener;
import com.example.sudokuforfun.inputPuzzle.DropInputFileHandler;
import com.example.sudokuforfun.inputPuzzle.PopulateBoard;
import com.example.sudokuforfun.inputPuzzle.UploadReceiver;
import com.example.sudokuforfun.inputPuzzle.level.PuzzleLevel;
import com.example.sudokuforfun.listeners.CheckerButtonListener;
import com.example.sudokuforfun.listeners.RestartButtonListener;
import com.example.sudokuforfun.listeners.SolverButtonListener;
import com.example.sudokuforfun.undo.UndoButtonListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import static eu.hurion.vaadin.heroku.VaadinForHeroku.herokuServer;
import static eu.hurion.vaadin.heroku.VaadinForHeroku.forApplication;

@SuppressWarnings("serial")
@Theme("sudokuforfun")
public class SudokuforfunUI extends UI {

	private GridLayout grid;
	private Board board;

	private final VerticalLayout vLayout = new VerticalLayout();
	private UploadReceiver uploadReceiver;
	private Upload upload;
	private Button solveButton = new Button("Solve");
	private Button checkButton = new Button("Check");
	private Button undoButton = new Button("Undo");
	private Button restartButton = new Button("Restart");
	private ComboBox levelCombo = new ComboBox("Choose a Level");
	private final TabSheet tab = new TabSheet();

	private PopulateBoard populate;

	private ProgressBar progress;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SudokuforfunUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		// Find the application directory
		String basepath = VaadinService.getCurrent().getBaseDirectory()
				.getAbsolutePath();

		System.out.println(basepath);

		// build a panel
		// https://vaadin.com/api/7.3.2/com/vaadin/ui/Panel.html

		// place a 9 x 9 grid control into the panel
		// https://vaadin.com/api/7.3.2/com/vaadin/ui/GridLayout.html

		// add a label to each element of the grid control
		// https://vaadin.com/api/com/vaadin/ui/Label.html
		// https://vaadin.com/api/7.3.2/com/vaadin/data/Property.Viewer.html
		// https://vaadin.com/api/com/vaadin/data/Property.html
		// https://vaadin.com/book/-/page/datamodel.html

		// each component should now be addressable by x,y

		grid = new GridLayout(9, 9);

		grid.setMargin(false);
		grid.setSpacing(false);
		grid.setWidth("300px");
		grid.setHeight("300px");
		// grid.addLayoutClickListener(new GridClickListener());

		grid.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		grid.addStyleName("v-gridlayout");

		board = new Board();

		// connect the tile to the display
		for (int col = 0; col < 9; col++)
			for (int row = 0; row < 9; row++) {
				Label label = new Label();

				label.setPropertyDataSource(board.getCellElement(col, row));
				label.addValueChangeListener(new CEValueChangeListener());

				label.setWidth(null);
				label.setImmediate(true);
				label.setSizeFull();

				if ((col == 8) || (col == 5) || (col == 2)) {
					label.addStyleName("dj-right-border");
				} else if (col == 0) {
					label.addStyleName("dj-left-border");
				}

				if ((row == 8) || (row == 5) || (row == 2)) {
					label.addStyleName("dj-bottom-border");
				} else if (row == 0) {
					label.addStyleName("dj-top-border");
				}

				DragAndDropWrapper wrapper = new DragAndDropWrapper(label);
				wrapper.setDropHandler(new DropNumberHandler());
				wrapper.setData(label);
				wrapper.setSizeFull();

				// grid.addComponent(label, col, row);
				grid.addComponent(wrapper, col, row);
			}

		// //// Drag number section ////////
		GridLayout gridNumbers = new GridLayout(3, 3);

		gridNumbers.setMargin(false);
		gridNumbers.setSpacing(false);
		gridNumbers.setWidth("102px");
		gridNumbers.setHeight("102px");

		gridNumbers.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		for (int num = 0; num < 9; num++) {
			Label label = new Label();

			label.setPropertyDataSource(new CellElement(num + 1, true, num, 0));
			label.setWidth(null);
			label.setImmediate(true);

			DragAndDropWrapper wrapper = new DragAndDropWrapper(label);
			wrapper.setSizeUndefined();
			wrapper.setDragStartMode(DragStartMode.WRAPPER);
			gridNumbers.addComponent(wrapper);
			// gridNumbers.addComponent(label, col, 0);
		}

		Panel panelNumbers = new Panel();

		panelNumbers.setContent(gridNumbers);
		panelNumbers.setWidth("130px");
		panelNumbers.setHeight("115px");
		panelNumbers.addStyleName("v-panel-caption");

		// ///////////////////////

		populate = new PopulateBoard(grid, board);

		uploadReceiver = new UploadReceiver(tab, populate);
		upload = new Upload(" ", uploadReceiver);
		upload.setButtonCaption("Upload initial puzzle");
		upload.setImmediate(true);

		solveButton.addStyleName("v-button-friendly");
		undoButton.addStyleName("v-button-primary");
		checkButton.addStyleName("v-button-friendly");
		restartButton.addStyleName("v-button-friendly");

		// ///////

		Label lbInfoLabel = new Label("Drag your initial puzzle here");
		Label lbH1Label = new Label("Sudoku for Fun");
		lbH1Label.setWidth(lbH1Label.getCaption());
		lbH1Label.addStyleName("h1");

		Label lbOr = new Label("Or");
		Label lbOrLevel = new Label("Or");
		lbOr.setWidth(lbOr.getCaption());
		lbOrLevel.setWidth(lbOrLevel.getCaption());
		lbInfoLabel.setWidth(lbInfoLabel.getCaption());

		VerticalLayout vlDropPane = new VerticalLayout(lbInfoLabel);
		vlDropPane.setComponentAlignment(lbInfoLabel, Alignment.MIDDLE_CENTER);
		vlDropPane.setWidth(240.0f, Unit.PIXELS);
		vlDropPane.setHeight(160.0f, Unit.PIXELS);
		vlDropPane.addStyleName("drop-area well");

		progress = new ProgressBar();
		progress.setIndeterminate(true);
		progress.setVisible(false);
		vlDropPane.addComponent(progress);
		vlDropPane.setComponentAlignment(progress, Alignment.MIDDLE_CENTER);

		DropInputFileHandler dropBox = new DropInputFileHandler(tab,
				vlDropPane, populate, lbInfoLabel, progress);
		dropBox.setSizeUndefined();

		vLayout.addComponent(lbH1Label);

		// /// ComboBox ////
		levelCombo.addItem(PuzzleLevel.EASY);
		levelCombo.addItem(PuzzleLevel.MEDIUM);
		levelCombo.addItem(PuzzleLevel.HARD);
		levelCombo.setTextInputAllowed(false);
		// //////////////

		VerticalLayout vLFirstTab = new VerticalLayout();
		vLFirstTab.setMargin(true);
		vLFirstTab.setSpacing(true);

		vLFirstTab.addComponent(dropBox);
		vLFirstTab.addComponent(lbOr);
		vLFirstTab.addComponent(upload);
		vLFirstTab.addComponent(lbOrLevel);
		vLFirstTab.addComponent(levelCombo);

		vLFirstTab.setComponentAlignment(upload, Alignment.MIDDLE_CENTER);
		vLFirstTab.setComponentAlignment(lbOr, Alignment.MIDDLE_CENTER);
		vLFirstTab.setComponentAlignment(lbOrLevel, Alignment.MIDDLE_CENTER);
		vLFirstTab.setComponentAlignment(dropBox, Alignment.MIDDLE_CENTER);
		vLFirstTab.setComponentAlignment(levelCombo, Alignment.MIDDLE_CENTER);

		Tab t = tab.addTab(vLFirstTab, "Setup");
		t.setEnabled(true);

		VerticalLayout vLSecondTab = new VerticalLayout();
		vLSecondTab.setMargin(true);
		vLSecondTab.setSpacing(true);
		vLSecondTab.addComponent(panelNumbers);
		vLSecondTab.addComponent(undoButton);
		vLSecondTab.addComponent(solveButton);
		vLSecondTab.addComponent(checkButton);

		vLSecondTab
				.setComponentAlignment(panelNumbers, Alignment.MIDDLE_CENTER);
		vLSecondTab.setComponentAlignment(solveButton, Alignment.MIDDLE_CENTER);
		vLSecondTab.setComponentAlignment(checkButton, Alignment.MIDDLE_CENTER);
		vLSecondTab.setComponentAlignment(undoButton, Alignment.MIDDLE_CENTER);

		HorizontalLayout hLSecondTab = new HorizontalLayout();
		hLSecondTab.setMargin(true);
		hLSecondTab.setSpacing(true);
		hLSecondTab.addComponent(vLSecondTab);
		hLSecondTab.addComponent(grid);
		hLSecondTab.setComponentAlignment(vLSecondTab, Alignment.MIDDLE_CENTER);
		hLSecondTab.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);

		VerticalLayout vteste = new VerticalLayout();
		vteste.addComponent(hLSecondTab);
		vteste.addComponent(restartButton);
		vteste.setComponentAlignment(hLSecondTab, Alignment.MIDDLE_CENTER);
		vteste.setComponentAlignment(restartButton, Alignment.BOTTOM_RIGHT);
		tab.addTab(vteste, "Play");

		vLayout.addComponent(tab);
		vLayout.setComponentAlignment(tab, Alignment.MIDDLE_CENTER);
		vLayout.setMargin(true);
		vLayout.setSpacing(true);

		setContent(vLayout);

		upload.addFinishedListener(uploadReceiver);

		/*
		 * Click on the Solve Button
		 */
		solveButton.addClickListener(new SolverButtonListener(grid, board));

		/*
		 * Click on the Undo Button
		 */
		undoButton.addClickListener(new UndoButtonListener());

		/*
		 * Change on Level ComboBox
		 */
		levelCombo
				.addValueChangeListener(new ChangeLevelListener(tab, populate));

		/*
		 * Click on the Check Button
		 */
		checkButton.addClickListener(new CheckerButtonListener(grid, board));

		/*
		 * Click on the Play Button
		 */
		restartButton.addClickListener(new RestartButtonListener(tab));
	}

	public static void main(final String[] args) {
		herokuServer(forApplication(SudokuforfunUI.class)).start();
	}

}