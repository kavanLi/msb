package cn.edu.demo;
/**
 * @author King老师
 * 多线程安全问题
 */
public class SaleTicket {
    public static void main(String[] args)throws Exception {
        Ticket ticket = new Ticket();
        for(int j=0;j<5;j++){  //使用5个线程
            new Thread(()->{//run方法
                for (int i = 1; i <= 10000 ; i++) {
                    ticket.sale();
                }
            }).start();
        }
        Thread.sleep(5000);
        ticket.print();
    }
}

// 票  资源类
class Ticket {
    //总票数
    private  int number = 50000;
    //售票
    public  void sale(){
        if (number>0){
            number--;   //不安全  操作简单
        }
    }
    public void print(){
        System.out.println("剩余票："+number);
    }
}
