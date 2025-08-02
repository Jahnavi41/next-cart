package dev.ju.nextcart.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private String message;
    private int status;
    private String code;
    private LocalDateTime timestamp;

}
