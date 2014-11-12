package com.example.sudokuforfun.listeners;

import com.example.sudokuforfun.gameboard.CellElement;
import com.vaadin.data.Property;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Label;

public class GridClickListener implements LayoutClickListener {
	private static final long serialVersionUID = 1L;
	private int sCol = -1, sRow = -1;

	@Override
	public void layoutClick(LayoutClickEvent event) {

		int col = -1, row = -1;

		if (event.getClickedComponent() != null) {
			Label clickedLabel = (Label) event.getClickedComponent();

			Property<?> property = clickedLabel.getPropertyDataSource();

			if (property instanceof CellElement) {
				col = ((CellElement) property).getCol();
				row = ((CellElement) property).getRow();
			}

			System.out.print("value: " + property.getValue());
			System.out.print("  isReadOnly: " + property.isReadOnly());
			System.out.println(" col: " + col + " row: " + row);

			// get the parent container of the label
			HasComponents hasComponents = clickedLabel.getParent();

			// remove selected style from previously selected element
			if (sCol != -1 && hasComponents instanceof GridLayout) {
				GridLayout grid = (GridLayout) hasComponents;
				Component component = grid.getComponent(sCol, sRow);
				component.removeStyleName("colored bold");
			}

			// only show selection on modifiable elements
			if (!property.isReadOnly()) {
				// add style to currently selected element
				clickedLabel.addStyleName("colored bold");

				// save the location of the clicked element
				sCol = col;
				sRow = row;
			} else
				sCol = sRow = -1;

		}
	}
}
