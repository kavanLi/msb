package com.mashibing.internal.common.config.easyExcel.read;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: kavanLi-R7000
 * @create: 2024-07-23 22:45
 * To change this template use File | Settings | File and Code Templates.
 */

@Data
@AllArgsConstructor
public class ReadExcelResult {
    private  Integer successCount;
    private  String successDetails;
    private  Integer errorCount;
    private  String errorDetails;
}
