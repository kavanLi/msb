package com.mashibing.factory.abstract_factory;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class Client {

    private AbstractTV tv;

    private AbstractFreezer freezer;

    public Client(ApplicationsFactory factory) {
        tv = factory.createTV();
        freezer = factory.createFreezer();
    }

    public AbstractTV getTv() {
        return tv;
    }

    public void setTv(AbstractTV tv) {
        this.tv = tv;
    }

    public AbstractFreezer getFreezer() {
        return freezer;
    }

    public void setFreezer(AbstractFreezer freezer) {
        this.freezer = freezer;
    }

    public static void main(String[] args) {

        Client client = new Client(new HisenseFactory());
        AbstractTV tv = client.getTv();
        System.out.println(tv);
    }
}
