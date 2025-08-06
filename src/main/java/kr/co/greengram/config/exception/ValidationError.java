package kr.co.greengram.config.exception;

import lombok.*;
import org.springframework.validation.FieldError;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ValidationError {
    private String field;
    private String message;

    public static ValidationError of(final FieldError fieldError) {
        return ValidationError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }
}
