package com.personal.finance.interfaces.controllers.budget;

import com.personal.finance.application.dto.budget.BudgetUpdateResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.servlet.http.HttpServletRequest;
import com.personal.finance.application.dto.budget.BudgetCreateResponse;
import com.personal.finance.application.dto.budget.BudgetRequestPayload;
import com.personal.finance.application.dto.error.ErrorResponse;
import com.personal.finance.application.handler.ResponseHandler;
import com.personal.finance.application.services.budget.BudgetService;
import com.personal.finance.domain.exceptions.BadRequestException;
import com.personal.finance.domain.exceptions.ResourceNotFoundException;
import com.personal.finance.infrastructure.persistence.entities.budget.Budget;
import com.personal.finance.infrastructure.persistence.repositories.budget.BudgetRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/budgets")
@Tag(name = "Budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetRepository repository;

    @Operation(summary = "Get all budgets.", description = "Return a list of all budgets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budgets successfully returned.",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Budget.class)))}),
            @ApiResponse(responseCode = "400", description = "Issue with Request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Resource Not Found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<?> getBudgets(HttpServletRequest request) {
        try {
            List<Budget> getAllBudgets = this.repository.findAll();

            if (getAllBudgets.isEmpty()) {
                return ResponseHandler.resourceNotFound("No budgets found.", request.getRequestURI());
            }

            return ResponseEntity.ok(getAllBudgets);

        } catch (Exception ex) {
            return ResponseHandler.internalServerError("An unexpected error occurred.", request.getRequestURI());
        }
    }

    @Operation(summary = "Add a new budget.", description = "Adds a new budget to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Budget added successfully.",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BudgetCreateResponse.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Issue with Request.",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )})
    })
    @PostMapping
    public ResponseEntity<?> createBudget(@RequestBody BudgetRequestPayload payload, HttpServletRequest request) {
        try {
            if (payload.theme() == null || payload.maximumSpend() <= 0 || payload.category() == null) {
                throw new BadRequestException("Invalid budget payload: Theme, category and maximumSpend are required.");
            }

            Budget newBudget = new Budget(payload);
            this.repository.save(newBudget);

            return ResponseHandler.success(
                    "Budget created successfully.",
                    newBudget.getId(),
                    payload, 201
            );

        } catch (BadRequestException ex) {
            return ResponseHandler.badRequest(ex.getMessage(), request.getRequestURI());
        } catch (ResourceNotFoundException ex) {
            return ResponseHandler.resourceNotFound(ex.getMessage(), request.getRequestURI());
        } catch (Exception ex) {
            return ResponseHandler.internalServerError("An unexpected error occurred.", request.getRequestURI());
        }
    }

    @Operation(summary = "Update a budget.", description = "Update a budget to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budget updated successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetUpdateResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Resource Not Found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBudget(@PathVariable UUID id, @RequestBody BudgetRequestPayload payload, HttpServletRequest request) {
        try {
            Optional<Budget> budget = this.repository.findById(id);

            if (budget.isPresent()) {
                Budget rawBudget = budget.get();
                rawBudget.setTheme(payload.theme());
                rawBudget.setMaximumSpend(payload.maximumSpend());
                rawBudget.setCategory(payload.category());

                this.repository.save(rawBudget);

                return ResponseHandler.success("Budget updated successfully.", rawBudget.getId(), payload, 200);
            }

            return ResponseHandler.resourceNotFound("Budget with id " + id + " not found.", request.getRequestURI());

        } catch (Exception ex) {
            return ResponseHandler.internalServerError("An unexpected error occurred.", request.getRequestURI());
        }
    }

    @Operation(
            summary = "Delete a budget.",
            description = "Deletes a budget from the database based on the provided ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Budget deleted successfully.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource Not Found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBudget(@PathVariable UUID id, HttpServletRequest request) {
        Optional<Budget> budget = this.repository.findById(id);

        if (budget.isEmpty()) {
            return ResponseHandler.resourceNotFound("Budget ID not found.", request.getRequestURI());
        }

        this.repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
