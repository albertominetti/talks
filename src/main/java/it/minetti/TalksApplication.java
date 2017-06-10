package it.minetti;

import it.minetti.controller.model.RestTalk;
import it.minetti.persistence.model.Talk;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.faces.webapp.FacesServlet;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
@ComponentScan(basePackageClasses = TalksApplication.class)
@EntityScan(basePackageClasses = { Talk.class, RestTalk.class })

public class TalksApplication {

  public static void main(String[] args) {
    SpringApplication.run(TalksApplication.class, args);
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any())
      .build()
      .pathMapping("/")
      //.useDefaultResponseMessages(false)
      .directModelSubstitute(LocalDateTime.class, String.class)
      .directModelSubstitute(LocalDate.class, String.class);
  }

  @Bean
  public FacesServlet facesServlet() {
    return new FacesServlet();
  }

  @Bean
  public ServletRegistrationBean facesServletRegistration() {
    ServletRegistrationBean registration = new ServletRegistrationBean(facesServlet(), "*.xhtml");
    registration.setName("FacesServlet");
    return registration;
  }

  @Bean
  public ServletContextInitializer initializer() {
    return servletContext -> {
      servletContext.setInitParameter("primefaces.THEME", "bootstrap");
      servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development"); //TODO just for dev
    };
  }

}
