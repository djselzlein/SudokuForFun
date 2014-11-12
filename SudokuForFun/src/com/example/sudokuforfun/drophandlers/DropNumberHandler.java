package com.example.sudokuforfun.drophandlers;

import com.example.sudokuforfun.gameboard.CellElement;
import com.example.sudokuforfun.undo.SingletonStack;
import com.example.sudokuforfun.undo.StackElement;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Label;

public class DropNumberHandler implements DropHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public void drop(DragAndDropEvent event) {

		WrapperTransferable t = (WrapperTransferable) event.getTransferable();
		Label transferedLabel = (Label) t.getDraggedComponent();
		event.getTargetDetails().getData(null);
		Label label = (Label) ((DragAndDropWrapper) event.getTargetDetails()
				.getTarget()).getData();
		CellElement prop = (CellElement) label.getPropertyDataSource();
		Integer oldValue = prop.getIntValue();

		System.out.println("Old value:" + oldValue);
		System.out.println("New value:"
				+ transferedLabel.getPropertyDataSource().getValue());

		// Only update property value if player dragged in a not read only label
		if (!prop.isReadOnly()) {
			prop.setValue(transferedLabel.getPropertyDataSource().getValue());
			label.setPropertyDataSource(prop);

			// //// Stack usage /////
			SingletonStack.getStack().push(new StackElement(label, oldValue));
			//
		}
	}

	@Override
	public AcceptCriterion getAcceptCriterion() {
		return AcceptAll.get();
	}

}
