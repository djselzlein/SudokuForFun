package com.example.sudokufinal2.dragdrop;

import com.example.sudokufinal2.CellElement;
import com.example.sudokufinal2.undo.SingletonStack;
import com.example.sudokufinal2.undo.StackElement;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Label;

public class DropNumberHandler implements DropHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Label label;

	public DropNumberHandler(Label lb) {
		this.label = lb;
	}

	@Override
	public void drop(DragAndDropEvent event) {

		WrapperTransferable t = (WrapperTransferable) event.getTransferable();
		Label transferedLabel = (Label) t.getDraggedComponent();
		CellElement prop = (CellElement) label.getPropertyDataSource();
		Integer oldValue = prop.getIntValue();
		
		System.out.println("Old value:" + oldValue);
		System.out.println("New value:" + transferedLabel.getPropertyDataSource().getValue());
		
		prop.setValue(transferedLabel.getPropertyDataSource().getValue());
		label.setPropertyDataSource(prop);
		
		////// Stack usage /////
		
		SingletonStack.getStack().push(new StackElement(label, oldValue));
		
		///////
		
	}

	@Override
	public AcceptCriterion getAcceptCriterion() {
		return AcceptAll.get();
	}

}
