package wiki.appendoc.api.wiki;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiki.appendoc.adapter.wiki.inbound.WikiApi;
import wiki.appendoc.application.wiki.port.inbound.ReadWikiDocumentByDocumentNameUseCase;
import wiki.appendoc.application.wiki.port.inbound.WriteNewWikiDocumentUseCase;
import wiki.appendoc.application.wiki.service.exception.WikiDocumentNotFoundException;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/wiki")
public class WikiController implements WikiApi {

    private final WriteNewWikiDocumentUseCase writeNewWikiDocumentUseCase;
    private final ReadWikiDocumentByDocumentNameUseCase readWikiDocumentByDocumentNameUseCase;

    public WikiController(
            WriteNewWikiDocumentUseCase writeNewWikiDocumentUseCase,
            ReadWikiDocumentByDocumentNameUseCase readWikiDocumentByDocumentNameUseCase
    ) {
        this.writeNewWikiDocumentUseCase = writeNewWikiDocumentUseCase;
        this.readWikiDocumentByDocumentNameUseCase = readWikiDocumentByDocumentNameUseCase;
    }

    @PostMapping
    @Override
    public CreateNewWikiDocumentResponse postNewWikiDocument(@RequestBody CreateNewWikiDocumentRequest request) {
        log.debug("request: {}", request);
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

    @GetMapping("/{documentName}")
    @Override
    public FindWikiDocumentResponse getWikiDocument(@PathVariable String documentName) {
        try {
            final var result = readWikiDocumentByDocumentNameUseCase.readWikiDocument(
                    new ReadWikiDocumentByDocumentNameUseCase.ReadWikiDocumentByDocumentNameQuery(
                            documentName
                    )
            );
            return new FindWikiDocumentResponse(
                    result.documentName(),
                    result.content(),
                    result.writtenAt()
            );
        } catch (WikiDocumentNotFoundException exception) {
            log.error("문서를 찾을 수 없습니다.", exception);
            throw exception;
        }
    }
}
