/**
 * @author wangzh
 * 
 * 生产产品，将产品放置到共享空间中
 *
 */
public class PC3_Producer implements Runnable{

	private PC3_Goods pC3_goods;
	
	public PC3_Producer (PC3_Goods pC3_goods) {
		this.pC3_goods = pC3_goods;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			if(i%2 == 0) {
				pC3_goods.set("娃哈哈", "矿泉水");
			}else {
				pC3_goods.set("旺仔", "小馒头");
			}
		}
		
	}
}
