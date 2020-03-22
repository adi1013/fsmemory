package com.adi.fsmemory.gateway.filter;

import com.adi.fsmemory.jwt.JwtCheckProxy;
import com.adi.fsmemory.jwt.utils.JwtGeneratorUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class TokenCheckFilter extends ZuulFilter {

    private NonCheckConfig nonCheckConfig;

    @Autowired
    private JwtCheckProxy jwtCheckProxy;

    private final static String UIM_TOKEN = "UIM-TOKEN";

    public NonCheckConfig getNonCheckConfig() {
        return nonCheckConfig;
    }

    public void setNonCheckConfig(NonCheckConfig nonCheckConfig) {
        this.nonCheckConfig = nonCheckConfig;
    }

    @Override
    public Object run() throws ZuulException {
        //发现不符合条件的请求时，我们通过context.setSendZuulResponse(false)，过滤该请求。
        HttpServletRequest request = getRequest();
        String token = request.getHeader( UIM_TOKEN );
        Map plainText = null;
        try {
            if (StringUtils.isNotBlank(token)) {
                //解析token
                plainText = JwtGeneratorUtils.parseJWT(token);
                boolean check = jwtCheckProxy.check(plainText);

                //添加验证通过标识，方便后置过滤器刷新token
                if (check) {

                }
                //currentContext
            } else {

            }
        } catch (ExpiredJwtException e) {
            //超时
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 确定需要过滤和非需要过滤的规则
     * @return
     */
    @Override
    public boolean shouldFilter() {
        String requestURI = getRequest().getRequestURI();

        List<String> urisList = Arrays.asList(nonCheckConfig.getSkipCheckUrls());

        //定制一个不需要过滤的集合
        //当该url不属于该集合时，则对其进行过滤
        //其他直接放行
        if (urisList.contains(requestURI)) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    private HttpServletRequest getRequest() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return requestContext.getRequest();
    }
}
