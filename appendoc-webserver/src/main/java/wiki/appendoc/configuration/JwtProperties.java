package wiki.appendoc.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtProperties {

    private String issuer;
    private String secret;
    private String domain;
    private String path;
}
