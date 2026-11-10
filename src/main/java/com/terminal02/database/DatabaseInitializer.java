
package com.terminal02.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() throws SQLException {

        String sqlUnidades = """
            CREATE TABLE IF NOT EXISTS unidades (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                numero TEXT NOT NULL UNIQUE,
                placa TEXT NOT NULL UNIQUE
            )
            """;

        String sqlUsuarios = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                rol TEXT NOT NULL CHECK (
                    rol IN ('ADMINISTRADOR', 'TRANSPORTISTA')
                ),
                id_unidad INTEGER,
                FOREIGN KEY (id_unidad) REFERENCES unidades(id)
            )
            """;

        String sqlDestinos = """
            CREATE TABLE IF NOT EXISTS destinos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL
            )
            """;

        String sqlMovimientos = """
            CREATE TABLE IF NOT EXISTS movimientos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_unidad INTEGER NOT NULL,
                tipo_movimiento TEXT NOT NULL CHECK (
                    tipo_movimiento IN ('ENTRADA', 'SALIDA')
                ),
                fecha TEXT NOT NULL,
                hora TEXT NOT NULL,
                id_destino INTEGER,
                FOREIGN KEY (id_unidad) REFERENCES unidades(id),
                FOREIGN KEY (id_destino) REFERENCES destinos(id)
            )
            """;

        String sqlAuditoria = """
            CREATE TABLE IF NOT EXISTS auditoria (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                hora TEXT NOT NULL,
                tipo_accion TEXT NOT NULL,
                registro_afectado TEXT,
                descripcion TEXT,
                FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
            )
            """;

        try (
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement()
        ) {
            statement.execute(sqlUnidades);
            statement.execute(sqlUsuarios);
            statement.execute(sqlDestinos);
            statement.execute(sqlMovimientos);
            statement.execute(sqlAuditoria);
        }
    }
}
