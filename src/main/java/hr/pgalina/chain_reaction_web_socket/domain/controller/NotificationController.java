package hr.pgalina.chain_reaction_web_socket.domain.controller;

import hr.pgalina.chain_reaction_web_socket.domain.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ws/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final RequestValidator requestValidator;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/update")
    public ResponseEntity<?> updateNotificationsCount(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long idUser
    ) {
        log.info("Entered '/ws/notifications/update' with token {} and user ID {} [GET].", token, idUser);

        if (!requestValidator.validatePrivilegedToken(token)) {
            log.error("Token {} is not valid.", token);

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        simpMessagingTemplate.convertAndSendToUser(String.valueOf(idUser), "/queue/topic/private-notifications", Boolean.TRUE);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
