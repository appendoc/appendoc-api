package wiki.appendoc.api.users.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
        @JsonSubTypes.Type(value = SignUpRequest.ByOAuth2.class, name = "OAUTH2")
)
public sealed class SignUpRequest {

    @Getter
    @NoArgsConstructor
    @ToString
    public static final class ByOAuth2 extends SignUpRequest {

        private String signUpKey;
        private String displayName;
    }
}
