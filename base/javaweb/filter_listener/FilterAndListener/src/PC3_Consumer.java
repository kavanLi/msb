/**
 * @author wangzh
 * 
 * 从共享空间中取走数据
 *
 */
public class PC3_Consumer implements Runnable{

	private PC3_Goods PC3_goods;
	
	public  PC3_Consumer(PC3_Goods PC3_goods) {
		this.PC3_goods = PC3_goods;
	}
	
	@Override
	public void run() {
		
		for (int i = 0; i < 10; i++) {
			PC3_goods.get();
		}				
	}
}
