package Models;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// bean class to represent location

import java.util.ArrayList;

public class Location {
	// store the Location in an array to accommodate multi-dimensional problem space
	private ArrayList<Produs> loc;

	public Location(ArrayList<Produs> loc) {
		this.loc = loc;
	}

	public ArrayList<Produs> getLoc() {
		return loc;
	}

	public void setLoc(ArrayList<Produs> loc) {
		this.loc = loc;
	}
	
}
