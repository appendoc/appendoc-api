package wiki.appendoc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wiki.appendoc.domain.authentication.TokenIssuer;
import wiki.appendoc.domain.authentication.TokenIssuerDomainService;

@Configuration
public class TokenIssuerDomainServiceConfiguration {

    private final JwtProperties jwtProperties;

    public TokenIssuerDomainServiceConfiguration(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public TokenIssuerDomainService getTokenIssuerDomainService() {
        return new TokenIssuer(
                jwtProperties.getSecret(),
                jwtProperties.getIssuer(),
                jwtProperties.getDomain(),
                jwtProperties.getPath()
        );
    }
}
