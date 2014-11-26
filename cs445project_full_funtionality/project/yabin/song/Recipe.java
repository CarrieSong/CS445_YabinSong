package project.yabin.song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Recipe  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5206346023213128966L;
	private String Rname;
	private int recipeID;
	private ArrayList<Ingredient> ingres;
	private String instructions;
	private int units_made;
	
	public Recipe() {
	}

	public int getRID() {
		return recipeID;
	}
	
	public void setRID(int id) {
		recipeID = id;
	}
	
	public String getRName() {
		return Rname;
	}

	public void setRName(String name) {
		this.Rname = name;
	}

	public ArrayList<Ingredient> getIngres() {
		return ingres;
	}

	public void setIngres(ArrayList<Ingredient> ingres) {
		this.ingres = ingres;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getUnits_made() {
		return units_made;
	}

	public void setUnits_made(int units_made) {
		this.units_made = units_made;
	}
	
	public String toString() {
		String str = "recipe: " + this.getRName() + " (id=" + this.getRID() + ")";
		return str;
	}
	
}
