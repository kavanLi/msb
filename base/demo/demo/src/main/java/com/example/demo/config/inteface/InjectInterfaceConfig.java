package com.example.demo.config.inteface;

/**
 * 通过@Bean注解把接口注入容器的方法来实现接口。
 * Created by macro on 2019/11/5.
 */
//@Configuration
public class InjectInterfaceConfig {

    //@Autowired
    //private UmsMemberService memberService;

    // 留用
    //@Bean
    //public UserDetailsService userDetailsService() {
    //    //获取登录用户信息
    //    return usernameWithAppId -> {
    //        MemberDetails memberDetail = memberService.loadUserByUsername(usernameWithAppId);
    //        return memberDetail;
    //    };
    //}

    //@Bean
    //public MemberDetailService memberDetailService() {
    //    return new MemberDetailService() {
    //        @Override
    //        public MemberDetails loadUserByParams(Claims claimsFromToken) throws UsernameNotFoundException {
    //            return memberService.loadUserByParams(claimsFromToken);
    //        }
    //
    //        @Override
    //        public MemberDetails loadUserByUsername(String usernameWithAppId) throws UsernameNotFoundException {
    //            return memberService.loadUserByUsername(usernameWithAppId);
    //        }
    //    };
    //}

}
