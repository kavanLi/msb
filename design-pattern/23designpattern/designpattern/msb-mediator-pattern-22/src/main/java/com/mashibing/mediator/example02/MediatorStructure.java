package com.mashibing.mediator.example02;

/**
 * 具体的中介者-中介机构
 * @author spikeCong
 * @date 2022/10/21
 **/
public class MediatorStructure extends Mediator {

    //中介知晓 房租出租人和承租人的信息
    private HouseOwner houseOwner;  //房主

    private Tenant tenant;  //租房者

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public HouseOwner getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(HouseOwner houseOwner) {
        this.houseOwner = houseOwner;
    }



    @Override
    public void contact(String message, Person person) {
        if(person == houseOwner){
            //如果是房主,则租房者获得信息
            tenant.getMessage(message);
        }else{
            //如果是租房者,则房租获得信息
            houseOwner.getMessage(message);
        }
    }
}
