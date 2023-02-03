package br.com.totvs.response;


import br.com.totvs.utils.GsonUtil;
import spark.Response;

import java.util.Objects;

public class ResponseResource<E> {

    private E resource;
    private String message;
    private int status;

    private ResponseResource(TypeStatus typeStatus, E resource) {
        this.resource = resource;
        this.message = typeStatus.getMessage();
        this.status = typeStatus.getStatus();
    }

    public static <E> ResponseResource<E> of(Response response, TypeStatus typeStatus, E resource) {
        response.status(typeStatus.getStatus());
        response.type("application/json");
        return new ResponseResource<>(typeStatus, resource);
    }

    public static <E> ResponseResource<E> of(Response response, TypeStatus typeStatus) {
        response.status(typeStatus.getStatus());
        response.type("application/json");
        return new ResponseResource<>(typeStatus, null);
    }

    @Override
    public String toString() {
        return "ResponseResource{" +
                "resource=" + resource +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
