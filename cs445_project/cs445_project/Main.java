package cs445_project;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bakery b = new Bakery("Carrie's Bakery");
		
		Subscriber s1 = new Subscriber("Carrie", "Song", "cs@hawk.com", "312","815");
		Subscriber s2 = new Subscriber("Steven", "Xu", "sx@hawk.com", "610", "815");
		Subscriber s3 = new Subscriber("Ji", "Xu", "807887012@qq.com", "13836004355", "1301");
		
		Product p1 = new Product("a-bread", 5);
		Product p2 = new Product("b-bread", 3.5);
		
		b.addSubscriber(s1);
		b.addSubscriber(s2);
		b.addSubscriber(s3);
		
		b.addProdcut(p1);
		b.addProdcut(p2);
		
		System.out.println(b.countSubscriber()); //2
		b.search("Xu");
		b.deleteSubscriber(s2);
		System.out.println(b.countSubscriber()); //1
		b.search("Xu");
		System.out.println(b.countProduct()); //2
		b.deleteProduct(p1);
		System.out.println(b.countProduct()); //1
		
		Subscription sub = new Subscription();
		sub.addProduct(1, p1, 2);
		sub.addProduct(1, p2, 2);
		sub.addProduct(3, p2, 4);
		sub.printsubscription();
		sub.modifySubscription(1, p1, 5); //amount of a-bread become 5.
		sub.printsubscription();

	}

}
