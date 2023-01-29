package wiki.appendoc.domain.authentication.oauth2.google;

public record ProfileFromGoogle(String id, String email, boolean verifiedEmail, String picture) {

}
