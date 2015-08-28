package com.example.sudokuforfun;

import static eu.hurion.vaadin.heroku.VaadinForHeroku.forApplication;
import static eu.hurion.vaadin.heroku.VaadinForHeroku.herokuServer;
import static eu.hurion.vaadin.heroku.MemcachedManagerBuilder.memcachierAddOn;

public class Launcher {

	public static void main(final String[] args) {
	    herokuServer(forApplication(SudokuforfunUI.class))
	            .withMemcachedSessionManager(memcachierAddOn())
	            .start();
	}
	
}
