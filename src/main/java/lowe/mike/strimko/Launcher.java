package lowe.mike.strimko;

import javafx.application.Application;
import lowe.mike.strimko.controller.Controller;

/**
 * Entry point for Strimko application.
 * <p>
 * Instances of {@link Launcher} cannot be created.
 * 
 * @author Mike Lowe
 */
public final class Launcher {

	// don't want instances
	private Launcher() {
	}

	public static void main(String[] args) {
		Application.launch(Controller.class);
	}
}