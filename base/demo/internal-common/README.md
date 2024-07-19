1。Yst2ApiRequestXXXX 实体类修改
   /*
   接口代码
   */
   private String transCode = Yst2ApiConstants.ApiCodeEnum.DOWNLOAD_UPLOAD_FILE.getCode();

2.Controller的接口url/方法名字/方法参数/备注修改，参考Yst2ApiConstants.class/Yst2ApiRequestXXXX.class
Serveice方法名字/方法参数改成Yst2ApiRequestXXXX，参考Yst2ApiConstants.class/Yst2ApiRequestXXXX.class
Interface方法名字/方法参数改成Yst2ApiRequestXXXX，参考Yst2ApiConstants.class/Yst2ApiRequestXXXX.class

3.集成测试比如 MemberAccountControllerTest.class增加测试方法 -> 可选