package me.bird.heroku.webconfig;

import java.util.Set;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.handler.RenderingTimeHandler;
import me.bird.heroku.handler.ResourceHander;
import me.bird.heroku.utils.ClassLoaderUtil;
import me.bird.heroku.utils.StringUtils;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.ext.handler.ContextPathHandler;

public class DefaultConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
//    	if (SystemUtils.isLocalDev()){
//    		me.setDevMode(true);
//    	}
        me.setBaseViewPath("templates");
    }

	@Override @SuppressWarnings("unchecked")
    public void configRoute(Routes me) {
    	Set<Class<?>> sets = ClassLoaderUtil.getClasses(BaseConsts.CONTROLLER_BASE_PACKAGE);
    	for (Class<?> controllerClass : sets) {
    		String controllerKey = StringUtils.subStringBefore(controllerClass.getSimpleName(), BaseConsts.CONTROLLER_SUFFIX).toLowerCase();
			me.add(controllerKey, (Class<? extends Controller>) controllerClass);
		}
    }
    
    @Override
    public void configPlugin(Plugins me) {
    }

    @Override
    public void configInterceptor(Interceptors me) {
    
    }

    @Override
    public void configHandler(Handlers me) {
    	me.add(new ContextPathHandler("contextPath"));
    	me.add(new RenderingTimeHandler());
    	me.add(new ResourceHander());
    }
}

