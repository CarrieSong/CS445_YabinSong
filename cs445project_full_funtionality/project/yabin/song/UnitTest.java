package project.yabin.song;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnitTest {

	Bakery b = new Bakery("Carrie's Bakery");
	
	@Test
	public void test_getSubscribers() {
		assertEquals(b.getSubcribers().size(), 0);
	}
	
	@Test
	public void test_getProducts() {
		assertEquals(b.getProducts().size(), 0);
	}
	
	@Test
	public void test_getRecipes() {
		assertEquals(b.getRecipes().size(), 0);
	}
	
	@Test
	public void test_getDeli() {
		assertEquals(b.getDeli(), 0, 0);
	}
	
	@Test
	public void test_setDeli() {
		b.setDeli(2);
		assertEquals(b.getDeli(), 2, 0);
	}
	
	@Test
	public void test_getFutureDelis() {
		assertEquals(b.getFutureDelis().size(), 0);
	}
	
	@Test
	public void test_getHistoryRevenue() {
		assertEquals(b.getHistoryRevenue().size(), 0);
	}
	
	@Test
	public void test_loadSubscriber() {
		b.loadSubscriber("customer1.xml");
		assertEquals(b.getSubcribers().size(), 1);
		b.loadSubscriber("customer1.xml");
		assertEquals(b.getSubcribers().size(), 1);
		b.loadSubscriber("customer2.xml");
		assertEquals(b.getSubcribers().size(), 2);
	}
	
	@Test
	public void test_addSubscriber() {
		b.addSubscriber(new Subscriber());
		assertEquals(b.getSubcribers().size(), 1);
		b.addSubscriber(new Subscriber());
		assertEquals(b.getSubcribers().size(), 1);
		b.modifySubscriber(0, "Carrie", "ysong46@iit.edu", "3124200000", new Address(), "fb", "tt", "haha");
		b.addSubscriber(new Subscriber());
		assertEquals(b.getSubcribers().size(), 2);
	}
	
	@Test
	public void test_modifySubscriber() {
		b.addSubscriber(new Subscriber());
		Address a = new Address("2801 S King Dr", "Chicago", "IL", "60616");
		b.modifySubscriber(0, "Carrie", "ysong46@iit.edu", "3124200000", a, "fb", "tt", "haha");
		assertEquals(b.getSubcribers().get(0).getName(), "Carrie");
		assertEquals(b.getSubcribers().get(0).getEmail(), "ysong46@iit.edu");
		assertTrue(b.getSubcribers().get(0).getAddress().getState().equals("IL"));
		assertEquals(b.getSubcribers().get(0).getFacebook(), "fb");
		assertEquals(b.getSubcribers().get(0).getTwitter(), "tt");
		assertEquals(b.getSubcribers().get(0).getInstruction(), "haha");
		b.modifySubscriber(0, "", "", "", null, "", "", "");
		assertEquals(b.getSubcribers().get(0).getName(), "Carrie");
		assertEquals(b.getSubcribers().get(0).getEmail(), "ysong46@iit.edu");
		assertTrue(b.getSubcribers().get(0).getAddress().getState().equals("IL"));
		assertEquals(b.getSubcribers().get(0).getFacebook(), "fb");
		assertEquals(b.getSubcribers().get(0).getTwitter(), "tt");
		assertEquals(b.getSubcribers().get(0).getInstruction(), "haha");
		Address aa = new Address("", "", "", "");
		b.modifySubscriber(0, "", "", "", aa, "", "", "");
		assertEquals(b.getSubcribers().get(0).getName(), "Carrie");
		assertEquals(b.getSubcribers().get(0).getEmail(), "ysong46@iit.edu");
		assertTrue(b.getSubcribers().get(0).getAddress().getState().equals("IL"));
		assertEquals(b.getSubcribers().get(0).getFacebook(), "fb");
		assertEquals(b.getSubcribers().get(0).getTwitter(), "tt");
		assertEquals(b.getSubcribers().get(0).getInstruction(), "haha");
	}
	
	@Test
	public void test_countSubscriber() {
		assertEquals(b.countSubscriber(), 0);
		b.addSubscriber(new Subscriber());
		assertEquals(b.countSubscriber(), 1);
	}
	
	@Test
	public void test_countProduct() {
		assertEquals(b.countProduct(), 0);
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		assertEquals(b.countProduct(), 1);
	}
	
	@Test
	public void test_modifyProductPrice() {
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		assertEquals(b.countProduct(), 1);
		b.addSubscriber(new Subscriber());
		b.addModifySubscription(0, 0, 0, 4);
		assertEquals(b.getSubcribers().get(0).getSub().weeklyValue(), 8, 0);
		b.modifyProductPrice(b.getProducts().get(0), 5);
		assertEquals(b.getProducts().size(), 2);
		assertEquals(b.getProducts().get(1).getPrice(), 5, 0);
		assertEquals(b.getSubcribers().get(0).getSub().weeklyValue(), 20, 0);
	}
	
	@Test
	public void test_terminateProduct() {
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.terminateProduct(0, new MyDate(2014, 12, 20));
		assertEquals(b.getProducts().get(0).getTerminateDate(), new MyDate(2014, 12, 20));
	}
	
	@Test
	public void test_addModifySubscription() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.addModifySubscription(0, 0, 0, 5);
		assertEquals(b.getSubcribers().get(0).getSub().getSubscription()[0].size(),1);
	}
	
	@Test
	public void test_cancelSubscription() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.addModifySubscription(0, 0, 0, 5);
		b.cancelSubscription(0);
		assertEquals(b.getSubcribers().get(0).getSub().getSubscription()[0].size(),0);
	}
	
	@Test
	public void test_suspendSubscription() {
		b.addSubscriber(new Subscriber());
		b.suspendSubscription(0, new MyDate(2014, 12, 20), new MyDate(2014, 12, 25));
		assertEquals(b.getSubcribers().get(0).getSub().getSuspendFirstDate(),
				new MyDate(2014, 12, 20));
		assertEquals(b.getSubcribers().get(0).getSub().getSuspendLastDate(),
				new MyDate(2014, 12, 25));
	}
	
	@Test
	public void test_setSpecialDelivery() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		String pname = b.getProducts().get(0).getPname();
		b.setSpecialDelivery(0, new MyDate(2014, 12, 20), 0, 2);
		assertEquals(b.getSubcribers().get(0).getSub().getSpecialDelivery(),
				"Future special deliveries: SAT, 12/20/14, 2 x "+pname);
	}
	
	@Test
	public void test_search() {
		assertEquals(b.search("").size(), 0);
		b.addSubscriber(new Subscriber());
		assertEquals(b.search("").size(), 1);
		assertEquals(b.search("example").size(), 1);
		assertEquals(b.search("carrie").size(), 0);
	}
	
	@Test
	public void test_getDailyDelivery() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.setSpecialDelivery(0, new MyDate(2014, 12, 20), 0, 2);
		b.getDailyDelivery(2014, 12, 20); 
	}
	
	@Test
	public void test_getDailyIngredientsNeed() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.setSpecialDelivery(0, new MyDate(2014, 11, 28), 0, 2);
		b.getDailyIngredientsNeed();
	}
	
	@Test
	public void test_getWeeklyIngredientsNeed() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.setSpecialDelivery(0, new MyDate(2014, 11, 30), 0, 2);
		assertEquals(b.getWeeklyIngredientsNeed().size(), 4);
	}
	
	@Test
	public void test_delivery_list() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.setSpecialDelivery(0, new MyDate(2014, 11, 27), 0, 2);
		b.delivery_list();
	}
	
	@Test
	public void test_delivery_charge() {
		b.delivery_charge(2, new MyDate(2014, 11, 1));
		b.delivery_charge(-2, new MyDate(2014, 12, 1));
		assertEquals(b.getFutureDelis().size(), 0);
		b.delivery_charge(2, new MyDate(2014, 12, 1));
		assertEquals(b.getDeli(), 0, 0);
		assertEquals(b.getFutureDelis().size(), 1);
		b.delivery_charge(2, new MyDate(2014, 12, 5));
		assertEquals(b.getFutureDelis().size(), 2);
		b.delivery_charge(2, new MyDate(2014, 12, 5));
		assertEquals(b.getFutureDelis().size(), 2);
		b.delivery_charge(2, new MyDate(2014, 12, 3));
		assertEquals(b.getFutureDelis().size(), 3);
	}
	
	@Test
	public void test_revenue() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.addModifySubscription(0, 4 , 0, 2);
		b.delivery_charge(2, new MyDate(2014, 12, 3));
		b.revenue(new MyDate(2013, 1, 1), new MyDate(2012, 12, 31));
		b.revenue(new MyDate(2013, 1, 1), new MyDate(2013, 12, 31));
		b.revenue(new MyDate(2015, 1, 1), new MyDate(2015, 12, 31));
		b.revenue(new MyDate(2013, 1, 1), new MyDate(2015, 12, 31));
	}
	
	@Test
	public void test_confirm_delivery() {
		b.addSubscriber(new Subscriber());
		b.loadRecipe("recipe1.xml");
		b.addProduct("bread", 2, 0);
		b.addModifySubscription(0, 4 , 0, 2);
		b.confirm_delivery(0);
		b.revenue(new MyDate(2014, 11, 27), new MyDate(2014, 11, 27)); 
	}
	
	@Test
	public void test_setRName() {
		Recipe r = new Recipe();
		r.setRName("bread");
		assertEquals(r.getRName(), "bread");
	}
	
	@Test
	public void test_setInstructions() {
		Recipe r = new Recipe();
		r.setInstructions("none");
		assertEquals(r.getInstructions(), "none");
	}
	
	@Test
	public void test_recipe_toString() {
		Recipe r = new Recipe();
		r.setRID(1);
		r.setRName("bread");
		assertEquals(r.toString(), "recipe: bread (id=1)");
	}
	
	@Test
	public void test_product_getRID() {
		Product p = new Product();
		b.loadRecipe("recipe1.xml");
		p.setRecipe(b.getRecipes().get(0));
		assertEquals(p.getRecipeID(), 0);
	}
	
	@Test
	public void test_printProduct() {
		Product p = new Product();
		b.loadRecipe("recipe1.xml");
		p.setRecipe(b.getRecipes().get(0));
		p.printProduct();
	}
	
	@Test
	public void test_product_getStartTime() {
		Product p = new Product();
		assertEquals(p.getStartTime(), new MyDate());
	}
	
	@Test
	public void test_setMeasuring_unit() {
		Ingredient ingre = new Ingredient();
		ingre.setMeasuring_unit("oz");
		assertEquals(ingre.getMeasuring_unit(), "oz");
	}
	
	@Test
	public void test_toString() {
		Ingredient ingre = new Ingredient();
		assertEquals(ingre.toString(), 
				"Ingredient_name: \tAmount: 0.0\tMeasuring_unit: \tType: \n");
	}
	
	@Test
	public void test_subscriber_constructor() {
		Subscriber s = new Subscriber("Carrie", "ysong46@hawk.iit.edu", "3124200000", new Address());
		assertEquals(s.getName(), "Carrie");
		assertEquals(s.getEmail(), "ysong46@hawk.iit.edu");
	}
	
	@Test
	public void test_subscriber_getSince() {
		Subscriber s = new Subscriber();
		assertEquals(s.getSince(), new MyDate());
	}
	
	@Test
	public void test_subscriber_printSubscription() {
		Subscriber s = new Subscriber();
		s.printSubscription();
	}
	
	@Test
	public void test_subscriber_printSubscriber() {
		Subscriber s = new Subscriber("Carrie", "ysong46@hawk.iit.edu", "3124200000", new Address());
		s.setFacebook("fb");
		s.setTwitter("tt");
		s.setInstruction("come after 6 pm.");
		s.printSubscriber();
	}
	
	@Test
	public void test_subscription_viewSubscriber() {
		Subscriber s = new Subscriber("Carrie", "ysong46@hawk.iit.edu", "3124200000", new Address());
		s.viewSubscriber();
	}
	
	@Test
	public void test_setStartDate() {
		Subscription s = new Subscription();
		s.setStartDate(new MyDate());
		assertEquals(s.getStartDate(), new MyDate());
	}
	
	@Test
	public void test_setEndDate() {
		Subscription s = new Subscription();
		s.setEndDate(new MyDate());
		assertEquals(s.getEndDate(), new MyDate());
	}
	
	@Test
	public void test_addModifyProduct1() {
		Subscription s = new Subscription();
		s.addModifyProduct(9, new Product(), 4);
		s.addModifyProduct(3, new Product(), 4);
		s.addModifyProduct(3, new Product(), 0);
	}
	
	@Test
	public void test_addModifyProduct2() {
		Subscription s = new Subscription();
		s.addModifyProduct(0, new Product(), 4);
		assertEquals(s.getDailySubscription(2014, 11, 30).size(), 1);
	}
	
	@Test
	public void test_getSuspensions() {
		Subscription s = new Subscription();
		s.setSuspendDate(new MyDate(2014, 11, 30), new MyDate(2013, 12, 30));
		s.setSuspendDate(new MyDate(2014, 11, 30), new MyDate(2014, 12, 30));
		assertEquals(s.getSuspensions(), 
				"Current or future subscription suspension(s): 11/30/14 through 12/30/14");
	}
	
	@Test
	public void test_getDailySubscription() {
		Subscription s = new Subscription();
		s.addModifyProduct(0, new Product(), 4);
		s.setSuspendDate(new MyDate(2014, 12, 20), new MyDate(2013, 12, 30));
		assertEquals(s.getDailySubscription(2014, 11, 30).size(), 1);
	}
	
	@Test
	public void test_printWeeklySubscription() {
		Subscription s = new Subscription();
		s.addModifyProduct(0, new Product(), 4);
		s.printWeeklySubscription();
	}
	
	@Test
	public void test_weeklyValue() {
		Subscription s = new Subscription();
		s.setStatus("cancelled");
		assertEquals(s.weeklyValue(), 0, 0);
	}
	
}
