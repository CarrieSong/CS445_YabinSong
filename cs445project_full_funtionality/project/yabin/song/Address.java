package project.yabin.song;


public class Address implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -287740270426914562L;
	private String street;
	private String city;
	private String state;
	private String zip;
	
	public Address() {
		street = "123 Main ST";
		city = "Anytown";
		state = "Anystate";
		zip = "12345";
	}
	
	public Address(String street, String c, String state, String z) {
		this.street = street;
		city = c;
		this.state = state;
		zip = z;
	}
	
	public void setStreet(String s) {
		street = s;
	}
	
	public String getStreet() {
		return this.street;
	}
	
	public void setCity(String c) {
		city = c;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setState(String s) {
		state = s;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setZip(String z) {
		zip = z;
	}
	
	public String getZip() {
		return this.zip;
	}
	
	@Override
	public String toString() {
		String s = "";
		s+= this.getStreet() + ", " + this.getCity() + ", " +
				this.getState() + ", " + this.getZip();
		return s;
	}

}
