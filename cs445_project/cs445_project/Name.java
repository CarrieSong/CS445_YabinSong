package cs445_project;

public class Name {

	private String first, last;
	
	public Name() {
	}
	
	public Name(String _first, String _last) {
		this.first = _first;
		this.last = _last;
	}
	
	public void setFirst(String _first) {
		this.first = _first;
	}
	
	public void setLast(String _last) {
		this.last = _last;
	}
	
	public String getLast() {
		return this.last;
	}
	
	public String printName() {
		return this.first+" "+this.last;
	}

}
