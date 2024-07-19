package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.SmartyunstBillPaymentCheck;
import generator.service.SmartyunstBillPaymentCheckService;
import generator.mapper.SmartyunstBillPaymentCheckMapper;
import org.springframework.stereotype.Service;

/**
* @author kavanLi-R7000
* @description 针对表【smartyunst_bill_payment_check(业务流对账结果 0-有差异1-无差异 表)】的数据库操作Service实现
* @createDate 2024-06-24 10:45:39
*/
@Service
public class SmartyunstBillPaymentCheckServiceImpl extends ServiceImpl<SmartyunstBillPaymentCheckMapper, SmartyunstBillPaymentCheck>
    implements SmartyunstBillPaymentCheckService{

}




