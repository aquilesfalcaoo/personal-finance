package com.personal.finance.application.dto.budget;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response object for budget creation.")
public record BudgetCreateResponse<T>(
        @Schema(description = "Timestamp of the response.", example = "2025-01-10T22:32:36.2200601")
        LocalDateTime timestamp,

        @Schema(description = "Success message indicating budget was created.", example = "Budget created successfully.")
        String message,

        @Schema(description = "ID of the created budget.", example = "245758d9-e4ce-413c-90c1-73fe93f26a50")
        UUID id,

        @Schema(description = "Details of the created budget.", implementation = BudgetRequestPayload.class)
        T data
) { }
