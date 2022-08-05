package hr.pgalina.chain_reaction_web_socket.domain.controller;

import hr.pgalina.chain_reaction_web_socket.domain.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController("/ws/notifications")
public class NotificationController {

    private final RequestValidator requestValidator;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/update/{idUser}&{updated}")
    public ResponseEntity<?> updateNotificationsCount(
        @RequestHeader(value = "Authorization") String token,
        @PathVariable Long idUser,
        @PathVariable boolean updated
    ) {
        log.info("Entered '/ws/notifications/update/{idUser}&{updated}' with token {}, user ID {} and updated {} [GET].", token, idUser, updated);

        if (!requestValidator.validatePrivilegedToken(token)) {
            log.error("Token {} is not valid.", token);

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        simpMessagingTemplate.convertAndSendToUser(String.valueOf(idUser), "/queue/topic/private-notifications", updated);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
