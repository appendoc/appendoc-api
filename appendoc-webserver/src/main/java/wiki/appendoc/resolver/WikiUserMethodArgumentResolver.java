package wiki.appendoc.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;
import wiki.appendoc.api.WikiUserAuthToken;
import wiki.appendoc.domain.authentication.TokenIssuerDomainService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class WikiUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenIssuerDomainService tokenIssuerDomainService;

    public WikiUserMethodArgumentResolver(TokenIssuerDomainService tokenIssuerDomainService) {
        this.tokenIssuerDomainService = tokenIssuerDomainService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(WikiUserAuthToken.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        try {
            final var httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            if (httpServletRequest == null) {
                return null;
            }
            final Cookie accessTokenCookie = WebUtils.getCookie(httpServletRequest, TokenIssuerDomainService.ACCESS_TOKEN_NAME);
            if (accessTokenCookie == null) {
                return null;
            }
            final String value = accessTokenCookie.getValue();
            log.debug("accessTokenCookie: {}", value);
            final String uid = tokenIssuerDomainService.extractUidFromTokenValue(value);
            log.debug("uid: {}", uid);
            final WikiUserAuthToken wikiUserAuthToken = new WikiUserAuthToken(uid);
            log.debug("wikiUserAuthToken: {}", wikiUserAuthToken);
            return wikiUserAuthToken;
        } catch (Throwable e) {
            return null;
        }
    }
}
