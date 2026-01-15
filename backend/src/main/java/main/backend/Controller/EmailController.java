package main.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import main.backend.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api-v0.2/email")
@RequiredArgsConstructor
public class EmailController {
    @Autowired
    MailService mailService;

    @PostMapping("/subscribe")
    @Async
    @Operation(
            summary = "Сохраняем данные пользователя для письма",
            description = "Сохраняет данные пользователя для отправки писем"
    )
    public ResponseEntity<?> subscribeUser(
            @Parameter(description = "email пользователя", required = true, example = "example@example.com")
            @Email(message = "Некорректный формат email")
            @RequestParam String email,
            @Parameter(description = "Имя и фамилия пользователя", required = true, example = "Иванов Иван")
            @Pattern(
                    regexp = "^[a-zA-Zа-яА-ЯёЁ\\s\\-]+$",
                    message = "Имя может содержать только буквы, пробелы и дефис"
            )
            @RequestParam String name
    ) {
        try {
            mailService.subscribeUser(email, name);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Template email sent successfully",
                    "recipient", email
            ));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Failed to send template email",
                            "error", e.getMessage()
                    ));
        }
    }

    @PostMapping("/subscribe/confirmation")
    @Async
    @Operation(
            summary = "Подтверждаем почту пользователя",
            description = "Подтверждает почту пользователя по почте"
    )
    public void confirmationUser(
            @Parameter(description = "email пользователя", required = true, example = "example@example.com")
            @Email(message = "Некорректный формат email")
            @RequestParam String email
    ) throws MessagingException {
        mailService.confirmationUser(email);
    }

    @Scheduled(cron = "1 0 * * * *")
    @Async
    public void DeleteUser() {
        mailService.deleteUserMailNotConfirmed();
    }
}
