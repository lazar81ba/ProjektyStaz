package littleRestServerSide.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("littleRestServerSide")
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
	}

	@Bean
	public ViewResolver contenetNegotiationViewResolvler(ContentNegotiationManager cnm) {
		ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
		cnvr.setContentNegotiationManager(cnm);
		return cnvr;
	}

	@Bean
	public ViewResolver beanNameViewResolver() {
		return new BeanNameViewResolver();
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	

}
