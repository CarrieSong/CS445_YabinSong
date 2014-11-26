package cs445_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Subscription  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2525500579833616697L;
	private HashMap<Product, Integer>[] subscription;
	private MyDate startDate;
	private MyDate endDate;
	private String status;
	private MyDate suspendFirstDate;
	private MyDate suspendLastDate;
	private HashMap<MyDate, HashMap<Product, Integer>> specialDelivery;
	
	@SuppressWarnings("unchecked")
	public Subscription() {
		this.subscription = new HashMap[7]; //[0] is Sun ... [6] is Sat.
		for(int i=0; i<7; i++) {
			this.subscription[i] = new HashMap<Product, Integer>();
		}
		status = "Nothing in the subscription";
		specialDelivery = new HashMap<MyDate, HashMap<Product, Integer>>();
	}
	
	public HashMap<Product, Integer>[] getSubscription() {
		return this.subscription;
	}
	
	public void setStartDate(MyDate date) {
		startDate = date;
	}
	
	public void setEndDate(MyDate date) {
		endDate = date;
	}
	
	public MyDate getStartDate() {
		return startDate;
	}
	
	public MyDate getEndDate() {
		return endDate;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void addModifyProduct(int d, Product pro, int amt) {
		if(this.getStartDate() == null)
			this.setStartDate(new MyDate());
		if(d < 0 || d > 6) 
			System.out.print("Day is from Sunday(0) to Saturday(6).");
		else {
			if(amt == 0) {
				this.subscription[d].remove(pro);
				this.updateStatus();
			}
			else
				this.subscription[d].put(pro, amt);
			this.updateStatus();
			System.out.println("OK");
		}
	}
	
	public void updateStatus() {
		for(int i = 0; i < 7; i++) {
			if(this.subscription[i].size() > 0) {
				this.setStatus("active");
				return;
			}
		}
	}
	/*
	public HashMap<Product, Integer> getSubofDay(int d) {
		if(d < 0 || d > 6) 
			System.out.print("Day is from Sunday(0) to Saturday(6).");
		else {
			return this.subscription[d];
		}
		return null;
	}*/
	
	public MyDate getSuspendFirstDate() {
		return this.suspendFirstDate;
	}
	
	public MyDate getSuspendLastDate() {
		return this.suspendLastDate;
	}
	
	public String getSuspensions() {
		String str = "Current or future subscription suspension(s): ";
		if(this.getSuspendFirstDate() == null) 
			str += "none";
		else {
			MyDate f = this.getSuspendFirstDate();
			int fm = f.getMonth();
			int fd = f.getDate();
			int fy = f.getYear() - 2000;
			String first = fm + "/" + fd + "/" + fy;
			MyDate l = this.getSuspendLastDate();
			int lm = l.getMonth();
			int ld = l.getDate();
			int ly = l.getYear() - 2000;
			String last = lm + "/" + ld + "/" + ly;
			str += first + " through " + last;
		}
		return str;
	}
	
	public void setSuspendDate(MyDate from, MyDate to) {
		if(from == null) {
			this.suspendFirstDate = null;
			this.suspendLastDate = null;
		}
		else if(to.before(from))
			System.out.println("Last date must be equal or after first date.");
		else {
			this.suspendFirstDate = from;
			this.suspendLastDate = to;
		}
		System.out.println("OK");
	}
	
	public void setSpecialDelivery(MyDate date, Product p, int quantity) {
		HashMap<Product, Integer> pro = new HashMap<Product, Integer>();
		pro.put(p, quantity);
		specialDelivery.clear();
		specialDelivery.put(date, pro);
	}
	
	public void clearSpecialDelivery() {
		specialDelivery.clear();
	}
	
	public String getSpecialDelivery() {
		String str = "Future special deliveries: ";
		if(this.specialDelivery.size() == 0)
			str += "none";
		else {
			for(MyDate dt: this.specialDelivery.keySet()) {
				HashMap<Product, Integer> food = this.specialDelivery.get(dt);
				for(Product p: food.keySet()) {
					String pname = p.getPname();
					int amt = food.get(p);
					int day = dt.getDay();
					switch(day) {
					case 0: str += "SUN"; break;
					case 1: str += "MON"; break;
					case 2: str += "TUE"; break;
					case 3: str += "WED"; break;
					case 4: str += "THU"; break;
					case 5: str += "FRI"; break;
					case 6: str += "SAT"; break;
					}
					int m = dt.getMonth();
					int d = dt.getDate();
					int y = dt.getYear() - 2000;
					str += ", " + m + "/" + d + "/" + y + ", " +
							amt + " x " +pname;	
				}
	        }
		}
		return str;
	}
	
	public HashMap<Product, Integer> getDailySubscription(int year, int month, int day) {
		MyDate date = new MyDate(year, month, day);
		if(!this.specialDelivery.containsKey(date)) {
			if(this.getSuspendFirstDate() == null || 
					date.before(this.getSuspendFirstDate()) || 
					date.after(this.getSuspendLastDate())) {
				int d = date.getDay();
				return this.subscription[d];
			}
			else
				return null;
		}
		else {
			if(this.specialDelivery.get(date).size() != 0) {
				return this.specialDelivery.get(date);
			}
			else
				return null;
		}	
	}
	
	public double weeklyValue() {
		if(status.equals("cancelled"))
			return 0.0;
		else {
			double value = 0;
			for(int i = 0; i < 7; i++) {
				if(this.subscription[i].size() > 0) {
					for(Product name: this.subscription[i].keySet()) {
			            double price = name.getPrice();
			            int v = this.subscription[i].get(name);  
			            value += price*v; 
			        }
				}
			}
			return value;
		}
		
	}
	
	public void printWeeklySubscription() {
		for(int i = 0; i < 7; i++) {
			if(this.subscription[i].size() > 0) {
				switch(i) {
				case 0: System.out.print("SUN: "); break;
				case 1: System.out.print("MON: "); break;
				case 2: System.out.print("TUE: "); break;
				case 3: System.out.print("WED: "); break;
				case 4: System.out.print("THU: "); break;
				case 5: System.out.print("FRI: "); break;
				case 6: System.out.print("SAT: "); break;
				}
				for(Product name: this.subscription[i].keySet()) {
		            String key = name.getPname();
		            int value = this.subscription[i].get(name);  
		            System.out.println(value + " x " + key);  
		        }
			}	
		}
		System.out.println(this.getSuspensions());
		System.out.println(this.getSpecialDelivery());
	}

}
