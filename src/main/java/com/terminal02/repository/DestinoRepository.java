
package com.terminal02.repository;

import com.terminal02.database.Database;
import com.terminal02.model.Destino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DestinoRepository {

    // Registrar un destino en la base de datos.
    public boolean registrarDestino(Destino destino) {

        String sql = """
                INSERT INTO destinos (nombre)
                VALUES (?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, destino.getNombre());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar el destino", e);
        }
    }

    // Consultar todos los destinos registrados.
    public List<Destino> consultarDestinos() {

        String sql = """
                SELECT id, nombre
                FROM destinos
                ORDER BY id DESC
                """;

        List<Destino> destinos = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                // Convertir cada fila de SQLite en un objeto Destino.
                Destino destino = new Destino(
                        result.getInt("id"),
                        result.getString("nombre")
                );

                destinos.add(destino);
            }

            return destinos;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar los destinos", e);
        }
    }
}
