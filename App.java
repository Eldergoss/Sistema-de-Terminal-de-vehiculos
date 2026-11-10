
package com.terminal02;

import io.jooby.Jooby;

public class App extends Jooby {

    {
        get("/", ctx -> "Sistema Terminal 02 funcionando");

    }

    {
        get("/holamundo" ctx -> "sistema funcionando completo seññores");
    }

    public static void main(String[] args) {
        runApp(args, App::new);
    }
}
