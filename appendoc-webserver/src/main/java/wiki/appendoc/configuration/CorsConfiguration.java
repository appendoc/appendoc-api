package wiki.appendoc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CorsConfiguration {

    @Profile({"default", "local"})
    @Configuration
    static class LocalAndDefaultCorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            log.debug("로컬환경에서 CORS 설정됨");
            registry.addMapping("/**")
                    .allowedOrigins("http://127.0.0.1:3000")
                    .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                    .exposedHeaders(HttpHeaders.SET_COOKIE)
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }

    @Profile({"alpha"})
    @Configuration
    static class AlphaCorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            log.debug("알파 환경에서 CORS 설정됨");
            registry.addMapping("/**")
                    .allowedOrigins("https://dev.appendoc.wiki")
                    .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                    .exposedHeaders(HttpHeaders.SET_COOKIE)
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }
}
