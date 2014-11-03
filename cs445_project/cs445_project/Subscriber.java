package cs445_project;

public class Subscriber {

	private Name name;
	private String email, phone, address;
	private String facebook = null, twitter = null;
	private Subscription sub;
	
	public Subscriber() {
		this.name = new Name();
		this.email = "iit@hawk.iit.edu";
		this.phone = "123-456-7890";
		this.address = "IIT";
		this.setSub(new Subscription());
	}
	
	public Subscriber(String f, String l, String em, String _phone, String addr) {
		this.name = new Name(f, l);
		this.email = em;
		this.phone = _phone;
		this.address = addr;
		this.setSub(new Subscription());
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
	
	public void setAddress(String addr) {
		this.address = addr;
	}
	/**
	 * @return the sub
	 */
	public Subscription getSub() {
		return sub;
	}

	/**
	 * @param sub the sub to set
	 */
	public void setSub(Subscription sub) {
		this.sub = sub;
	}
	
	public boolean isMatch(String kw) {
		if((this.name.getLast().matches(kw))||(this.phone.matches(kw))||(this.email.matches(kw)))
			return true;
		return false;
	}
	
	public void printSubscriber() {
		String s;
		s = "Name: " + this.name.printName()+"\n" +
				"Phone: " + this.phone.toString() + "\n" +
				"Email: " + this.email.toString() + "\n" +
				"Address: " + this.address.toString() + "\n";
		if(this.facebook != null)
			s += "Facebook: " + this.facebook.toString() + "\n";
		if(this.twitter != null)
			s += "Twitter: " + this.twitter.toString() + "\n";
		System.out.println(s);
	}

}
