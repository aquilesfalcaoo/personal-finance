package com.personal.finance.application.handler;

import com.personal.finance.application.dto.budget.BudgetCreateResponse;
import com.personal.finance.application.dto.error.ErrorResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseHandler {
    public static <T> ResponseEntity<BudgetCreateResponse<T>> success(String message, UUID id, T data, int status) {
        BudgetCreateResponse<T> response = new BudgetCreateResponse<>(
                LocalDateTime.now(),
                message,
                id,
                data
        );
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<ErrorResponse> badRequest(String message, String path) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                message,
                path
        );
        return ResponseEntity.status(400).body(response);
    }

    public static ResponseEntity<ErrorResponse> resourceNotFound(String message, String path) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Resource Not Found",
                message,
                path
        );
        return ResponseEntity.status(404).body(response);
    }

    public static ResponseEntity<ErrorResponse> internalServerError(String message, String path) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                500,
                "Internal Server Error",
                message,
                path
        );
        return ResponseEntity.status(500).body(response);
    }
}
