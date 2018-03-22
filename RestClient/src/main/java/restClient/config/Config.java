package restClient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import restClient.dao.DatabaseOperation;
import restClient.dao.GetRestUrlDetailsFromDB;
import restClient.exceptions.DatabaseOperationException;
import restClient.model.HttpHeadersWrapper;
import restClient.model.URLRepresentation;
import restClient.services.PhoneService;
import restClient.services.SoapService;
import restClient.soapclasses.PhonesPort;
import restClient.soapclasses.PhonesPortService;

@Configuration
public class Config {
	@Bean
	public PhoneService getJSPService() {
		return new PhoneService(getRestURL(),getHttpHeadersWrapper());
	}
	
	@Bean
	public HttpHeadersWrapper getHttpHeadersWrapper() {
		return new HttpHeadersWrapper(getRestURL());
	}
	
	@Bean
	public URLRepresentation getRestURL() {
		URLRepresentation url =  new URLRepresentation();
		try {
			url = (URLRepresentation) getRestUrlDetailsFromDB().execute();
			url.createURI();
		} catch (DatabaseOperationException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	@Bean(name="GetRestUrlDetailsFromDB")
	public DatabaseOperation getRestUrlDetailsFromDB() {
		return new GetRestUrlDetailsFromDB();
	}
	
	@Bean
	public PhonesPort getPortPhone() {
		PhonesPortService service = new PhonesPortService();
		return service.getPhonesPortSoap11();
	}
	
	@Bean
	public SoapService getSoapService() {
		return new SoapService(getPortPhone());
	}
}
