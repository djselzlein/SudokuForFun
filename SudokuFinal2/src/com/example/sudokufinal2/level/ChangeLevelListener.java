package com.example.sudokufinal2.level;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.TabSheet;

public class ChangeLevelListener implements ValueChangeListener {
	private static final long serialVersionUID = 1L;

	private TabSheet tabSheet;
	private PuzzleFile puzzle;

	public ChangeLevelListener(TabSheet tab, PuzzleFile puzzle) {
		this.tabSheet = tab;
		this.puzzle = puzzle;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		String level = (String) event.getProperty().getValue();
		if (!((level == null) || (level.trim().isEmpty()))) {
			puzzle.populateBoard("../resources/" + level.toLowerCase() + ".txt");
			// advance to the next tab, so the game start
			tabSheet.setSelectedTab(tabSheet.getTabIndex() + 1);
		}
	}
}
