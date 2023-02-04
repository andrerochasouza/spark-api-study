package br.com.totvs.response;


import br.com.totvs.utils.GsonUtil;
import spark.Response;

import java.util.Objects;

public class ResponseResource<E> {

    private E resource;
    private String messageError;
    private String messageStatus;
    private int status;

    private ResponseResource(E resource, String messageError, TypeStatus typeStatus) {
        this.resource = resource;
        this.messageError = messageError;
        this.messageStatus = typeStatus.getMessage();
        this.status = typeStatus.getStatus();
    }

    public static <E> ResponseResource<E> of(Response response, E resource, TypeStatus typeStatus) {
        response.status(typeStatus.getStatus());
        response.type("application/json");
        return new ResponseResource<>(resource, null, typeStatus);
    }

    public static ResponseResource ofError(Response response, String messageError, TypeStatus typeStatus) {
        response.status(typeStatus.getStatus());
        response.type("application/json");
        return new ResponseResource<>(null, messageError, typeStatus);
    }

    @Override
    public String toString() {
        return "ResponseResource{" +
                "resource=" + resource +
                ", messageError='" + messageError + '\'' +
                ", messageStatus='" + messageStatus + '\'' +
                ", status=" + status +
                '}';
    }
}
