package cn.edu.model;


import java.io.Serializable;

/**
 * transaction_log
 * @author 
 */
public class TransactionLog implements Serializable {
    /**
     * 事务ID
     */
    private String id;

    /**
     * 业务标识
     */
    private String business;

    /**
     * 对应业务表中的主键
     */
    private String foreignKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    private static final long serialVersionUID = 1L;
}