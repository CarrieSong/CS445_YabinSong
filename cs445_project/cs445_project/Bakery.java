package cs445_project;

import java.util.ArrayList;
import java.util.Iterator;

public class Bakery {

	private String name;
	private ArrayList<Subscriber> subs;
	private ArrayList<Product> pros;
	private double delivery = 2; //delivery charge
	
	public Bakery(String n) {
		this.name = n;
		subs = new ArrayList<Subscriber>();
		pros = new ArrayList<Product>();
	}
	
	public void changeDelivery(double d) {
		this.delivery = d;
	}
	
	public void addSubscriber(Subscriber s) {
		subs.add(s);
	}
	
	public void deleteSubscriber(Subscriber s) {
		subs.remove(s);
	}
	
	public int countSubscriber() {
		return subs.size();
	}
	
	public int countProduct() {
		return pros.size();
	}
	
	public void addProdcut(Product p) {
		pros.add(p);
	}
	
	public void deleteProduct(Product p) {
		pros.remove(p);
	}
	
	public ArrayList<Subscriber> search(String kw) {
		ArrayList<Subscriber> result = new ArrayList<Subscriber>();
		Iterator<Subscriber> i = subs.iterator();
		while(i.hasNext()) {
			Subscriber s = i.next();
			if(s.isMatch(kw)) {
				result.add(s);
				s.printSubscriber();
			}
		}
		if(result.size() == 0)
			System.out.println("No such a subscriber.");
		return result;
	}

}
