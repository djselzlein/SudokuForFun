package com.example.sudokufinal2.undo;

import com.vaadin.data.Property;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;

public class Undo implements ClickListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void buttonClick(ClickEvent event) {
		if (!SingletonStack.getStack().isEmpty()) {
			StackElement element = SingletonStack.getStack().pop();
			Label lb = element.getElement();
			Property<Integer> prop = lb.getPropertyDataSource();
			prop.setValue(element.getOldValue());
			lb.setPropertyDataSource(prop);
		}
	}

}
