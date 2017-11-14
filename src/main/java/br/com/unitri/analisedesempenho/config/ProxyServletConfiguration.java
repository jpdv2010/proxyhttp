package br.com.unitri.analisedesempenho.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import br.com.unitri.analisedesempenho.proxy.ProxyServlet;

@Configuration
public class ProxyServletConfiguration implements EnvironmentAware {

  @Bean
  public ServletRegistrationBean servletRegistrationBean(){
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), propertyResolver.getProperty("servlet_url"));
    servletRegistrationBean.addInitParameter(ProxyServlet.P_TARGET_URI, propertyResolver.getProperty("target_url"));
    servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, propertyResolver.getProperty("logging_enabled", "false"));
    return servletRegistrationBean;
  }

  private RelaxedPropertyResolver propertyResolver;

  public void setEnvironment(Environment environment) {
	  
    this.propertyResolver = new RelaxedPropertyResolver(environment, "proxy.solr.");
  
  }

}