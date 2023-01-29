package wiki.appendoc.api.users.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = SignUpResponse.SignUpAccepted.class,
                name = "SIGN_UP_ACCEPTED"
        ),
        @JsonSubTypes.Type(
                value = SignUpResponse.SignUpDeclined.class,
                name = "SIGN_UP_DECLINED"
        ),
})
public sealed class SignUpResponse {

    enum Type {
        SIGN_UP_ACCEPTED,
        SIGN_UP_DECLINED
    }

    private final Type type;

    public SignUpResponse(Type type) {
        this.type = type;
    }

    public static final class SignUpAccepted extends SignUpResponse {
        public SignUpAccepted() {
            super(Type.SIGN_UP_ACCEPTED);
        }
    }

    public static final class SignUpDeclined extends SignUpResponse {
        public SignUpDeclined() {
            super(Type.SIGN_UP_DECLINED);
        }
    }
}
