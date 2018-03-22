package littleRestServerSide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import littleRestServerSide.repository.SimpleRepository;
import pl.kamsoft.nfz.model.Model;

@Configuration
public class ControllerConfig {
	
	@Bean
	public View getAllPhones() {
		return new MappingJackson2JsonView();
	}
	
	@Bean
	public SimpleRepository getRepository() {
		return new SimpleRepository();
	}
	
}
