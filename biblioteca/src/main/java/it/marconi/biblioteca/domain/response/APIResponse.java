package it.marconi.biblioteca.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class APIResponse<T> {

    private String status;
    private T data;
    private String message;

    private APIResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>("success", data, null);
    }

    public static <T> APIResponse<T> fail(T data) {
        return new APIResponse<>("fail", data, null);
    }

    public static <T> APIResponse<T> error(String message) {
        return new APIResponse<>("error", null, message);
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}