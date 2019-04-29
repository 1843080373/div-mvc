package com.base.servlet;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * �����������ͨ�õĹ���������
 */
public class EncodingFilter implements Filter {
    // ���ù�����ʹ�õı���Ϊutf-8
    private String charsetName = "UTF-8";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("��ʼ�����������ݵı��롭��");
        // ת��Ϊ��Э����ض���
        HttpServletRequest req = (HttpServletRequest) request;
        // ��ȡ���󷽷�
        String method = req.getMethod();
        if ("post".equalsIgnoreCase(method)) {
            // ���post
            req.setCharacterEncoding(charsetName);
        } else {
            // ���get����
            req = new EncodingRequest(req, charsetName);
        }
        // �����Ӧ����
        response.setContentType("text/html; charset=" + charsetName);// ������Ӧ���ݺ���Ӧ��ҳ������ʽ
        chain.doFilter(req, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}

class EncodingRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    private String charsetName;
    private Map<String, String[]> map;
    private Enumeration<String> names;

    public EncodingRequest(HttpServletRequest request, String charsetName) {
        super(request);
        this.request = request;
        this.charsetName = charsetName;
        map = getParameterMap();
        names = Collections.enumeration(map.keySet());
    }
    @Override
    //����get�����в����ļ�����������
    public Enumeration<String> getParameterNames() {
        return names;
    }
    @Override
    public String getParameter(String name) {
        // ͨ��getParameterMap�������
        String[] values = getParameterValues(name);
        if (values == null) {
            return null;
        }
        return values[0];
    }
    @Override
    public String[] getParameterValues(String name) {
        String[] values = map.get(name);
        return values;
    }
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String[]> map = new HashMap<>(parameterMap.size());
        for (Map.Entry<String, String[]> entries : parameterMap.entrySet()) {
            String key = entries.getKey();//��ȡ������key
            String[] values = entries.getValue();
            try {
                //����get�����в����ļ�����������
                key = new String(key.getBytes("ISO-8859-1"), charsetName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //����get�����в�����ֵ����������
            if(values != null && values.length > 0){
                for (int i = 0; i < values.length; i++) {
                    try {
                        // values��һ����ַ
                        values[i] = new String(values[i].getBytes("ISO-8859-1"), charsetName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            map.put(key, values);
        }
        return map;
    }
}