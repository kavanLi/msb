public class PC3_Test {
	
	public static void main(String[] args) {
		
		PC3_Goods PC3_goods = new PC3_Goods();
		
		PC3_Producer PC3_producer = new PC3_Producer(PC3_goods);
		PC3_Consumer PC3_consumer = new PC3_Consumer(PC3_goods);

		Thread t1 = new Thread(PC3_producer);
		Thread t2 = new Thread(PC3_consumer);
		
        t1.start();
        t2.start();
	}

}
