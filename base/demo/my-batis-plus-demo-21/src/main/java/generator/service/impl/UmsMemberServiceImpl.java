package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UmsMember;
import generator.service.UmsMemberService;
import generator.mapper.UmsMemberMapper;
import org.springframework.stereotype.Service;

/**
* @author kavanLi-R7000
* @description 针对表【ums_member(会员基础信息表)】的数据库操作Service实现
* @createDate 2024-05-15 10:58:39
*/
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember>
    implements UmsMemberService{

}




