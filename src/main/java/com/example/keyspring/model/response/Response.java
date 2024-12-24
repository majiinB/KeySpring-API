package com.example.keyspring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * Represents a generic response structure used throughout the application.
 * Contains a status code, a message, and optional data.
 *
 * @param status The status code of the response (e.g., "200" for success, "400" for bad request).
 * @param message A message providing additional information about the response.
 * @param data Optional data associated with the response.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-23
 * @modified 2024-12-24
 */
public class Response {
    private String status;
    private String message;
    private Object data;
}
