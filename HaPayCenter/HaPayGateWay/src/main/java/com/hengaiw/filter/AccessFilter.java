package com.hengaiw.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;


public class AccessFilter extends ZuulFilter  {

    //private static final MyLog _log = MyLog.getLog(ZuulFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //_log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        //此处定义网关的安全机制，进行安全IP限制等。
        Object accessToken = request.getParameter("accessToken");
        System.out.println("gateway is url");
        /*if(accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }*/
        return null;
    }

}
