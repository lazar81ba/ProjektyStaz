package littleRestClientSide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import littleRestClientSide.services.RestService;

@Configuration
public class ControllerConfig {

	@Bean
	public View getJackson() {
		return new MappingJackson2JsonView();
	}
	
	@Bean
	public RestService getRestService() {
		return new RestService();
	}
}
