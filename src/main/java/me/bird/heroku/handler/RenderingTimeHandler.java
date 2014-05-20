package me.bird.heroku.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.handler.Handler;

/**
 * <a href = http://www.oschina.net/question/173052_62229 />
 * 
 * @author kid
 * 
 */
public class RenderingTimeHandler extends Handler {

   private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        long start = System.currentTimeMillis();
        String userAgent = request.getHeader("User-Agent");
        nextHandler.handle(target, request, response, isHandled);
        long end = System.currentTimeMillis();
        logger.info("User-Agent:["+ userAgent + "]\tURL:["+ target + "]\tTRENDING TIME:\t[" + (end - start) + "]ms");
    }

}
