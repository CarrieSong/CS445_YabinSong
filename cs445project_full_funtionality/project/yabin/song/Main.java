package project.yabin.song;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
	
	public static void saveBakeryState(Bakery b) throws IOException {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream("bakery-yabin-song.ser");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(b);
			//System.out.println("save complete");
		} catch (IOException e) {
		        e.printStackTrace();			        
		} finally {
			if(oos != null) {
				oos.close();
			} 
		}
	}

	
	public static Bakery restoreBakeryState(Bakery b) throws IOException {
		ObjectInputStream ois = null;
		try {
			FileInputStream fis = new FileInputStream("bakery-yabin-song.ser");
			ois = new ObjectInputStream(fis);
			b = (Bakery)ois.readObject();
			//System.out.println("restore complete");
			return b;
		} catch (IOException e) {
			//System.err.println("Nothing to restore.\n");
		} 
		catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException caught in restoreBakeryState()");
			e.printStackTrace();
		}
			finally {
			if (ois != null) {
				ois.close();
			} 
		}
		return b;
	}
	
	public static void addOptions(Options options) {
		options.addOption("search", false, "search for customer");
		options.addOption("k", true, "keyword for search");
	    options.addOption("customer", false, "commands for customer" );
	    options.addOption("add", false, "add something");
	    options.addOption("modify", false, "modify something");
	    options.addOption("view", false, "view something");
	    options.addOption("load", false, "load something from xml file");
	    options.addOption("n", true, "set name");
	    options.addOption("e", true, "set email");
	    options.addOption("a", true, "set address street");
	    options.addOption("c", true, "set city");
	    options.addOption("s", true, "set state");
	    options.addOption("z", true, "set zip");
	    options.addOption("h", true, "set phone");
	    options.addOption("f", true, "set facebook or load file, or a date");
	    options.addOption("t", true, "set twitter");
	    options.addOption("i", true, "delievery instruction");
	    options.addOption("u", true, "user ID");
	    options.addOption("product", false, "commands for product");
	    options.addOption("terminate", false, "terminate a product");
	    options.addOption("d", true, "set product price");
	    options.addOption("r", true, "receipe ID");
	    options.addOption("p", true, "product ID");
	    options.addOption("q", true, "set quantity");
	    options.addOption("l", true, "set last date");
	    options.addOption("recipe", false, "commands for recipe");
	    options.addOption("subscription", false, "commands for subscription");
	    options.addOption("w", true, "day of week"); 
	    options.addOption("addmodify", false, "add or modify a subscription");
	    options.addOption("suspend", false, "suspend a subscription");
	    options.addOption("cancel", false, "cancel a subscription");
	    options.addOption("admin", false, "admin something");
	    options.addOption("daily", false, "get tomorrow's ingredients needed");
	    options.addOption("weekly", false, "get next week's ingredients needed");
	    options.addOption("revenue", false, "get revenue between a period");
	    options.addOption("delivery_list", false, "get delivery list for today");
	    options.addOption("delivery_charge", false, "change delivery charge");
	    options.addOption("confirm_delivery", false, "confirm a delivery");
	}

	public static MyDate toDate(String s) {
		int year = 2000 + Integer.parseInt(s.substring(4));
		int month = Integer.parseInt(s.substring(0, 2));
		int date1 = Integer.parseInt(s.substring(2, 4));
		MyDate dt = new MyDate(year, month, date1);
		return dt;
	}
	
	public static void main(String[] args) throws ParseException {
		
		// Try restoring bakery state
		Bakery b = new Bakery("Panem Nostrum");
		try {
			b = restoreBakeryState(b);
		} catch (IOException e) {
			System.err.println("IOException caught when trying to restore state");
		}
		
		Options options = new Options();
		addOptions(options);
	    
	    CommandLine line = new BasicParser().parse( options, args );
	    if(line.hasOption("search")) {
	    	if(line.hasOption("k")) {
	    		String keyword = line.getOptionValue("k");
	    		b.search(keyword);
	    	}
	    	else {
	    		b.search("");
	    	}
	    }
	    else if( line.hasOption( "customer" ) ) {
	    	if( line.hasOption( "add" ) ) {
	    		if(!(line.hasOption( "n" ) && line.hasOption( "e" ) 
	    				&& line.hasOption( "a" ) && line.hasOption( "c" )
	    				&& line.hasOption( "s" ) && line.hasOption( "z" )
	    				&& line.hasOption( "h" ))) {
	    			System.out.println("Lack of information.");
	    		}
	    		else {
	    			Subscriber s = new Subscriber();
		    		Address a = new Address();
	    			
		    		String repeat = line.getOptionValue("n");
	    			s.setName(repeat);
	    			repeat = line.getOptionValue("e");
	    			s.setEmail(repeat);
	    			repeat = line.getOptionValue("a");
	    			a.setStreet(repeat);
	    			repeat = line.getOptionValue("c");
	    			a.setCity(repeat);
	    		    repeat = line.getOptionValue("s");
	    			a.setState(repeat);
	    			repeat = line.getOptionValue("z");
	    			a.setZip(repeat);
	    			repeat = line.getOptionValue("h");
	    			s.setPhone(repeat); 		
		    		if(line.hasOption("f")) {
		    			repeat = line.getOptionValue("f");
		    			s.setFacebook(repeat);
		    		}
		    		if(line.hasOption("t")) {
		    			repeat = line.getOptionValue("t");
		    			s.setTwitter(repeat);
		    		}
		    		if(line.hasOption("i")) {
		    			repeat = line.getOptionValue("i");
		    			s.setInstruction(repeat);
		    		}
		    		s.setAddress(a);
		    		b.addSubscriber(s);
	    		}	
			}
	    	else if(line.hasOption("modify")) {
	    		if(!line.hasOption("u")) {
	    			System.out.println("User ID required.");
	    		}
	    		else {
	    			String cid = line.getOptionValue("u");
	    			int CID = Integer.parseInt(cid);
	    			String name = "";
	    			String email = "";
	    			String phone = "";
	    			String street = "";
	    			String city = "";
	    			String state = "";
	    			String zip = "";
	    			String facebook = "";
	    			String twitter = "";
	    			String instruction = "";
	    			Address addr;
	    			if(line.hasOption("n"))
	    				name = line.getOptionValue("n");
	    			if(line.hasOption("e"))
	    				email = line.getOptionValue("e");
	    			if(line.hasOption("h"))
	    				phone = line.getOptionValue("h");
	    			if(line.hasOption("f"))
	    				facebook = line.getOptionValue("f");
	    			if(line.hasOption("t"))
	    				twitter = line.getOptionValue("t");
	    			if(line.hasOption("i"))
	    				instruction = line.getOptionValue("i");
	    			if(!line.hasOption("a") && !line.hasOption("c")
	    					&& !line.hasOption("s") && !line.hasOption("z")) {
	    				addr = null;
	    			}
	    			else {
	    				if(line.hasOption("a"))
	    					street = line.getOptionValue("a");
	    				if(line.hasOption("c"))
	    					city = line.getOptionValue("c");
	    				if(line.hasOption("s"))
	    					state = line.getOptionValue("s");
	    				if(line.hasOption("z"))
	    					zip = line.getOptionValue("z");
	    				addr = new Address(street, city, state, zip);
	    			}
	    			b.modifySubscriber(CID, name, email, phone, addr, facebook, twitter, instruction);
	    			b.getSubcribers().get(CID).printSubscriber();
	    		}
	    	}
	    	else if(line.hasOption("view")) {
	    		if(!line.hasOption("u")) {
	    			System.out.println("User ID required.");
	    		}
	    		else {
	    			String cid = line.getOptionValue("u");
	    			int CID = Integer.parseInt(cid);
	    			b.viewSubscriber(CID); 
	    		}
	    	}
	    	else if(line.hasOption("load")) {
	    		if(!line.hasOption("f"))
	    			System.out.println("XML file required.");
	    		else {
	    			String file = line.getOptionValue("f");
	    			b.loadSubscriber(file);
	    		}
	    	}
	    	else
	    		System.out.println("Wrong command.");
	    }
	    else if(line.hasOption("product")) {
	    	if(line.hasOption("add")) {
	    		if(!line.hasOption("n") || !line.hasOption("d") || !line.hasOption("r"))
	    			System.out.println("Lack of information.");
	    		else {
	    			String name = line.getOptionValue("n");
	    			double price = Double.parseDouble(line.getOptionValue("d"));
	    			if(price <= 0)
	    				System.out.println("Price must be greater than zero.");
	    			else {
	    				int RID = Integer.parseInt(line.getOptionValue("r"));
	    				if(RID >= b.getRecipes().size()) 
	    					System.out.println("No such recipe. (id="+RID+")");
	    				else
	    					b.addProduct(name, price, RID); 
	    			}
	    			
	    		}
	    	}
	    	else if(line.hasOption("modify")) {
	    		if(! line.hasOption("p"))
	    			System.out.println("Product ID required (-p)");
	    		else {
	    			if(line.hasOption("r")) {
    					int RID = Integer.parseInt(line.getOptionValue("r"));
    					if(RID >= b.getRecipes().size())
    						System.out.println("No such recipe. (id=" + RID +")");
	    			}
	    			else {
	    				int PID = Integer.parseInt(line.getOptionValue("p"));
		    			if(line.hasOption("d")) {
		    				double price = Double.parseDouble(line.getOptionValue("d"));
		    				if(price <= 0)
		    					System.out.println("Price must be greater than zero.");
		    				else {
		    					Product newP = b.modifyProductPrice(b.getProducts().get(PID), price);
		    					if(line.hasOption("n")) 
			    					newP.setPname(line.getOptionValue("n"));
			    				if(line.hasOption("r")) {
			    					int RID = Integer.parseInt(line.getOptionValue("r"));
			    					Recipe r = b.getRecipes().get(RID);
			    					newP.setRecipe(r); 	
			    				}
			    				System.out.println("OK");
		    				}		
		    			}
		    			else {
		    				if(line.hasOption("n")) {
		    					String pname = line.getOptionValue("n");
		    					b.getProducts().get(PID).setPname(pname);
		    				}
		    				if(line.hasOption("r")) {
		    					int RID = Integer.parseInt(line.getOptionValue("r"));
		    					Recipe r = b.getRecipes().get(RID);
		    					b.getProducts().get(PID).setRecipe(r); 
		    				}
		    				System.out.println("OK");
		    			}
		    			
	    			}	
	    		}
	    	}
	    	else if(line.hasOption("view")) {
	    		if(! line.hasOption("p"))
	    			System.out.println("Product ID required.");
	    		else {
	    			int PID = Integer.parseInt(line.getOptionValue("p"));
	    			b.viewProduct(PID); 
	    		}
	    	}
	    	else if(line.hasOption("terminate")) {
	    		if(! line.hasOption("p") || ! line.hasOption("f"))
	    			System.out.println("Product ID and terminate date required.");
	    		else {
	    			int PID = Integer.parseInt(line.getOptionValue("p"));
	    			MyDate dt = toDate(line.getOptionValue("f")); 
	    			b.terminateProduct(PID, dt); 
	    		}
	    	}
	    	else
	    		System.out.println("Wrong command.");
	    }
	    else if(line.hasOption("recipe")) {
	    	if(line.hasOption("load")) {
	    		if(! line.hasOption("f"))
	    			System.out.println("XML file needed.");
	    		else {
	    			String file = line.getOptionValue("f");
	    			b.loadRecipe(file); 
	    		}
	    	}
	    	else 
	    		System.out.println("Wrong commands.");
	    }
	    else if(line.hasOption("subscription")) {
	    	if(line.hasOption("addmodify")) {
	    		if(! line.hasOption("u") || ! line.hasOption("p") ||
	    				! line.hasOption("q")) {
	    			System.out.println("Lack of information.");
	    		}
	    		else {
	    			if((line.hasOption("w") && line.hasOption("f")) ||
	    					(!line.hasOption("w") && !line.hasOption("f")))
	    				System.out.println("Wrong commands.");
	    			else {
	    				int UID = Integer.parseInt(line.getOptionValue("u"));
	    				if(UID >= b.getSubcribers().size())
	    					System.out.println("No such user. (id=" + UID + ")");
	    				else {
	    					int PID = Integer.parseInt(line.getOptionValue("p"));
	    					if(PID >= b.getProducts().size())
	    						System.out.println("No such product. (id=" + PID + ")");
	    					else {
	    						int q = Integer.parseInt(line.getOptionValue("q"));
	    						if(q <= 0)
	    							System.out.println("The quantity must be greater than zero.");
	    						else {
	    							if(line.hasOption("w")) {
				    					int dow = Integer.parseInt(line.getOptionValue("w"));
				    					b.addModifySubscription(UID, dow, PID, q); 
				    					
				    				}
				    				else if(line.hasOption("f")) {
				    	    			MyDate dt = toDate(line.getOptionValue("f")); 
				    	    			if(!dt.after(new MyDate()))
				    	    				System.out.println("Date must be in the future.");
				    	    			else {
				    	    				b.setSpecialDelivery(UID, dt, PID, q); 
				    	    				System.out.println("OK");
				    	    			}
				    				}
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	else if(line.hasOption("view")) {
	    		if(! line.hasOption("u"))
	    			System.out.println("User ID required.");
	    		else {
	    			int UID = Integer.parseInt(line.getOptionValue("u"));	
	    			b.viewSubscription(UID);
	    		}
	    	}
	    	else if(line.hasOption("cancel")) {
	    		if(! line.hasOption("u"))
	    			System.out.println("User ID required.");
	    		else {
	    			int UID = Integer.parseInt(line.getOptionValue("u"));
	    			b.cancelSubscription(UID);
	    		}
	    	}
	    	else if(line.hasOption("suspend")) {
	    		if(! line.hasOption("u") || ! line.hasOption("f") ||
	    				! line.hasOption("l"))
	    			System.out.println("Lack of information.");
	    		else {
	    			int UID = Integer.parseInt(line.getOptionValue("u"));
	    			MyDate f = toDate(line.getOptionValue("f")); 
	    			MyDate l = toDate(line.getOptionValue("l")); 
	    			b.suspendSubscription(UID, f, l); 
	    		}
	    	}
	    	else
	    		System.out.println("Wrong command.");
	    }
	    else if(line.hasOption("admin")) {
	    	if(line.hasOption("daily")) {
	    		b.getDailyIngredientsNeed();
	    	}
	    	else if(line.hasOption("weekly")) {
	    		b.getWeeklyIngredientsNeed();
	    	}
	    	else if(line.hasOption("revenue")) {
	    		if(!line.hasOption("f") || !line.hasOption("l"))
	    			System.out.println("First date and last date needed.");
	    		else {
	    			MyDate f = toDate(line.getOptionValue("f")); 
	    			MyDate l = toDate(line.getOptionValue("l")); 
	    			b.revenue(f, l); 
	    		}
	    	}
	    	else if(line.hasOption("delivery_list")) {
	    		b.delivery_list();
	    	}
	    	else if(line.hasOption("delivery_charge")) {
	    		if(!line.hasOption("d") || !line.hasOption("f"))
	    			System.out.println("Lack of information.");
	    		else {
	    			double charge = Double.parseDouble(line.getOptionValue("d"));
	    			MyDate dt = toDate(line.getOptionValue("f")); 
	    			b.delivery_charge(charge, dt); 
	    		}
	    	}
	    	else if(line.hasOption("confirm_delivery")) {
	    		if(!line.hasOption("u"))
	    			System.out.println("User ID required.");
	    		else {
	    			int UID = Integer.parseInt(line.getOptionValue("u"));  
	    			b.confirm_delivery(UID);
	    		}
	    	}
	    	else
	    		System.out.println("Wrong command.");
	    }
	    else
	    	System.out.println("Wrong command.");
		// Try saving bakery state
		try {
			saveBakeryState(b);			
		} catch (IOException e) {
			System.err.println("IOException caught when trying to save state.\n");
		}
		
	}
}
