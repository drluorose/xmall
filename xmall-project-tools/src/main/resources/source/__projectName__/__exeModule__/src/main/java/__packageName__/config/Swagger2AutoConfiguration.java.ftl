package @packageName@.config;

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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//TODO 请讲以下内容改成你自己的配置
@Profile(value = {"!pro", "!high", "!trunk"})
@Configuration
@EnableSwagger2
public class Swagger2AutoConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("@packageName@.facade.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("孔令圳", "mailto:konglingzhen@douyu.tv", "konglingzhen@douyu.tv");
        return new ApiInfoBuilder()
                .title("某某服务")
                .description(
                        "更多某某服务相关信息，请移步：你的设计文档地址")
                .termsOfServiceUrl("mailto:wsd_group@douyu.tv")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}

