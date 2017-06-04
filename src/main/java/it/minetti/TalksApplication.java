package it.minetti;

import it.minetti.controller.RestTalkController;
import it.minetti.logic.TalksService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ComponentScan(basePackageClasses = TalksService.class)
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {RestTalkController.class})
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

}
