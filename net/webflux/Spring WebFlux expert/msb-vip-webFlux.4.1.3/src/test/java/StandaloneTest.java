import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

public class StandaloneTest {
    @Test
    public void checkApplicationRunning(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(18);
        DefaultPasswordVerificationService service = new DefaultPasswordVerificationService(WebClient.builder());
        StepVerifier.create(service.check("test",encoder.encode("test1")))
                .expectSubscription()
                .expectComplete()
                .verify(Duration.ofSeconds(30));
    }
}
