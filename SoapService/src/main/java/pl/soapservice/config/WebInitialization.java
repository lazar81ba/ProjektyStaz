package pl.soapservice.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import pl.soapservice.config.WebConfig;

public class WebInitialization extends AbstractAnnotationConfigDispatcherServletInitializer {

	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
