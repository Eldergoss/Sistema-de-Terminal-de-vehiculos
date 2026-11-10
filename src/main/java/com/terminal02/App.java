
package com.terminal02;

import com.terminal02.controller.DestinoController;
import com.terminal02.controller.MovimientoController;
import com.terminal02.controller.UnidadController;

import com.terminal02.database.Database;
import com.terminal02.database.DatabaseInitializer;

import com.terminal02.model.Destino;
import com.terminal02.model.Movimiento;
import com.terminal02.model.Unidad;

import com.terminal02.repository.DestinoRepository;
import com.terminal02.repository.MovimientoRepository;
import com.terminal02.repository.UnidadRepository;

import com.terminal02.service.DestinoService;
import com.terminal02.service.MovimientoService;
import com.terminal02.service.UnidadService;

import io.jooby.Jooby;
import io.jooby.jackson.Jackson2Module;

import java.sql.SQLException;

// Clase principal: configura la aplicación y sus rutas HTTP.
public class App extends Jooby {

    {
        // Instalar Jackson para procesar solicitudes y respuestas JSON.
        install(new Jackson2Module());

        // Inicializar la base de datos al arrancar la aplicación.
        try {
            DatabaseInitializer.initialize();
        } catch (SQLException e) {
            throw new RuntimeException(
                "No se pudo inicializar la base de datos", e
            );
        }

        // Construir los Controllers y conectar sus dependencias.
        UnidadController unidadController =
            crearUnidadController();

        MovimientoController movimientoController =
            crearMovimientoController();

        DestinoController destinoController =
            crearDestinoController();

        // Endpoint principal.
        get("/", ctx -> "Sistema Terminal 02 funcionando");

        // Comprobar conexión y claves foráneas.
        get("/db-test", ctx -> {
            try (
                var connection = Database.getConnection();
                var statement = connection.createStatement();
                var result = statement.executeQuery(
                    "PRAGMA foreign_keys"
                )
            ) {
                if (!connection.isValid(2)) {
                    ctx.setResponseCode(500);
                    return "Conexión con SQLite no válida";
                }

                if (result.next() && result.getInt(1) == 1) {
                    return "Conexión con SQLite correcta. "
                        + "Claves foráneas activadas.";
                }

                ctx.setResponseCode(500);
                return "Conexión correcta, pero las claves "
                    + "foráneas están desactivadas.";

            } catch (SQLException e) {
                ctx.setResponseCode(500);
                return "Error al conectar con SQLite";
            }
        });

        // =========================
        // MÓDULO DE UNIDADES
        // =========================

        // Consultar todas las unidades.
        get("/unidades", ctx ->
            unidadController.consultarUnidades()
        );

        // Registrar una nueva unidad.
        post("/unidades", ctx -> {

            Unidad unidad = ctx.body(Unidad.class);

            boolean registrada =
                unidadController.registrarUnidad(unidad);

            if (!registrada) {
                ctx.setResponseCode(400);
                return "No se pudo registrar la unidad. "
                    + "Verifica los datos o posibles duplicados.";
            }

            ctx.setResponseCode(201);
            return "Unidad registrada correctamente";
        });

        // Actualizar la placa de una unidad.
        put("/unidades/{id}/placa", ctx -> {

            int idUnidad = ctx.path("id").intValue();

            Unidad unidad = ctx.body(Unidad.class);

            boolean actualizada =
                unidadController.actualizarPlaca(
                    idUnidad,
                    unidad.getPlaca()
                );

            if (!actualizada) {
                ctx.setResponseCode(400);
                return "No se pudo actualizar la placa";
            }

            ctx.setResponseCode(200);
            return "Placa actualizada correctamente";
        });

        // =========================
        // MÓDULO DE MOVIMIENTOS
        // =========================

        // Registrar una entrada o salida.
        post("/movimientos", ctx -> {

            Movimiento movimiento =
                ctx.body(Movimiento.class);

            boolean registrado =
                movimientoController.registrarMovimiento(
                    movimiento
                );

            if (!registrado) {
                ctx.setResponseCode(400);
                return "No se pudo registrar el movimiento";
            }

            ctx.setResponseCode(201);
            return "Movimiento registrado correctamente";
        });

        // Consultar todos los movimientos.
        get("/movimientos", ctx ->
            movimientoController.consultarMovimientos()
        );

        // Consultar los movimientos de una unidad específica.
        get("/movimientos/unidad/{id}", ctx -> {

            int idUnidad = ctx.path("id").intValue();

            return movimientoController
                .consultarMovimientosPorUnidad(idUnidad);
        });

        // =========================
        // MÓDULO DE DESTINOS
        // =========================

        // Registrar un nuevo destino.
        post("/destinos", ctx -> {

            Destino destino = ctx.body(Destino.class);

            boolean registrado =
                destinoController.registrarDestino(destino);

            if (!registrado) {
                ctx.setResponseCode(400);
                return "No se pudo registrar el destino";
            }

            ctx.setResponseCode(201);
            return "Destino registrado correctamente";
        });

        // Consultar todos los destinos.
        get("/destinos", ctx ->
            destinoController.consultarDestinos()
        );
    }

    // Construye los objetos del módulo de unidades.
    private UnidadController crearUnidadController() {

        UnidadRepository repository =
            new UnidadRepository();

        UnidadService service =
            new UnidadService(repository);

        return new UnidadController(service);
    }

    // Construye los objetos del módulo de movimientos.
    private MovimientoController crearMovimientoController() {

        MovimientoRepository movimientoRepository =
            new MovimientoRepository();

        UnidadRepository unidadRepository =
            new UnidadRepository();

        MovimientoService service =
            new MovimientoService(
                movimientoRepository,
                unidadRepository
            );

        return new MovimientoController(service);
    }

    // Construye los objetos del módulo de destinos.
    private DestinoController crearDestinoController() {

        DestinoRepository repository =
            new DestinoRepository();

        DestinoService service =
            new DestinoService(repository);

        return new DestinoController(service);
    }

    // Punto de entrada de la aplicación.
    public static void main(String[] args) {
        runApp(args, App::new);
    }
}
