package com.mudigal.one.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

/**
 * Swagger configuration
 *
 * @author vijmudig
 *
 */
@Configuration //@Configuration: Đánh dấu lớp này là một lớp cấu hình Spring
@EnableSwagger2WebFlux
@Profile("default")
class SwaggerConfig {
  // Docket là cấu hình chính của Swagger trong Spring. 
  //Cấu hình này khai báo các thông tin và lựa chọn cho việc tạo tài liệu API.
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.mudigal.one"))
        .paths(PathSelectors.any()).build().apiInfo(apiInfo())
        .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfo() {
    //Phương thức này tạo thông tin chi tiết về API mà Swagger sẽ hiển thị.
    return new ApiInfoBuilder().title("Microservices Sample - Service One").description(
        "API documentation for service one reactive service with mongo database")
                .termsOfServiceUrl("#")
        .contact(new Contact("Mudigal Technologies LLP", "https://www.mudigal.com", "contact@mudigal.com.com"))
        .license("Apache License 2.0")
        .licenseUrl("#").version("5.0.0")
        .build();
  }
}
// Cung cấp tài liệu API: Swagger sẽ tự động tạo tài liệu cho các API trong ứng dụng của bạn. Điều này giúp các nhà phát triển khác có thể dễ dàng hiểu và sử dụng các API của bạn.

// Kích hoạt Swagger cho WebFlux: Với @EnableSwagger2WebFlux, Swagger được tích hợp vào môi trường Spring WebFlux (để hỗ trợ ứng dụng phi đồng bộ).

// Cấu hình chi tiết API: Bạn có thể cung cấp thông tin chi tiết về API như tiêu đề, mô tả, phiên bản, và giấy phép để người dùng có thể tham khảo.