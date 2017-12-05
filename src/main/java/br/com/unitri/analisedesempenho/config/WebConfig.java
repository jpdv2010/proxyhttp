package br.com.unitri.analisedesempenho.config;

import org.apache.catalina.valves.AccessLogValve;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer {

    public void customize(ConfigurableEmbeddedServletContainer container) {

        if (container instanceof TomcatEmbeddedServletContainerFactory) {
            TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
            AccessLogValve accessLogValve = new AccessLogValve();
            accessLogValve.setDirectory("/tmp/tomcatlogs");

            /**
             * Formato:

             %a - Remote IP address
             %A - Local IP address
             %b - Bytes sent, excluding HTTP headers, or '-' if no bytes were sent
             %B - Bytes sent, excluding HTTP headers
             %h - Remote host name (or IP address if enableLookups for the connector is false)
             %H - Request protocol
             %l - Remote logical username from identd (always returns '-')
             %m - Request method
             %p - Local port
             %q - Query string (prepended with a '?' if it exists, otherwise an empty string
             %r - First line of the request
             %s - HTTP status code of the response
             %S - User session ID
             %t - Date and time, in Common Log Format format
             %u - Remote user that was authenticated
             %U - Requested URL path
             %v - Local server name
             %D - Time taken to process the request, in millis
             %T - Time taken to process the request, in seconds
             %I - current Request thread name (can compare later with stacktraces)
             In addition, the caller can specify one of the following aliases for commonly utilized patterns:

             common - %h %l %u %t "%r" %s %b
             combined - %h %l %u %t "%r" %s %b "%{Referer}i" "%{User-Agent}i"

             */


            accessLogValve.setPattern("%h %l %u %{msec}t \"%r\" %s %b \"%{Referer}i\" \"%{User-Agent}i\"");


            accessLogValve.setSuffix(".log");
            accessLogValve.setBuffered(false);
            factory.addContextValves(accessLogValve);
        }

    }

}