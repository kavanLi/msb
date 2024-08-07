package com.mashibing.internal.common.config.easyExcel.setting;

/**
 * @author: kavanLi-R7000
 * @create: 2024-07-05 09:58
 * To change this template use File | Settings | File and Code Templates.
 */

import java.util.List;

import lombok.Data;

/**
 * 用于封装表格类和数据列表的类
 */
@Data
public class SheetTableData {
    private Class<?> clazz;
    private List <?> dataList;

    public SheetTableData(Class<?> clazz, List<?> dataList) {
        this.clazz = clazz;
        this.dataList = dataList;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
}
