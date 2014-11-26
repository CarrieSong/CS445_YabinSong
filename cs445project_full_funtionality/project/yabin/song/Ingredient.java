package project.yabin.song;

public class Ingredient implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4284974887747010812L;
	private String Iname;
	private double amount;
	private String measuring_unit;
	private String type;
	
	public String getIName() {
		return Iname;
	}
	public void setIName(String name) {
		this.Iname = name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMeasuring_unit() {
		return measuring_unit;
	}
	public void setMeasuring_unit(String measuring_unit) {
		this.measuring_unit = measuring_unit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = "Ingredient_name: " + this.getIName() + "\tAmount: " +
				this.getAmount() + "\tMeasuring_unit: " +
				this.getMeasuring_unit() + "\tType: " +
				this.getType() + "\n";
		return str;
	}
	
}
