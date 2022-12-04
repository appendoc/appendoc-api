package wiki.appendoc.api.wiki;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiki.appendoc.adapter.wiki.inbound.WikiApi;
import wiki.appendoc.application.wiki.port.inbound.WriteNewWikiDocumentUseCase;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/wiki")
public class WikiController implements WikiApi {

    private final WriteNewWikiDocumentUseCase writeNewWikiDocumentUseCase;

    public WikiController(WriteNewWikiDocumentUseCase writeNewWikiDocumentUseCase) {
        this.writeNewWikiDocumentUseCase = writeNewWikiDocumentUseCase;
    }

    @PostMapping
    @Override
    public CreateNewWikiDocumentResponse postNewWikiDocument(@RequestBody CreateNewWikiDocumentRequest request) {
        final var command = new WriteNewWikiDocumentUseCase.WriteNewWikiCommand(
                request.documentName(),
                request.content(),
                LocalDateTime.now()
        );
        final var result = writeNewWikiDocumentUseCase.writeWikiDocument(command);
        return new CreateNewWikiDocumentResponse(
                result.newDocumentId()
        );
    }
}
