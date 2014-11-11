package com.example.sudokufinal2.undo;

import com.example.sudokufinal2.CellElement;
import com.vaadin.ui.Label;

public class StackElement {
	private Label element;
	private Integer oldValue;

	public StackElement(Label el, Integer val) {
		this.element = el;
		this.oldValue = val;

		System.out.println("Old value:"
				+ val + " New Value: " + element.getPropertyDataSource().getValue()
				+ " on Coordinates: x:" + (((CellElement) element.getPropertyDataSource()).getCol() + 1)
				+ " y: " + (((CellElement) element.getPropertyDataSource()).getRow()) + 1);
	}

	public Label getElement() {
		return element;
	}

	public void setElement(Label element) {
		this.element = element;
	}

	public Integer getOldValue() {
		return oldValue;
	}

	public void setOldValue(Integer oldValue) {
		this.oldValue = oldValue;
	}

}
