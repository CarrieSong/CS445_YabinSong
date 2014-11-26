package cs445_project;

import java.io.Serializable;

public class Product  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2153217760301310731L;
	private String pname;
	private int PID;
	private double price;
	private Recipe recipe;
	MyDate startDate;
	MyDate terminateDate;
	
	public Product() {
		this.setPname("Baguette");
		this.setPrice(2.95);
		this.setRecipe(new Recipe());
		startDate = new MyDate();
	}
	
	public Product(String n, double p, Recipe r) {
		this.setPname(n);
		this.setPrice(p);
		this.setRecipe(r);
		startDate = new MyDate();
	}
	
	public int getPID() {
		return PID;
	}
	
	public void setPID(int id) {
		PID = id;
	}
	
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public Product changePrice(double price) {
		Product p = new Product();
		p.setPname(this.getPname());
		p.setPrice(price);
		p.setRecipe(this.getRecipe());
		//p.ID = ProductIDGenerator.newID();
		return p;
	}

	public Recipe getRecipe() {
		return recipe;
	}
	
	public int getRecipeID() {
		return this.recipe.getRID();
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	public MyDate getStartTime() {
		return this.startDate;
	}
	
	public MyDate getTerminateDate() {
		return this.terminateDate;
	}
	
	public void setTerminateDate(MyDate dt) {
		this.terminateDate = dt;
	}
	
	public void printProduct() {
		String str = "";
		str += this.PID + " " + this.pname + " $" + this.price + 
				" " + this.recipe.toString() + "\n";
		System.out.println(str);
	}

}
