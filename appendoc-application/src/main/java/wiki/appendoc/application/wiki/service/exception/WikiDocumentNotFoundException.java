package wiki.appendoc.application.wiki.service.exception;

import lombok.Getter;

@Getter
public final class WikiDocumentNotFoundException extends RuntimeException {

    private final String documentName;

    public WikiDocumentNotFoundException(String message, String documentName) {
        super(message);
        this.documentName = documentName;
    }
}
