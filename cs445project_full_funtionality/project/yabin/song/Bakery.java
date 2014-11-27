package project.yabin.song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class Bakery implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8250327393828466293L;
	private AtomicInteger nextRID = new AtomicInteger();
	private AtomicInteger nextPID = new AtomicInteger();
	private AtomicInteger nextUID = new AtomicInteger();
	private String name;
	private ArrayList<Subscriber> subs;
	private ArrayList<Product> pros;
	private ArrayList<Recipe> recipes;
	private double deli = 0; //delivery charge
	private ArrayList<FutureDeli> future_delis;
	private HashMap<MyDate, HashMap<Double, Double>> historyRevenue;
	
	public class FutureDeli implements java.io.Serializable {
		MyDate date;
		double deli; //delivery charge on this future date
		
		public FutureDeli(MyDate dt, double de) {
			date = dt;
			deli = de;
		}
		
		public MyDate getDate() {
			return date;
		}
		
		public void setDate(MyDate dt) {
			this.date = dt;
		}
		
		public double getDeli() {
			return deli;
		}
		
		public void setDeli(double d) {
			this.deli = d;
		}
	}
	
	public Bakery(String n) {
		this.name = n;
		subs = new ArrayList<Subscriber>();
		pros = new ArrayList<Product>();
		recipes = new ArrayList<Recipe>();
		future_delis = new ArrayList<FutureDeli>();
		historyRevenue = new HashMap<MyDate, HashMap<Double, Double>>();
	}
	
	public ArrayList<Subscriber> getSubcribers() {
		return subs;
	}
	
	public ArrayList<Product> getProducts() {
		return pros;
	}
	
	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}
	
	public double getDeli() {
		return deli;
	}
	
	public ArrayList<FutureDeli> getFutureDelis() {
		return future_delis;
	}

	public void setDeli(double deli) {
		this.deli = deli;
	}
	
	public HashMap<MyDate, HashMap<Double, Double>> getHistoryRevenue() {
		return historyRevenue;
	}

	public void loadSubscriber(String fileName) {
		CustomerStaXParser read = new CustomerStaXParser();
	    Subscriber s = read.readCustomer(fileName);
	    boolean flag = false;
	    Iterator<Subscriber> i = subs.iterator();
		while(i.hasNext()) {
			Subscriber cur = i.next();
			if(cur.getEmail().equals(s.getEmail())) {
				System.out.println("User with email address "+cur.getEmail()+
						" has already been loaded.");
				flag = true;
			}
		}
		if(flag == false) {
			s.setUID(nextUID.getAndIncrement());
			subs.add(s);
			System.out.println(s.getUID());
		}
		
	}
	
	/*
	public void changeDelivery(double d) {
		this.setDeli(d);
	} */
	
	public void addSubscriber(Subscriber s) {
		Iterator<Subscriber> i = subs.iterator();
		while(i.hasNext()) {
			Subscriber cur = i.next();
			if(cur.getEmail().equals(s.getEmail())) {
				System.out.println("User with email address "+cur.getEmail()+
						" has already been added.");
				return;
			}
		}
		s.setUID(nextUID.getAndIncrement());
		subs.add(s);
		System.out.println(s.getUID());
	}
	
	public void viewSubscriber(int CID) {
		subs.get(CID).viewSubscriber();
	}
	
	public void modifySubscriber(int CID, String name, String e, String ph, Address a, String f, String t, String i) {
		if(!name.equals(""))
			subs.get(CID).setName(name);
		if(!e.equals(""))
			subs.get(CID).setEmail(e);
		if(!ph.equals(""))
			subs.get(CID).setPhone(ph);
		if(a != null) {
			if(!a.getStreet().equals(""))
				subs.get(CID).setStreet(a.getStreet());
			if(!a.getCity().equals(""))
				subs.get(CID).setCity(a.getCity());
			if(!a.getState().equals(""))
				subs.get(CID).setState(a.getState());
			if(!a.getZip().equals(""))
				subs.get(CID).setZip(a.getZip());
		}	
		if(!f.equals(""))
			subs.get(CID).setFacebook(f);
		if(!t.equals(""))
			subs.get(CID).setTwitter(t);
		if(!i.equals(""))
			subs.get(CID).setInstruction(i); 
	}
	
	public int countSubscriber() {
		return subs.size();
	}
	
	public int countProduct() {
		return pros.size();
	}
	
	public void viewProduct(int PID) {
		pros.get(PID).printProduct();
	}
	
	public void addProduct(String pname, double price, int RID) {
		Recipe r = recipes.get(RID);
		Product p = new Product(pname, price, r);
		p.setPID(nextPID.getAndIncrement()); 
		pros.add(p);
		System.out.println(p.getPID());
	}
	
	public Product modifyProductPrice(Product p, double price) {
		Product newProduct = p.changePrice(price);
		newProduct.setPID(nextPID.getAndIncrement());
		p.setTerminateDate(new MyDate());
		pros.add(newProduct);
		for(int i=0; i<subs.size(); i++) {
			HashMap<Product, Integer>[] sub = subs.get(i).getSub().getSubscription();
			for(int j=0; j<7; j++) {
				if(sub[j] != null && sub[j].size() != 0) {
					if(sub[j].containsKey(p)) {
						int amount = sub[j].get(p);
						sub[j].remove(p);
						sub[j].put(newProduct, amount);
					}
				}
			}
		}
		return newProduct;
	}
	
	public void terminateProduct(int PID, MyDate dt) {
		pros.get(PID).setTerminateDate(dt); 
	}
	
	public void loadRecipe(String fileName) {
		RecipeStaXParser read = new RecipeStaXParser();
		Recipe r = read.readRecipe(fileName);
		r.setRID(nextRID.getAndIncrement()); 
		recipes.add(r);
		System.out.println(r.getRID());
	}
	
	public void viewSubscription(int UID) {
		subs.get(UID).printSubscriber();
		subs.get(UID).printSubscription();
	}
	
	public void cancelSubscription(int UID) {
		subs.get(UID).cancelSub();
	}
	
	public void addModifySubscription(int UID, int dow, int PID, int quantity) {
		Product p = pros.get(PID);
		subs.get(UID).getSub().addModifyProduct(dow, p, quantity); 
	}
	
	public void suspendSubscription(int UID, MyDate first, MyDate last) {
		subs.get(UID).getSub().setSuspendDate(first, last);
 	}
	
	public void setSpecialDelivery(int UID, MyDate date, int PID, int quantity) {
		Product p = pros.get(PID);
		subs.get(UID).getSub().setSpecialDelivery(date, p, quantity);
	}
	
	public ArrayList<Subscriber> search(String kw) {
		ArrayList<Subscriber> result = new ArrayList<Subscriber>();
		Iterator<Subscriber> i = subs.iterator();
		if(kw.equals("")) {
			while(i.hasNext()) {
				Subscriber s = i.next();
				result.add(s);
				s.printSubscriber();
			}
		}
		else {
			while(i.hasNext()) {
				Subscriber s = i.next();
				if(s.isMatch(kw)) {
					result.add(s);
					s.printSubscriber();
				}
			}
		}	
		if(result.size() == 0)
			System.out.println("Nothing found.");
		return result;
	}
	
	public void getDailyDelivery(int year, int month, int day) {
		MyDate date = new MyDate(year, month, day);
		String weekDay = null;
		switch(date.getDay()) {
		case 0: weekDay = "Sunday"; break;
		case 1: weekDay = "Monday"; break;
		case 2: weekDay = "Tuesday"; break;
		case 3: weekDay = "Wednesday"; break;
		case 4: weekDay = "Thursday"; break;
		case 5: weekDay = "Friday"; break;
		case 6: weekDay = "Satursday"; break;
		} 
		System.out.println("Subscriptions for "+date+" "+weekDay+" are as follows:");
		
		for(int i=0; i < subs.size(); i++) {
			Subscriber subscriber = subs.get(i);
			if(subscriber.getSub().getStartDate() != null) {
				HashMap<Product, Integer> dailySub = subscriber.getSub().getDailySubscription(year, month, day);
				if(dailySub != null && dailySub.size() != 0) {
					System.out.println(subscriber.getName().toString()+
							"( CID: "+ subscriber.getUID() +"):");
					String dailySubscription = "";
					for(Product product: dailySub.keySet()) {
			            String key = product.getPname();
			            int pid = product.getPID();
			            int amount = dailySub.get(product);   
			            dailySubscription += ("\t" + key + "(PID: "+pid+"): " + amount + "\n");  
			        }
					System.out.println(dailySubscription);
				}
			}
		}
	}
	
	public HashMap<Product, Integer> dailyProductsNeed(int year, int month, int day) {
		MyDate date = new MyDate(year, month, day);
		HashMap<Product, Integer> productList = new HashMap<Product, Integer>();
		for(int i=0; i < subs.size(); i++) {
			if(subs.get(i).getSub().getStartDate() != null) {
				HashMap<Product, Integer> dailySub = subs.get(i).getSub().getDailySubscription(year, month, day);
				if(dailySub != null && dailySub.size() != 0) {
					for(Product product: dailySub.keySet()) {
			            int amount = dailySub.get(product);
			            if(productList.containsKey(product)) {
			    			int old = productList.get(product);
			    			productList.put(product, old+amount);
			    		}
			    		else {
			    			productList.put(product, amount);
			    		}
					}
				}
			}
		}
		
		return productList;
	}

	public HashMap<String, Double> dailyIngredientsNeed(int year, int month, int day) {
		HashMap<Product, Integer> productList = this.dailyProductsNeed(year, month, day);
		//got productList(product, amount), figure out ingredientList(name, amount)
		HashMap<String, Double> ingreList = new HashMap<String, Double>();
		for(Product pid: productList.keySet()) {
			ArrayList<Ingredient> ingredients = pid.getRecipe().getIngres();
			int units_made = pid.getRecipe().getUnits_made();
			int pamount = productList.get(pid);
			for(int i=0; i<ingredients.size(); i++) {
				String ingreName = ingredients.get(i).getIName();
				String type = ingredients.get(i).getType();
				if(type != "") {
					ingreName = ingreName + "(" + type + ")";
				}
				double amount = ingredients.get(i).getAmount()*pamount/units_made;
				if(ingreList.containsKey(ingreName)) {
					double old = ingreList.get(ingreName);
					ingreList.put(ingreName, old+amount);
				}
				else {
					ingreList.put(ingreName, amount);
				}
			}
		}
		return ingreList;
	}
	
	public void getDailyIngredientsNeed() {
		MyDate dt = new MyDate();
		dt.nextDay();
		HashMap<Product ,Integer> productList= 
				this.dailyProductsNeed(dt.getYear(), dt.getMonth(), dt.getDate());
		HashMap<String, Double> dailyNeed = 
				this.dailyIngredientsNeed(dt.getYear(), dt.getMonth(), dt.getDate());
		System.out.println("Bill of materials for "+dt.getYear()+"-"+dt.getMonth()+
				"-"+dt.getDate()+":");
		for(Product p: productList.keySet()) {
			String pname = p.getPname();
			int amt = productList.get(p);
			double price = p.getPrice();
			System.out.println("Product: " + pname + ", loafs to make: " + amt);
		}
		for(String name: dailyNeed.keySet()) {
			double amount = dailyNeed.get(name);
			System.out.println(name + ": " + amount);
		}
	}
	
	public HashMap<String, Double> getWeeklyIngredientsNeed() {
		MyDate date = new MyDate();
		date.nextDay();
		while(date.getDay() != 0)
			date.nextDay();
		System.out.println("Bill of materials for next week (start from "
				+date.getYear()+"-"+date.getMonth()+"-"+date.getDate()+"):");
		HashMap<String, Double> weeklyNeed = new HashMap<String, Double>();
		HashMap<String, Integer> weeklyP = new HashMap<String, Integer>();
		for(int i=0; i<7; i++) {
			HashMap<Product, Integer> dailyP = 
					this.dailyProductsNeed(date.getYear(), date.getMonth(), date.getDate());
			for(Product p: dailyP.keySet()) {
				String pname = p.getPname();
				int amt = dailyP.get(p);
				if(weeklyP.containsKey(pname)) {
					int old = weeklyP.get(pname);
					weeklyP.put(pname, old+amt);
				}
				else
					weeklyP.put(pname, amt);
			}
			HashMap<String, Double> daily = 
					this.dailyIngredientsNeed(date.getYear(), date.getMonth(), date.getDate());
			for(String name: daily.keySet()) {
				double amount = daily.get(name);
				if(weeklyNeed.containsKey(name)) {
					double old = weeklyNeed.get(name);
					weeklyNeed.put(name, old+amount);
				}
				else
					weeklyNeed.put(name, amount);
			}
			date.nextDay();
		}
		for(String pname: weeklyP.keySet()) {
			int amt = weeklyP.get(pname);
			System.out.println("Product: "+pname+", loafs to make: " + amt);
		}
		for(String name: weeklyNeed.keySet()) {
			double amount = weeklyNeed.get(name);
			System.out.println(name + ": " + amount);
		}
		return weeklyNeed;
	}
	
	public void delivery_list() {
		MyDate date = new MyDate();
		for(int i=0; i < subs.size(); i++) {
			Subscriber subscriber = subs.get(i);
			if(subscriber.getSub().getStartDate() != null) {
				HashMap<Product, Integer> dailySub = 
						subscriber.getSub().getDailySubscription(date.getYear(), date.getMonth(), date.getDate());
				if(dailySub != null && dailySub.size() != 0) {
					subscriber.printDeliveryList();
				}
			}
		}
	}
	
	public void delivery_charge(double deli, MyDate date) {
		
		MyDate dt = new MyDate();
		if(!date.after(dt))
			System.out.println("Date must be in the future.");
		else {
			if(deli < 0)
				System.out.println("Delivery charge must be positive number.");
			else {
				FutureDeli fd = new FutureDeli(date, deli);
				if(future_delis.size() == 0)
					future_delis.add(fd);
				else {
					Iterator<FutureDeli> i = future_delis.iterator();
					int c = 0;
					while(i.hasNext()) {
						FutureDeli cur = (FutureDeli) i.next();
						if(fd.getDate().before(cur.getDate())) {
							future_delis.add(c, fd);
							break;
						}
						else if(fd.getDate().equals(cur.getDate())){
							cur.setDeli(fd.getDeli());
							break;
						}
						else {
							c++;
						}
					}
					if(c == future_delis.size())
						future_delis.add(fd);
				}
				System.out.println("OK");
			}
		}
	}
	
	public void revenue(MyDate first, MyDate last) {
		if(first.after(last))
			System.out.println("First day can't be after last day.");
		else {
			double HPR = 0;
			double HDR = 0;
			double FPR = 0;
			double FDR = 0;
			MyDate date = new MyDate();
			if(first.after(date)) {
				//all futures
				MyDate dt = new MyDate(first.getYear(), first.getMonth(), first.getDate());
				while(!dt.after(last)) {
					HashMap<Product, Integer> productList = new HashMap<Product, Integer>();
					for(int i=0; i < subs.size(); i++) {
						if(subs.get(i).getSub().getStartDate() != null) {
							HashMap<Product, Integer> dailySub = 
									subs.get(i).getSub().getDailySubscription(dt.getYear(), dt.getMonth(), dt.getDate());
							if(dailySub != null && dailySub.size() != 0) {
								//add a future deli_charge
								Iterator<FutureDeli> ite = future_delis.iterator();
								int c = 0;
								while(ite.hasNext()) {
									FutureDeli fd = ite.next();
									if(dt.equals(fd.getDate())) {
										FDR += fd.getDeli();
										break;
									}
									else if(fd.getDate().before(dt))
										c++;
									else {
										if(c == 0)
											FDR += this.getDeli();
										else
											FDR += future_delis.get(c-1).getDeli();
										break;
									}
									FDR += future_delis.get(c-1).getDeli();
								}
								for(Product product: dailySub.keySet()) {
						            int amount = dailySub.get(product);
						            if(productList.containsKey(product)) {
						    			int old = productList.get(product);
						    			productList.put(product, old+amount);
						    		}
						    		else {
						    			productList.put(product, amount);
						    		}
								}
							}
						}
					}
					//calculate product revenue according to productList
					for(Product p: productList.keySet()) {
						double price = p.getPrice();
						int amt = productList.get(p);
						 FPR += price * amt;
					}
					dt.nextDay();
				}
				
			}
			else if(last.before(date)) { // all history
				MyDate dt = new MyDate(first.getYear(), first.getMonth(), first.getDate());
				while(!dt.after(last)) {
					if(historyRevenue.containsKey(dt)) {
						HashMap<Double, Double> dd = historyRevenue.get(dt);
						for(Double d: dd.keySet()) {
							HPR += d;
							HDR += dd.get(d);
						}
					}
					dt.nextDay();
				}
			}
			else {
				//first to date [), history
				MyDate dt = new MyDate(first.getYear(), first.getMonth(), first.getDate());
				while(dt.before(date)) {
					if(historyRevenue.containsKey(dt)) {
						HashMap<Double, Double> dd = historyRevenue.get(dt);
						for(Double d: dd.keySet()) {
							HPR += d;
							HDR += dd.get(d);
						}
					}
					dt.nextDay();
				}
				//today's confirmed revenue
				for(MyDate d: historyRevenue.keySet()) {
					if(d.equals(dt)) {
					HashMap<Double, Double> dd = historyRevenue.get(d);
					for(Double p: dd.keySet()) {
						HPR += p;
						HDR += dd.get(p);
					}
					}
				}
				//today's un-confirmed revenue
				HashMap<Product, Integer> productList = new HashMap<Product, Integer>();
				for(int i=0; i < subs.size(); i++) {
					if(subs.get(i).getConfirmed() == null || 
							subs.get(i).getConfirmed().before(dt)) {
						if(subs.get(i).getSub().getStartDate() != null) {
							HashMap<Product, Integer> dailySub = 
									subs.get(i).getSub().getDailySubscription(dt.getYear(), dt.getMonth(), dt.getDate());
							if(dailySub != null && dailySub.size() != 0) {
								//add a future deli_charge
								Iterator<FutureDeli> ite = future_delis.iterator();
								int c = 0;
								while(ite.hasNext()) {
									FutureDeli fd = ite.next();
									if(dt.equals(fd.getDate())) {
										FDR += fd.getDeli();
										break;
									}
									else if(fd.getDate().before(dt))
										c++;
									else {
										if(c == 0)
											FDR += this.getDeli();
										else
											FDR += future_delis.get(c-1).getDeli();
										break;
									}
								}
								for(Product product: dailySub.keySet()) {
						            int amount = dailySub.get(product);
						            if(productList.containsKey(product)) {
						    			int old = productList.get(product);
						    			productList.put(product, old+amount);
						    		}
						    		else {
						    			productList.put(product, amount);
						    		}
								}
							}
						}
					}
				}
				dt.nextDay();
				//date to last (], future
				while(!dt.after(last)) {
					
					for(int i=0; i < subs.size(); i++) {
						if(subs.get(i).getSub().getStartDate() != null) {
							HashMap<Product, Integer> dailySub = 
									subs.get(i).getSub().getDailySubscription(dt.getYear(), dt.getMonth(), dt.getDate());
							if(dailySub != null && dailySub.size() != 0) {
								//add a future deli_charge
								Iterator<FutureDeli> ite = future_delis.iterator();
								int c = 0;
								while(ite.hasNext()) {
									FutureDeli fd = ite.next();
									if(dt.equals(fd.getDate())) {
										FDR += fd.getDeli();
										break;
									}
									else if(fd.getDate().before(dt))
										c++;
									else {
										if(c == 0)
											FDR += this.getDeli();
										else
											FDR += future_delis.get(c-1).getDeli();
										break;
									}
								}
								for(Product product: dailySub.keySet()) {
						            int amount = dailySub.get(product);
						            if(productList.containsKey(product)) {
						    			int old = productList.get(product);
						    			productList.put(product, old+amount);
						    		}
						    		else {
						    			productList.put(product, amount);
						    		}
								}
							}
						}
					}
					//calculate product revenue according to productList
					for(Product p: productList.keySet()) {
						double price = p.getPrice();
						int amt = productList.get(p);
						 FPR += price * amt;
					}
					dt.nextDay();
				}
			}
			System.out.println("Revenue realized between "+first.toString()+
					" and "+last.toString()+":");
			System.out.println("Bakery products: $"+HPR);
			System.out.println("Delivery fees: $"+HDR);
			System.out.println("Revenue projected between "+first.toString()+
					" and "+last.toString()+":");
			System.out.println("Bakery products: $"+FPR);
			System.out.println("Delivery fees: $"+FDR);
		}
	}


	public void confirm_delivery(int UID) {
		double pRevenue = 0;
		double deliRevenue = this.getDeli();
		MyDate date = new MyDate();
		HashMap<Product, Integer> productList = this.getSubcribers().get(UID).
				getSub().getDailySubscription(date.getYear(), date.getMonth(), date.getDate());
		for(Product p: productList.keySet()) {
			double price = p.getPrice();
			int amt = productList.get(p);
			pRevenue += price * amt;
		}
		HashMap<Double, Double> dailyRevenue = new HashMap<Double, Double>();
		boolean flag = false;
		if(historyRevenue.size() > 0) {
			
			if(this.historyRevenue.containsKey(date))
				flag = true;
		
		
		if(flag = false) {
			
			dailyRevenue.put(pRevenue, deliRevenue);
			historyRevenue.put(date, dailyRevenue);
		}
		else {
			HashMap<Double, Double> old = historyRevenue.get(date);
			for(Double oldPR: old.keySet()) {
				double oldDR = old.get(oldPR);
				pRevenue += oldPR;
				deliRevenue += oldDR;
			}
			dailyRevenue.put(pRevenue, deliRevenue);
			historyRevenue.put(date, dailyRevenue);
		}
		}
		else {
			dailyRevenue.put(pRevenue, deliRevenue);
			historyRevenue.put(date, dailyRevenue);
		}
		this.getSubcribers().get(UID).setConfirmed(date);		
		System.out.println("OK");
	}

}
