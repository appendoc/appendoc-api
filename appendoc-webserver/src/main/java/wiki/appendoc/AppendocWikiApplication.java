package wiki.appendoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wiki.appendoc.domain.users.WikiUser;

@SpringBootApplication
public class AppendocWikiApplication {

    public static void main(String[] args) {
        final WikiUser wikiUser = new WikiUser();
        SpringApplication.run(AppendocWikiApplication.class, args);
    }
}
