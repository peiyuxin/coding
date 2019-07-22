package org.pyx.fescar;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/*
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fescar.core.context.RootContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
*/
/**
 * @author pyx
 * @date 2019/3/4
 */
public class FescarXidFilter{// extends OncePerRequestFilter {
    //财务预测，经营预测
    protected Logger logger = LoggerFactory.getLogger(FescarXidFilter.class);

    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
        ServletException,
        IOException {
        String xid = RootContext.getXID();
        String restXid = request.getHeader("Fescar-Xid");
        boolean bind = false;
        if(StringUtils.isBlank(xid)&&StringUtils.isNotBlank(restXid)){
            RootContext.bind(restXid);
            bind = true;
            if (logger.isDebugEnabled()) {
                logger.debug("bind[" + restXid + "] to RootContext");
            }
        }
        try{
            filterChain.doFilter(request, response);
        } finally {
            if (bind) {
                String unbindXid = RootContext.unbind();
                if (logger.isDebugEnabled()) {
                    logger.debug("unbind[" + unbindXid + "] from RootContext");
                }
                if (!restXid.equalsIgnoreCase(unbindXid)) {
                    logger.warn("xid in change during http rest from " + restXid + " to " + unbindXid);
                    if (unbindXid != null) {
                        RootContext.bind(unbindXid);
                        logger.warn("bind [" + unbindXid + "] back to RootContext");
                    }
                }
            }
        }
    }*/
}
