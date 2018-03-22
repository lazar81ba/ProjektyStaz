package serverApplication.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import dao.JsonDAOFactory;
import dao.JsonDAOOperation;
import pl.kamsoft.nfz.project.dao.Database;
import pl.kamsoft.nfz.project.dao.DatabaseImpl;

@Configuration
public class DatabaseConfig {
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		driverManagerDataSource.setUrl("jdbc:oracle:thin:@GLORA2.kamsoft.local:1521/SZKOL");
		driverManagerDataSource.setUsername("Hibernate");
		driverManagerDataSource.setPassword("Hibernate");
		return driverManagerDataSource;
	}
	
	@Bean
	public Database getDatabase() {

		return new DatabaseImpl();
	}
	
	@Bean
	@Qualifier("export")
	public JsonDAOOperation getJSONExport() {
		return JsonDAOFactory.build("export");
	}
	
	@Bean
	@Qualifier("import")
	public JsonDAOOperation getJSONImport() {
		return JsonDAOFactory.build("import");
	}
	

}
