package hr.pgalina.chain_reaction_web_socket.domain.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestValidator {

    public static final String PRIVILEGED_TOKEN = "Basic Gts4RPPZfx35g1/jVeQlYp6HJAE=";

    public boolean validatePrivilegedToken(String token) {
        return PRIVILEGED_TOKEN.equals(token);
    }
}
