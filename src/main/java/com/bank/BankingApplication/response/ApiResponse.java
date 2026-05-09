package com.bank.BankingApplication.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String error;
    private LocalDateTime timestamp;
    private HttpStatus status;

    // Success response with data
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operation successful", data, null, LocalDateTime.now(), HttpStatus.OK);
    }

    // Success response with custom message
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null, LocalDateTime.now(), HttpStatus.OK);
    }

    // Success response without data
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null, null, LocalDateTime.now(), HttpStatus.OK);
    }

    // Error response
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, null, message, LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }

    // Error response with custom status
    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, null, null, message, LocalDateTime.now(), status);
    }

    // Created response
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, "Resource created successfully", data, null, LocalDateTime.now(), HttpStatus.CREATED);
    }

    // No content response
    public static <T> ApiResponse<T> noContent(String message) {
        return new ApiResponse<>(true, message, null, null, LocalDateTime.now(), HttpStatus.NO_CONTENT);
    }
}