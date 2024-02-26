package generator.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName saasyunst_item_managerment
 */
@Data
public class SaasyunstItemManagerment implements Serializable {
    /**
     * 编号（无意义唯一）主键、unsigned、单表时自增、步长为 1
     */
    private Long id;

    /**
     * 商户用户编号
     */
    private String bizUserId;

    /**
     * 科目编号
     */
    private String itemNo;

    /**
     * 科目名称
     */
    private String itemName;

    /**
     * 科目描述
     */
    private String itemDesc;

    /**
     * 科目层级，1-科目类别 2-科目明细
     */
    private Integer itemTier;

    /**
     * 应用场景，1-POS款项 2-H5款项
     */
    private Integer itemScene;

    /**
     * 顺序号
     */
    private Integer seqNo;

    /**
     * 价格，单位：分
     */
    private Long itemPrice;

    /**
     * 实名标识
     */
    private Integer isRealName;

    /**
     * 收款方编号
     */
    private String receiverId;

    /**
     * 收款方名称
     */
    private String receiverName;

    /**
     * 启用状态，1-启用 0-禁用
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间，格式：YYYY-MM-DD hh:mm:ss
     */
    private Date gmtCreate;

    /**
     * 更新人
     */
    private String modifiedUserName;

    /**
     * 更新时间，格式：YYYY-MM-DD hh:mm:ss
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SaasyunstItemManagerment other = (SaasyunstItemManagerment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBizUserId() == null ? other.getBizUserId() == null : this.getBizUserId().equals(other.getBizUserId()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getItemName() == null ? other.getItemName() == null : this.getItemName().equals(other.getItemName()))
            && (this.getItemDesc() == null ? other.getItemDesc() == null : this.getItemDesc().equals(other.getItemDesc()))
            && (this.getItemTier() == null ? other.getItemTier() == null : this.getItemTier().equals(other.getItemTier()))
            && (this.getItemScene() == null ? other.getItemScene() == null : this.getItemScene().equals(other.getItemScene()))
            && (this.getSeqNo() == null ? other.getSeqNo() == null : this.getSeqNo().equals(other.getSeqNo()))
            && (this.getItemPrice() == null ? other.getItemPrice() == null : this.getItemPrice().equals(other.getItemPrice()))
            && (this.getIsRealName() == null ? other.getIsRealName() == null : this.getIsRealName().equals(other.getIsRealName()))
            && (this.getReceiverId() == null ? other.getReceiverId() == null : this.getReceiverId().equals(other.getReceiverId()))
            && (this.getReceiverName() == null ? other.getReceiverName() == null : this.getReceiverName().equals(other.getReceiverName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateUserName() == null ? other.getCreateUserName() == null : this.getCreateUserName().equals(other.getCreateUserName()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifiedUserName() == null ? other.getModifiedUserName() == null : this.getModifiedUserName().equals(other.getModifiedUserName()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBizUserId() == null) ? 0 : getBizUserId().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getItemName() == null) ? 0 : getItemName().hashCode());
        result = prime * result + ((getItemDesc() == null) ? 0 : getItemDesc().hashCode());
        result = prime * result + ((getItemTier() == null) ? 0 : getItemTier().hashCode());
        result = prime * result + ((getItemScene() == null) ? 0 : getItemScene().hashCode());
        result = prime * result + ((getSeqNo() == null) ? 0 : getSeqNo().hashCode());
        result = prime * result + ((getItemPrice() == null) ? 0 : getItemPrice().hashCode());
        result = prime * result + ((getIsRealName() == null) ? 0 : getIsRealName().hashCode());
        result = prime * result + ((getReceiverId() == null) ? 0 : getReceiverId().hashCode());
        result = prime * result + ((getReceiverName() == null) ? 0 : getReceiverName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateUserName() == null) ? 0 : getCreateUserName().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifiedUserName() == null) ? 0 : getModifiedUserName().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bizUserId=").append(bizUserId);
        sb.append(", itemNo=").append(itemNo);
        sb.append(", itemName=").append(itemName);
        sb.append(", itemDesc=").append(itemDesc);
        sb.append(", itemTier=").append(itemTier);
        sb.append(", itemScene=").append(itemScene);
        sb.append(", seqNo=").append(seqNo);
        sb.append(", itemPrice=").append(itemPrice);
        sb.append(", isRealName=").append(isRealName);
        sb.append(", receiverId=").append(receiverId);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", status=").append(status);
        sb.append(", createUserName=").append(createUserName);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", modifiedUserName=").append(modifiedUserName);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}