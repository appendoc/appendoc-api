package wiki.appendoc.application.wiki.service.exception;

public final class WikiDocumentAlreadyExistException extends RuntimeException {

    public WikiDocumentAlreadyExistException(String message) {
        super(message);
    }
}
