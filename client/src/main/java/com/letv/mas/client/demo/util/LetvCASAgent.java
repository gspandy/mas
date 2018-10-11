package com.letv.mas.client.demo.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

@WebFilter(urlPatterns = "/*",filterName = "checkLogin")
public class LetvCASAgent implements Filter {

    private static String LETV_SSO_TRANSCODE_API_URL = "http://ucapi.lecommons.com/transcode.php";
    private static String LETV_SSO_PORTAL_APP_SITE = "omp";
    private static String LETV_SSO_PORTAL_APP_KEY = "TkJERERMU1EyMDE4MDkyOTAwMDE";

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String mtk = req.getParameter("m_tk");
//        System.err.println(mtk);
        if(StringUtils.isNotBlank(mtk)){
            Properties prop = new Properties();
            prop.setProperty("SSO_API", LETV_SSO_TRANSCODE_API_URL);
            prop.setProperty("SSO_KEY", LETV_SSO_PORTAL_APP_KEY);
            prop.setProperty("SSO_SITE", LETV_SSO_PORTAL_APP_SITE);
            String userid = decode(mtk, prop);
            if(StringUtils.isNotBlank(userid)){
                request.setAttribute("loginUser",userid);
            }
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie: cookies){
                if("loginUser".equals(cookie.getName())){
                    request.setAttribute("loginUser",cookie.getValue());
                }
            }
        }
        chain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    private static String decode(String mtk, Properties prop) {
        try {
            String param = "site=" + prop.getProperty("SSO_SITE", "portal")
                    + "&time=" + System.currentTimeMillis() / 1000
                    + "&type=DECODE&v=";
            String url = prop.getProperty("SSO_API", "") + "?" + param
                    + URLEncoder.encode(mtk, "utf-8") + "&sign="
                    + getMD5(param + mtk + prop.getProperty("SSO_KEY", ""));
            String json = invokeService(url);
            JSONObject result = new JSONObject(json);
            JSONObject repond = result.getJSONObject("respond");
            String status = repond.getString("status");
            if ("1".equals(status)) {
                String objects = result.getString("objects");
                int begin = objects.indexOf("&u=") + 3;
                int end = objects.indexOf("&time=");
                return objects.substring(begin, end);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getMD5(String key) {
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(key.getBytes("UTF-8"));
            for (byte b : digest.digest()) {
                buffer.append(String.format("%02x", b & 0xff));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private static String invokeService(String spec) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(spec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    in, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            in.close();
            reader.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
