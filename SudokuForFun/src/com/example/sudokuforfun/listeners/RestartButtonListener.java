package com.example.sudokuforfun.listeners;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet;

public class RestartButtonListener implements ClickListener {
	private static final long serialVersionUID = 1L;

	private final TabSheet tabSheet;

	public RestartButtonListener(final TabSheet tab) {
		this.tabSheet = tab;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		System.out.println(tabSheet.getTabIndex());
		tabSheet.setSelectedTab(0);
		System.out.println(tabSheet.getTabIndex());
	}

}
