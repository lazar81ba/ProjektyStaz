package pl.soapservice.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

public class WebServiceDispatcherInicjalizer 
implements WebApplicationInitializer
{
    public void onStartup(ServletContext arg0) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfig.class);
        ctx.setServletContext(arg0);
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        Dynamic dynamic = arg0.addServlet("dispather",  servlet);
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/ws/*");
    }

}
