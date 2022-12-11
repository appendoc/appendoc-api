package wiki.appendoc.api.wiki;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wiki.appendoc.application.wiki.service.exception.WikiDocumentNotFoundException;

@Slf4j
@RestControllerAdvice
public class WikiControllerAdvice {

    record WikiDocumentNotFoundResponse(
            String requestDocumentName
    ) {

    }

    @ExceptionHandler(WikiDocumentNotFoundException.class)
    public ResponseEntity<WikiDocumentNotFoundResponse> handleWikiDocumentNotFound(WikiDocumentNotFoundException exception) {
        log.error("WikiControllerAdvice is going to handle WikiDocumentNotFoundException.", exception);
        final var wikiDocumentNotFoundResponse = new WikiDocumentNotFoundResponse(exception.getDocumentName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(wikiDocumentNotFoundResponse);
    }

}
