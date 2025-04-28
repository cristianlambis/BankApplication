package com.devsu.hackerearth.backend.account.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    
    private String message;
    private int status;
    private LocalDateTime timestamp;

}
