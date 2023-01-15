package wiki.appendoc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import wiki.appendoc.application.wiki.port.inbound.WriteNewWikiDocumentUseCase;

import java.time.LocalDateTime;

@SpringBootApplication
public class AppendocWikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppendocWikiApplication.class, args);
    }
}

@Slf4j
@Profile({"default", "local", "alpha"})
@Component
class MockDataRunner implements ApplicationRunner {

    private final WriteNewWikiDocumentUseCase useCase;

    public MockDataRunner(WriteNewWikiDocumentUseCase useCase) {
        this.useCase = useCase;
    }

    // FIXME: 환경 적절히 구축된 후에는 반드시 사라져야 한다.
    @Override
    public void run(ApplicationArguments args) {
        log.warn("MockDataRunner가 실행되었습니다. 제거 계획을 수립하고 반드시 없애주세요.");
        try {
            useCase.writeWikiDocument(
                    new WriteNewWikiDocumentUseCase.WriteNewWikiCommand(
                            "메인",
                            """
                                    # 1. 개요
                                                                    
                                    ```java
                                    class Main {
                                        public static void main(String[] args) {
                                            System.out.println("Hello, world!");
                                        }
                                    }
                                    ```
                                                                    
                                    썬 마이크로시스템즈에서 1995년에 개발한 객체 지향 프로그래밍 언어. 창시자는 제임스 고슬링이다. 2010년에 오라클이 썬 마이크로시스템즈를 인수하면서 Java의 저작권을 소유하였다. 현재는
                                    OpenJDK는 GPL2이나 오라클이 배포하는 Oracle JDK는 상업라이선스로 2019년 1월부터 유료화정책을 강화하고 있다. Java EE는 이클립스 재단의 소유이다. Java 언어는
                                    J2SE 1.4부터는 Java Community Process (JCP)에서 개발을 주도하고 있다.
                                    C#과 문법적 성향이 굉장히 비슷하며, 그에 비해 2019년 Q3에서 가장 많이 이용하는 언어로 뽑혔다.
                                                                    
                                    ## 2. 특징
                                                                    
                                    Java의 가장 큰 특징은 플랫폼에 독립적인 언어라는 점이다. 소스 코드를 기계어로 직접 컴파일하여 링크하는 C/C++의 컴파일러와 달리 자바 컴파일러는 바이트코드인 클래스 파일(.class)을
                                    생성하고, 이 파일의 바이트코드를 읽은 뒤 기계어로 바꾸어 실행하는 것은 자바 가상 머신이다.
                                                                    
                                    예를 들어 플랫폼에 종속된 경우 윈도우에서 빌드한 프로그램을 그대로 리눅스나 macOS에서 실행하려 하면 일반적으로 오류가 나지만 Java로 작성 된 프로그램은 플랫폼에 맞는 Java
                                    Runtime Environment만 설치되어 있다면 문제 없이 동작한다. 이는 Java 코드 자체가 플랫폼이 아닌 가상머신에 종속적이라는 점, 그리고 프로그램 실행의 주체가 운영 체제가 아닌
                                    Java Runtime Environment이라는 점 때문이며 이러한 점을 통틀어 Java는 플랫폼 종속성이 낮은 언어라고 표현한다.
                                                                    
                                    Java Runtime Environment 기반의 프로젝트에서는 하나의 언어만을 고집하지 않는다. 함수형 언어가 유리(최근 유행하는 빅데이터 등)한 부분은 Scala로 작업하며, 견고한
                                    인터페이스와 대규모 통합이 필요한 곳은 Java로 작업한 뒤 이들을 서로 합쳐서 운영하는 게 가능하다. (Java에서 만든 객체를 Scala에서 그대로 사용할 수 있다.) 안드로이드 쪽에서는
                                    크리티컬하지 않은 부분부터 Kotlin으로 코드를 교체하는 경우도 많아지고 있다.
                                                                    
                                    실제 현업에서는 Java를 비롯한 여러가지 인기 언어가 자주 사용되므로 장단점을 잘 알아두는게 좋다. 다른 언어에 대해 맹목적으로 찬양/비판하는 태도보다는 환경이나 주어진 작업의 특성에 따라 적합한 언어를 선택할 수 있는 노하우가 필요하다.
                                    ### 3. 테스트 3
                                    #### 4. 테스트 4
                                    ##### 5. 테스트 5
                                    ###### 6. 테스트 6
                                    # 7. 테스트
                                                                    
                                    > 이것은 인용구 \s
                                    > 이것은 인용구 \s""",
                            LocalDateTime.now()
                    )
            );
        } catch (Throwable ignore) {
        }
    }
}
