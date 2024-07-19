package com.mashibing.internal.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ControllerUtils {

    //private final static String COMPANY_COOKIE_NAME = "company_name";
    //
    //@Value("${manager.sample.rawfile.mode}")
    //public static String rawfileStoreMode;
    //
    //public static MedicalUser generateOperator(HttpServletRequest request) {
    //    AuthInfo session = getAuthUser().getAuthInfo();
    //    MedicalUser admin = new MedicalUser();
    //    admin.setId(session.getUserId());
    //    admin.setCompanyId(session.getCompanyId());
    //    admin.setOrganizationId(session.getOrganizationId());
    //    admin.setMobile(session.getMobile());
    //    admin.setRealname(session.getRealname());
    //    admin.setRoleIdList(session.getPrivilegeList());
    //    return admin;
    //}
    //
    //public static MedicalUser generateOperator() {
    //    AuthInfo session = getAuthUser().getAuthInfo();
    //    MedicalUser admin = new MedicalUser();
    //    admin.setId(session.getUserId());
    //    admin.setCompanyId(session.getCompanyId());
    //    admin.setOrganizationId(session.getOrganizationId());
    //    admin.setMobile(session.getMobile());
    //    admin.setRealname(session.getRealname());
    //    admin.setRoleIdList(session.getPrivilegeList());
    //    return admin;
    //}
    //
    ///**
    // * @param response
    // * @param request
    // * @param file
    // * @param ischaracter 是否设置编码集，true:设置编码集GBK,false:不设置
    // * @param token       前端下载插件传递过来参数,需要保存在cookies中
    // * @param setHeader   true:设置Header浏览器,浏览器会直接下载到本地, false:在浏览器中打开文件
    // */
    //public static void fileResponseUtils(HttpServletResponse response, HttpServletRequest request, File file, boolean ischaracter, String token, boolean setHeader) {
    //    ServletContext context = request.getSession().getServletContext();
    //    response.setContentType(context.getMimeType(file.getName()));
    //    if (setHeader) {
    //        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());// 设置文件名
    //    }
    //    if (StringUtils.isNotBlank(token)) {
    //        Cookie cookie = new Cookie("downloadToken", token);
    //        cookie.setPath("/");
    //        response.addCookie(cookie);
    //    }
    //    BufferedInputStream in = null;
    //    OutputStream out = null;
    //    BufferedReader reader = null;
    //    try {
    //        out = response.getOutputStream();
    //        if (ischaracter) {
    //            reader = new BufferedReader(new FileReader(file));
    //            IOUtils.copy(reader, out, "gbk");
    //        } else {
    //            in = new BufferedInputStream(new FileInputStream(file));
    //            IOUtils.copy(in, out);
    //        }
    //
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //        log.error(e.getMessage());
    //    } finally {
    //        IOUtils.closeQuietly(out);
    //        IOUtils.closeQuietly(reader);
    //        IOUtils.closeQuietly(in);
    //    }
    //}
    //
    ///**
    // * @param response
    // * @param status
    // * @param msg
    // * @param succ
    // * @throws IOException
    // */
    //public static void responseFormat(HttpServletResponse response, int status, String msg, boolean succ) throws IOException {
    //    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    //    response.setCharacterEncoding("UTF-8");
    //    PrintWriter writer = response.getWriter();
    //    GeneralResult <?> result = new GeneralResult <>();
    //    if (succ) {
    //        response.setStatus(HttpStatus.OK.value());
    //        result.setResultCode(ResultCode.OPERATION_SUCCESS);
    //    } else {
    //        if (status != 0) {
    //            response.setStatus(status);
    //        }
    //        result.setResultCode(ResultCode.ERR_SERVER);
    //        result.setDetailDescription(msg);
    //    }
    //    writer.write(JSON.toJSONString(result));
    //    writer.flush();
    //    writer.close();
    //}
    //
    //
    //public static AuthUser getAuthUser() {
    //    AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //    return authUser;
    //}
    //
    //public static String getCompanyName(HttpServletRequest httpRequest) {
    //    Cookie[] cookies = httpRequest.getCookies();
    //    if (cookies != null) {
    //        for (Cookie cookie : cookies) {
    //            if (COMPANY_COOKIE_NAME.equals(cookie.getName())) {
    //                return cookie.getValue();
    //            }
    //        }
    //    }
    //    String company = httpRequest.getParameter(COMPANY_COOKIE_NAME);
    //    if (StringUtils.isNotBlank(company)) {
    //        return company;
    //    }
    //
    //    HttpSession httpSession = httpRequest.getSession();
    //    if (httpSession != null && httpSession.getAttribute(COMPANY_COOKIE_NAME) != null) {
    //        company = (String) httpSession.getAttribute(COMPANY_COOKIE_NAME);
    //        return company;
    //    }
    //    return null;
    //}
    //
    //public static String getJSESSIONID(HttpServletRequest httpRequest) {
    //    Cookie[] cookies = httpRequest.getCookies();
    //    if (cookies != null) {
    //        for (Cookie cookie : cookies) {
    //            if ("JSESSIONID".equals(cookie.getName())) {
    //                return cookie.getValue();
    //            }
    //        }
    //    }
    //    return null;
    //}
    //
    ///**
    // * * 以下是发送ajax,获取页面的请求，当用户登录超时，要重定向到首页或者登录页面
    // */
    //private static String[] reloadHtmlUrl = new String[]{"/home"};
    //
    //public static String getRedirectUrl(HttpServletRequest httpRequest, String serviceIP) {
    //    String redirectUrl = serviceIP + "/login";
    //    StringBuffer requestURL = httpRequest.getRequestURL();
    //    boolean falg = false;
    //    for (String url : reloadHtmlUrl) {
    //        if (requestURL.toString().contains(url)) {
    //            falg = true;
    //        }
    //    }
    //    if (falg) {
    //        redirectUrl = serviceIP + "/login";
    //    } else {
    //        String companyName = getCompanyName(httpRequest);
    //        if (StringUtils.isNotBlank(companyName)) {
    //            redirectUrl = serviceIP + "/login";
    //        }
    //    }
    //
    //    // 判断程序是否运行在dubug模式
    //    boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
    //            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    //
    //    if (!StringUtils.equalsIgnoreCase("bmps", rawfileStoreMode) && isDebug) {
    //        //redirectUrl = "http://192.168.2.179:9024/login";
    //        //redirectUrl = "http://192.168.1.128:9024/login";
    //    }
    //    return redirectUrl;
    //}
}
