import com.msb.bean.PasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DefaultPasswordVerificationService  implements PasswordVerificationService{
     WebClient webClient;
    public DefaultPasswordVerificationService(WebClient.Builder webBulder) {
        this.webClient = webBulder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public Mono<Void> check(String raw, String encode) {
      //  webClient.post()
        return WebClient.create("http://localhost:8080").post()
                .uri("/password")
                .body(BodyInserters.fromPublisher(
                        Mono.just(new PasswordDTO(raw,encode)),
                        PasswordDTO.class))
                //指定结果的处理方式
                .retrieve()
                .toEntityFlux(ResponseEntity.class)
                .flatMap(response ->{
                    if(response.getStatusCode().is2xxSuccessful()){
                        return Mono.empty();
                    }else if(response.getStatusCode() == HttpStatus.EXPECTATION_FAILED){
                        return Mono.error(new BadCredentialsException("Invalid credentials"));
                    }
                    return Mono.error(new IllegalStateException("非法参数"));
                });


    }
}


