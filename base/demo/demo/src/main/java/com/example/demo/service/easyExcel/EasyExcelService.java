package com.example.demo.service.easyExcel;

import java.io.IOException;
import java.util.List;

import com.mashibing.internal.common.domain.request.ItemManagermentQueryReq;
import com.mashibing.internal.common.config.easyExcel.YchItemDownloadData;
import com.mashibing.internal.common.config.easyExcel.read.ReadExcelResult;
import org.springframework.web.multipart.MultipartFile;

/**
* @author kavanLi-R7000
* @description 针对表【smartyunst_item_management】的数据库操作Service
* @createDate 2024-02-22 16:18:54
*/
public interface EasyExcelService {


    List <YchItemDownloadData> itemDataExport(ItemManagermentQueryReq req);

    ReadExcelResult itemDataImport(ItemManagermentQueryReq req, MultipartFile file) throws IOException;
}
