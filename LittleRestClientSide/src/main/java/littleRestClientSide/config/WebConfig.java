package littleRestClientSide.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("littleRestClientSide")
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
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
}
