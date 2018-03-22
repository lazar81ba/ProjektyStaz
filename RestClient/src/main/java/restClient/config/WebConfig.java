package restClient.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan("restClient")
public class WebConfig  extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(100000);
	    return new CommonsMultipartResolver();
	}
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
	
	@Bean
	public InternalResourceViewResolver jspResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("WEB-INF/");
		resolver.setSuffix(".jsp");
		resolver.setViewNames("jsp/*");
		return resolver;
	}


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    

    @Bean
    public ViewResolver thResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setViewNames(new String[] {"th/*"});
        return resolver;
    }

    private TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("WEB-INF/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

}
