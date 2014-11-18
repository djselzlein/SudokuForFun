package com.example.sudokuforfun.gameboard;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Label;

public class CEValueChangeListener implements ValueChangeListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void valueChange(ValueChangeEvent event) {
		((Label)event.getProperty()).markAsDirty();
		((Label)event.getProperty()).setImmediate(true);
	}
}
