package br.com.totvs;

import br.com.totvs.routes.RouteConfig;

public class Main {
    public static void main(String[] args) {

        RouteConfig.initHMG(true, true, false);
        // RouteConfig.initPROD(8080, true, true, false);
    }
}