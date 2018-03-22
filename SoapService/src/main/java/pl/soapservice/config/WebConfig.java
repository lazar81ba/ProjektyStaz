package pl.soapservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
@ComponentScan("pl.soapservice")
public class WebConfig extends WsConfigurerAdapter {

	
    @Bean(name="myschema")
    public DefaultWsdl11Definition country(XsdSchema countriesSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
     
        definition.setPortTypeName("PhonesPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("/SoapService/");
        definition.setSchema(countriesSchema);
        return definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("myschema.xsd"));
    }
 
}
