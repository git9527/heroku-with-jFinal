package me.bird.heroku.webconfig;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().log("origin default timezone:" + TimeZone.getDefault().getDisplayName());
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		sce.getServletContext().log("current default timezone:" + TimeZone.getDefault().getDisplayName());
		sce.getServletContext().log("fake contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
