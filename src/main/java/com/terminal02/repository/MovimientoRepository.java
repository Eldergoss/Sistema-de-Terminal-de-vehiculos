
package com.terminal02.repository;

import com.terminal02.database.Database;
import com.terminal02.model.Movimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovimientoRepository {

    // Registra un movimiento en la base de datos.
    public boolean registrarMovimiento(Movimiento movimiento) {

        String sql = """
                INSERT INTO movimientos (
                    id_unidad,
                    tipo_movimiento,
                    fecha,
                    hora,
                    id_destino
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, movimiento.getIdUnidad());
            statement.setString(2, movimiento.getTipoMovimiento());
            statement.setString(3, movimiento.getFecha());
            statement.setString(4, movimiento.getHora());

            // El destino es opcional.
            if (movimiento.getIdDestino() == null) {
                statement.setNull(5, java.sql.Types.INTEGER);
            } else {
                statement.setInt(5, movimiento.getIdDestino());
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar el movimiento", e);
        }
    }

    // Consulta todos los movimientos registrados.
    public List<Movimiento> consultarMovimientos() {

        // Consulta SQL para recuperar los movimientos.
        String sql = """
                SELECT
                    id,
                    id_unidad,
                    tipo_movimiento,
                    fecha,
                    hora,
                    id_destino
                FROM movimientos
                ORDER BY id DESC
                """;

        // Lista donde almacenaremos los resultados.
        List<Movimiento> movimientos = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            // Recorrer cada fila devuelta por SQLite.
            while (result.next()) {

                // Recuperar el destino permitiendo valores NULL.
                int destino = result.getInt("id_destino");

                Integer idDestino = result.wasNull()
                        ? null
                        : destino;

                // Construir el objeto Movimiento.
                Movimiento movimiento = new Movimiento(
                        result.getInt("id"),
                        result.getInt("id_unidad"),
                        result.getString("tipo_movimiento"),
                        result.getString("fecha"),
                        result.getString("hora"),
                        idDestino
                );

                // Agregar el movimiento a la lista.
                movimientos.add(movimiento);
            }

            return movimientos;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar los movimientos", e);
        }
    }
    
    // Consulta los movimientos registrados de una unidad específica.
    public List<Movimiento> consultarMovimientosPorUnidad(int idUnidad) {

        // Consultar únicamente los movimientos de la unidad solicitada.
        String sql = """
                SELECT
                    id,
                    id_unidad,
                    tipo_movimiento,
                    fecha,
                    hora,
                    id_destino
                FROM movimientos
                WHERE id_unidad = ?
                ORDER BY id DESC
                """;

        // Lista donde almacenaremos los resultados.
        List<Movimiento> movimientos = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Asignar el ID de unidad al parámetro de la consulta.
            statement.setInt(1, idUnidad);

            try (ResultSet result = statement.executeQuery()) {

                // Recorrer los movimientos encontrados.
                while (result.next()) {

                    // Recuperar el destino permitiendo valores NULL.
                    int destino = result.getInt("id_destino");

                    Integer idDestino = result.wasNull()
                            ? null
                            : destino;

                    // Construir el objeto Movimiento.
                    Movimiento movimiento = new Movimiento(
                            result.getInt("id"),
                            result.getInt("id_unidad"),
                            result.getString("tipo_movimiento"),
                            result.getString("fecha"),
                            result.getString("hora"),
                            idDestino
                    );

                    movimientos.add(movimiento);
                }
            }

            return movimientos;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar los movimientos de la unidad", e);
        }
    }




























        // Consulta el último movimiento registrado de una unidad.
    public Optional<String> buscarUltimoTipoMovimiento(int idUnidad) {

        String sql = """
                SELECT tipo_movimiento
                FROM movimientos
                WHERE id_unidad = ?
                ORDER BY id DESC
                LIMIT 1
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idUnidad);

            try (ResultSet result = statement.executeQuery()) {

                // Si existe un movimiento, devolvemos su tipo.
                if (result.next()) {
                    return Optional.of(result.getString("tipo_movimiento"));
                }

                // Si no hay movimientos, devolvemos un Optional vacío.
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar el último movimiento", e);
        }
    }
}






