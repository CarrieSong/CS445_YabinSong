package cs445_project;

public class Recipe {

	private double water;
	private double flour;
	private double salt;
	private double yeast;
	private String description;
	
	public Recipe() {
		this.setWater(0);
		this.setFlour(0);
		this.setSalt(0);
		this.setYeast(0);
	}
	
	public Recipe(double w, double f, double s, double y) {
		this.setWater(w);
		this.setFlour(f);
		this.setSalt(s);
		this.setYeast(y);
		this.setDescription("");
	}
	/**
	 * @return the water
	 */
	public double getWater() {
		return water;
	}

	/**
	 * @param water the water to set
	 */
	public void setWater(double water) {
		this.water = water;
	}

	/**
	 * @return the flour
	 */
	public double getFlour() {
		return flour;
	}

	/**
	 * @param flour the flour to set
	 */
	public void setFlour(double flour) {
		this.flour = flour;
	}

	/**
	 * @return the salt
	 */
	public double getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(double salt) {
		this.salt = salt;
	}

	/**
	 * @return the yeast
	 */
	public double getYeast() {
		return yeast;
	}

	/**
	 * @param yeast the yeast to set
	 */
	public void setYeast(double yeast) {
		this.yeast = yeast;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
