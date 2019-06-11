package cn.com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import cn.com.util.Page;

public abstract class BaseController
{
    /**
     * 获取请求参数map 封装page对象
     * 
     * @param request
     */
    public Map<String, Object> getRequestParams(HttpServletRequest request)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null)
        {
            for (String key : parameterMap.keySet())
            {
                String[] values = parameterMap.get(key);
                params.put(key, values.length == 1 ? values[0].trim() : values);
            }
        }
        int pageNo = getPageNo(request);
        // 调用方法名称 分页请求地址
        String url = Thread.currentThread().getStackTrace()[2].getMethodName();
        Page page = Page.newBuilder(pageNo,getPageSize(request), url);
        params.put("page", page);
        return params;
    }
    
    /**
     * 获取第几页
     * 
     * @param request
     */
    private final int getPageNo(HttpServletRequest request)
    {
        String pageNoStr = (String) request.getParameter("pageNo");
        int pageNo = StringUtils.isEmpty(pageNoStr) ? 1
                : Integer.valueOf(pageNoStr);
        return pageNo;
    }
    
    /**
     * 获取第几页 
     * @param request
     */
    private final int getPageSize(HttpServletRequest request){
        String pageSizeStr = (String)request.getParameter("pageSize");
        int pageSize = StringUtils.isEmpty(pageSizeStr) ? Integer.valueOf(Page.DEFAULT_PAGESIZE) : Integer.valueOf(pageSizeStr);
        return pageSize;
    }
}