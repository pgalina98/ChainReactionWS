package hr.pgalina.chain_reaction_web_socket.config;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Slf4j
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    public static final String USER_ID_PARAM = "userId";

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter(USER_ID_PARAM);

        log.info("User with id {} successfully logged in [TCP HANDSHAKE].", userId);

        return new UserPrincipal(userId);
    }
}