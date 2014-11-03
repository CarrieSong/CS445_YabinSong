package cs445_project;

public class Product {

	private String pname;
	private double price;
	private Recipe recipe;
	
	public Product(String name, double _price) {
		this.setPname(name);
		this.setPrice(_price);
		this.setRecipe(new Recipe());
	}
	
	public Product(String n, double p, Recipe r) {
		this.setPname(n);
		this.setPrice(p);
		this.setRecipe(r);
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the recipe
	 */
	public Recipe getRecipe() {
		return recipe;
	}

	/**
	 * @param recipe the recipe to set
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
