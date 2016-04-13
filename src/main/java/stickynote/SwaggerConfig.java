/**
 * 
 */
package stickynote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;

/**
 * @author a598767
 *
 */
@Profile(value = "default")
@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired(required=true)
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns("/notes/*.*");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("REST API Documentation", "REST API", "REST API terms of service", "Contact",
                "REST API Licence Type", "REST API License URL");
        return apiInfo;
    }

}
