package me.bird.heroku.webconfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().log("fake contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
