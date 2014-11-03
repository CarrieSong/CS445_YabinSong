package cs445_project;

import java.util.HashMap;

public class Subscription {

	private HashMap<Product, Integer>[] subscription;
	
	@SuppressWarnings("unchecked")
	public Subscription() {
		this.subscription = new HashMap[7]; //[0] is Sun ... [6] is Sat.
		for(int i=0; i<7; i++) {
			this.subscription[i] = new HashMap<Product, Integer>();
		}
	}
	
	public void addProduct(int d, Product pro, int amt) {
		if(d < 0 || d > 6) 
			System.out.print("Day is from Sunday(0) to Saturday(6).");
		else {
			if(this.subscription[d] != null) {
				if(this.subscription[d].containsKey(pro)) {
					int value = this.subscription[d].get(pro);
					this.subscription[d].put(pro, value+amt);
				}
				else
					this.subscription[d].put(pro, amt);
			}
			else
				this.subscription[d].put(pro, amt);
		}		
	}
	
	public HashMap<Product, Integer> getSubofDay(int d) {
		if(d < 0 || d > 6) 
			System.out.print("Day is from Sunday(0) to Saturday(6).");
		else {
			return this.subscription[d];
		}
		return null;
	}
	
	public void cancelOneProdcutForOneDay(int d, Product pro) {
		if(d < 0 || d > 6) 
			System.out.print("Day is from Sunday(0) to Saturday(6).");
		else {
			this.subscription[d].remove(pro);
		}	
	}
	
	public void cancelOneProduct(Product pro) {
		for(int i=0; i < 7; i++) {
			this.subscription[i].remove(pro);
		}
	}
	
	public void cancelOneDay(int d) {
		if(d < 0 || d > 6) 
			System.out.print("Day is from Sunday(0) to Saturday(6).");
		else {
			this.subscription[d].clear();
		}	
	}
	
	public void modifySubscription(int d, Product pro, int amt) {
		if(d < 0 || d > 6) 
			System.out.print("Day is from Sunday(0) to Saturday(6).");
		else {
			if(this.subscription[d].containsKey(pro)) {
				this.subscription[d].put(pro, amt);
			}
			else
				System.out.print("There's no subscription of this product!");
		}	
	}
	
	public void printsubscription() {
		for(int i = 0; i < 7; i++) {
			if(this.subscription[i].size() > 0) {
				switch(i) {
				case 0: System.out.println("Sunday:"); break;
				case 1: System.out.println("Monday:"); break;
				case 2: System.out.println("Tuesday:"); break;
				case 3: System.out.println("Wednesday:"); break;
				case 4: System.out.println("Thursday:"); break;
				case 5: System.out.println("Friday:"); break;
				case 6: System.out.println("Satursday:"); break;
				}
			}
			for(Product name: this.subscription[i].keySet()) {
	            String key = name.getPname();
	            int value = this.subscription[i].get(name);  
	            System.out.println("\t" + key + ": " + value);  
	        }
		}
	}

}
