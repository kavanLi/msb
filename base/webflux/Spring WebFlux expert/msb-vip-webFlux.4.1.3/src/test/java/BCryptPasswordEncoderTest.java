import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));
        System.out.println(bCryptPasswordEncoder.matches("123","$2a$10$W3RM8MH2n.rqMb61nGadaOg/I1/vVI1Y8G0NZEmsJJgCAP.JnzkIu"));
    }
}
