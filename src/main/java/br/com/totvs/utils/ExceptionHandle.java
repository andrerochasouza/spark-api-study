package br.com.totvs.utils;

import spark.Request;
import spark.Response;

public class ExceptionHandle {

    public static void handleException400(Exception e, Request req, Response res){
        res.status(400);
        res.body(e.getMessage());
    }

    public static void handleException401(Exception e, Request req, Response res){
        res.status(401);
        res.body("Acesso negado");
    }

    public static void handleException500(Exception e, Request req, Response res){
        res.status(500);
        res.body(e.getMessage());
    }

}
