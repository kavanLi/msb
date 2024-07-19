package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.SmartyunstBillOrderCheck;
import generator.service.SmartyunstBillOrderCheckService;
import generator.mapper.SmartyunstBillOrderCheckMapper;
import org.springframework.stereotype.Service;

/**
* @author kavanLi-R7000
* @description 针对表【smartyunst_bill_order_check(支付流对账结果 0-有差异1-无差异 表)】的数据库操作Service实现
* @createDate 2024-06-17 17:25:46
*/
@Service
public class SmartyunstBillOrderCheckServiceImpl extends ServiceImpl<SmartyunstBillOrderCheckMapper, SmartyunstBillOrderCheck>
    implements SmartyunstBillOrderCheckService{

}




