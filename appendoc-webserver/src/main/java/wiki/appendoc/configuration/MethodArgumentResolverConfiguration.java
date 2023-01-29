package wiki.appendoc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wiki.appendoc.resolver.WikiUserMethodArgumentResolver;

import java.util.List;

@Configuration
public class MethodArgumentResolverConfiguration implements WebMvcConfigurer {

    private final WikiUserMethodArgumentResolver wikiUserMethodArgumentResolver;

    public MethodArgumentResolverConfiguration(WikiUserMethodArgumentResolver wikiUserMethodArgumentResolver) {
        this.wikiUserMethodArgumentResolver = wikiUserMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(wikiUserMethodArgumentResolver);
    }
}
