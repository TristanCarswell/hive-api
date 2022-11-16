package carswell.tristan.hive.hiveapi.auth.config;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    public static final String AUTH_TAG = "Authentication";
    public static final String USER_TAG = "Users";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/auth-api/api/**"))
                .build()
                .apiInfo(apiInfo())
                .tags(
                        new Tag(AUTH_TAG, "Authentication and registration of Hive accounts."),
                        new Tag(USER_TAG, "Management of all registered Hive accounts.")
                );
    }

    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
            final WebEndpointsSupplier webEndpointsSupplier,
            final ServletEndpointsSupplier servletEndpointsSupplier,
            final ControllerEndpointsSupplier controllerEndpointsSupplier,
            final EndpointMediaTypes endpointMediaTypes,
            final CorsEndpointProperties corsProperties,
            final WebEndpointProperties webEndpointProperties,
            final Environment environment
    ) {
        final List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        final Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());

        final String basePath = webEndpointProperties.getBasePath();
        final EndpointMapping endpointMapping = new EndpointMapping(basePath);
        final boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(
                webEndpointProperties,
                environment,
                basePath
        );

        return new WebMvcEndpointHandlerMapping(
                endpointMapping,
                webEndpoints,
                endpointMediaTypes,
                corsProperties.toCorsConfiguration(),
                new EndpointLinksResolver(allEndpoints, basePath),
                shouldRegisterLinksMapping,
                null
        );
    }

    private ApiInfo apiInfo() {
        final var contact = new Contact(
                "Tristan Carswell",
                "https://www.neslotech.co.za",
                "tcarswell@neslotech.co.za"
        );
        return new ApiInfoBuilder()
                .title("Hive Authentication API")
                .description("Hive Authentication Spring Boot Microservice.")
                .version("v0.1.0")
                .contact(contact)
                .extensions(Collections.emptyList())
                .build();
    }

    private boolean shouldRegisterLinksMapping(final WebEndpointProperties webEndpointProperties,
                                               final Environment environment,
                                               final String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled()
                && (StringUtils.hasText(basePath)
                || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}
