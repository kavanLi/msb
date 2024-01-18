import reactor.core.publisher.Mono;

public interface PasswordVerificationService {
    Mono<Void> check(String raw,String encode);
}
