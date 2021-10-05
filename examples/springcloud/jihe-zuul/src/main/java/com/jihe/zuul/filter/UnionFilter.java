package com.jihe.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description: 统一过滤器
 * @author: ck
 * @time: 2019-12-15 22:16
 **/
@Component
public class UnionFilter extends ZuulFilter {


    /**
     * 包含类型：
     * pre:路由代理之前执行
     * route：代理时执行
     * error：代理出错执行
     * post：route || error 之后执行
     */
    @Override
    public String filterType() {
        // 之前执行
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 存在多个过滤器时执行顺序
     * 数字越小，优先级越高
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行过滤器：true 需要
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体执行逻辑
     *
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("enter UnionFilter,auth begin");
        RequestContext context = RequestContext.getCurrentContext();
        // 获取到 request 对象，进行鉴权等操作
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            try {
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(400);
                context.getResponse().getWriter().println("auth failed,please reload");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
