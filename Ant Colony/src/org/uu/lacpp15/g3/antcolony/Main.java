package org.uu.lacpp15.g3.antcolony;

import org.uu.lacpp15.g3.antcolony.client.Client;

/**
 * Serves as the entry point for the program.
 *
 */
public class Main {
	
	public static void main(String[] args) {
		try {
			new Client().run();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
}
