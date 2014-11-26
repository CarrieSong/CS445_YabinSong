package project.yabin.song;

public class Subscriber  implements java.io.Serializable {

	private static final long serialVersionUID = 3182191650273578106L;
	private String name;
	private int UID;
	private Address addr;
	private String email;
	private String phone;
	private String facebook;
	private String twitter;
	private Subscription sub;
	private MyDate since;
	private String instruction;
	private MyDate confirmed;
	
	public Subscriber() {
		this.name = "Jane Doe";
		this.addr = new Address();
		this.email = "jane.doe@example.com";
		this.phone = "123-456-7890";
		this.facebook = "";
		this.twitter = "";
		this.setSub(new Subscription());
		this.since = new MyDate();
		this.instruction = "";
	}
	
	public Subscriber(String _name, String em, String _phone, Address a) {
		this.name = _name;
		this.addr = a;
		this.email = em;
		this.phone = _phone;
		this.facebook = "";
		this.twitter = "";
		this.setSub(new Subscription());
		this.since = new MyDate();
		this.instruction = "";
	}
	
	public void setFacebook(String fcbk) {
		this.facebook = fcbk;
	}
	
	public void setTwitter(String twtr) {
		this.twitter = twtr;
	}
	
	public void setEmail(String e) {
		this.email = e;
	}
	
	public void setPhone(String ph) {
		this.phone = ph;
	}
	
	public void setStreet(String s) {
		this.addr.setStreet(s);
	}
	
	public void setCity(String c) {
		this.addr.setCity(c);
	}
	
	public void setState(String s) {
		this.addr.setState(s);
	}
	
	public void setZip(String z) {
		this.addr.setZip(z);
	}
	
	public void setAddress(Address a) {
		this.addr = a;
	}
	
	public Address getAddress() {
		return this.addr;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setUID(int id) {
		UID = id;
	}
	
	public int getUID() {
		return UID;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public MyDate getSince() {
		return this.since;
	}
	
	public void setInstruction(String s) {
		this.instruction = s;
	}
	
	public String getInstruction() {
		return this.instruction;
	}
	
	public MyDate getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(MyDate confirmed) {
		this.confirmed = confirmed;
	}

	public Subscription getSub() {
		return sub;
	}

	public void setSub(Subscription sub) {
		this.sub = sub;
	}
	
	public void printSubscription() {
		this.sub.printWeeklySubscription();
	}
	
	public void cancelSub() {
		this.sub.setStatus("cancelled");
		this.sub.clearSpecialDelivery();
		for(int i = 0; i < 7; i++)
			this.sub.getSubscription()[i].clear();
		this.sub.setSuspendDate(null, null);
	}
	
	public boolean isMatch(String kw) {
		if((this.name.toLowerCase().contains(kw.toLowerCase()))||(this.phone.contains(kw))||(this.email.contains(kw)) ||
				this.facebook.matches(kw) || this.twitter.matches(kw))
			return true;
		return false;
	}
	
	public void printSubscriber() {
		String s;
		s = this.UID + ", " + this.name.toString()+", " +
				this.addr.toString() + ", " + this.email.toString() + ", " + 
				this.phone.toString() + ", ";
		if(!this.facebook.equals(""))
			s += "facebook: " + this.facebook.toString() + ", ";
		if(!this.twitter.equals(""))
			s += "twitter: " + this.twitter.toString();
		System.out.println(s);
	}
	
	public void printDeliveryList() {
		String s;
		s = this.UID + ", " + this.name.toString()+", " +
				this.addr.toString() + ". Special instructions: ";
		if(!this.getInstruction().equals(""))
			s += this.getInstruction();
		else
			s += "none";
		System.out.println(s);
	}
	
	public void viewSubscriber() {
		this.printSubscriber();
		System.out.println("Customer since: "+this.getSince().toString());
		System.out.println("Subscription status: " + this.getSub().getStatus());
		System.out.println(this.getSub().getSuspensions());
		System.out.println(this.getSub().getSpecialDelivery());
		System.out.println("Weekly value: $" + this.getSub().weeklyValue());
	}

}
