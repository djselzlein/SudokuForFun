package com.example.sudokuforfun.listeners;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Label;

public class CEValueChangeListener implements ValueChangeListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void valueChange(ValueChangeEvent event) {
//		System.out.println( "In CEValueChangeListener: " + event.getProperty() );
		((Label)event.getProperty()).markAsDirty();
		((Label)event.getProperty()).setImmediate(true);
	}
}
