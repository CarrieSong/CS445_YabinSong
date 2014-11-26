package project.yabin.song;

import java.util.Date;

public class MyDate extends Date implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6915519987568413400L;
	
	public MyDate() {
		super();
	}

	@SuppressWarnings("deprecation")
	public MyDate(int year, int month, int day) {
		super(year-1900, month-1, day);
	}
	
	public int getYear() {
		return super.getYear()+1900;
	}
	
	public int getMonth() {
		return super.getMonth()+1;
	}
	
	public void nextDay() {
		super.setTime(super.getTime()+1*24*60*60*1000);
	}
	
	public boolean before(MyDate d) {
		if(this.getYear() > d.getYear())
			return false;
		else if(this.getYear() < d.getYear())
			return true;
		else {
			if(this.getMonth() > d.getMonth())
				return false;
			else if(this.getMonth() < d.getMonth())
				return true;
			else {
				if(this.getDate() < d.getDate())
					return true;
				else
					return false;
			}
		}
	}
	
	public boolean after(MyDate d) {
		if(this.getYear() > d.getYear())
			return true;
		else if(this.getYear() < d.getYear())
			return false;
		else {
			if(this.getMonth() > d.getMonth())
				return true;
			else if(this.getMonth() < d.getMonth())
				return false;
			else {
				if(this.getDate() > d.getDate())
					return true;
				else
					return false;
			}
		}
	}
	
	public boolean equals(MyDate d) {
		if(this.getYear() == d.getYear() && 
				this.getMonth() == d.getMonth() &&
				this.getDate() == d.getDate())
			return true;
		else
			return false;
	}
	
	public String toString() {
		String str = this.getMonth() + "/" + this.getDate() + "/" + this.getYear();
		return str;
	}

}
