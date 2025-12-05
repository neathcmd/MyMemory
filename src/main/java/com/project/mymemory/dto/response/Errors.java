package com.project.mymemory.dto.response;

// ======= ERROR FACTORY ======= //
public class Errors {
    public static ApiException badRequest(String message) {
        return new ApiException(400, message != null ? message: "Bad request.");
    }

    public static ApiException notFound(String message) {
        return new ApiException(404, message != null ? message: "Data not found.");
    }

    public static ApiException unAuthroized(String message) {
        return new ApiException(401, message != null ? message: "Token requried.");
    }

    public static ApiException forbidden(String message) {
        return new ApiException(403, message != null ? message: "You are not allowed to do this action.");
    }

    public static ApiException validation(String message) {
        return new ApiException(422, message != null ? message: "Validation falied.");
    }

    public static ApiException internal(String message) {
        return new ApiException(500, message != null ? message: "Internal server error.");
    }
}
