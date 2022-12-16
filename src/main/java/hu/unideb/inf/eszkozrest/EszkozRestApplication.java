package hu.unideb.inf.eszkozrest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;

@SpringBootApplication
public class EszkozRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EszkozRestApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FacesServlet facesServlet() {
        return new FacesServlet();
    }

    @Bean
    public ServletRegistrationBean<Servlet> facesServletRegistration() {
        ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<Servlet>(facesServlet(), "*.jsf");
        return registration;
    }

    @Bean
    public com.sun.faces.config.ConfigureListener jsfConfigureListener() {
        return new com.sun.faces.config.ConfigureListener();
    }

}