
package com.terminal02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.terminal02.database.Database;
import com.terminal02.model.Unidad;

// Clase responsable de ejecutar las consultas SQL de unidades.
public class UnidadRepository {

    // Busca una unidad por su placa.
    public Optional<Unidad> buscarPorPlaca(String placa) {

        String sql = """
                SELECT id, numero, placa
                FROM unidades
                WHERE placa = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, placa);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Unidad unidad = new Unidad(
                            result.getInt("id"),
                            result.getString("numero"),
                            result.getString("placa")
                    );

                    return Optional.of(unidad);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar la unidad por placa", e
            );
        }

        return Optional.empty();
    }

    // Busca una unidad por su número.
    public Optional<Unidad> buscarPorNumero(String numero) {

        String sql = """
                SELECT id, numero, placa
                FROM unidades
                WHERE numero = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, numero);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Unidad unidad = new Unidad(
                            result.getInt("id"),
                            result.getString("numero"),
                            result.getString("placa")
                    );

                    return Optional.of(unidad);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar la unidad por número", e
            );
        }

        return Optional.empty();
    }

    // Consulta todas las unidades registradas.
    public List<Unidad> consultarUnidades() {

        String sql = """
                SELECT id, numero, placa
                FROM unidades
                """;

        List<Unidad> unidades = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Unidad unidad = new Unidad(
                        result.getInt("id"),
                        result.getString("numero"),
                        result.getString("placa")
                );

                unidades.add(unidad);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar las unidades", e
            );
        }

        return unidades;
    }

    // Busca la unidad asociada a un usuario transportista.
    public Optional<Unidad> buscarPorIdUsuario(int idUsuario) {

        String sql = """
                SELECT u.id, u.numero, u.placa
                FROM unidades u
                INNER JOIN usuarios us ON us.id_unidad = u.id
                WHERE us.id = ?
                AND us.rol = 'TRANSPORTISTA'
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Unidad unidad = new Unidad(
                            result.getInt("id"),
                            result.getString("numero"),
                            result.getString("placa")
                    );

                    return Optional.of(unidad);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar la unidad del transportista", e
            );
        }

        return Optional.empty();
    }

    // Actualiza la placa de una unidad.
    public boolean actualizarPlaca(int idUnidad, String nuevaPlaca) {

        String sql = """
                UPDATE unidades
                SET placa = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nuevaPlaca);
            statement.setInt(2, idUnidad);

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar la placa de la unidad", e
            );
        }
    }

    // Registra una nueva unidad.
    public boolean registrarUnidad(Unidad unidad) {

        String sql = """
                INSERT INTO unidades (numero, placa)
                VALUES (?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, unidad.getNumero());
            statement.setString(2, unidad.getPlaca());

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar la unidad", e
            );
        }
    }



    // Busca una unidad utilizando su identificador.
public Optional<Unidad> buscarPorId(int id) {

    // Consulta SQL para localizar la unidad.
    String sql = """
            SELECT id, numero, placa
            FROM unidades
            WHERE id = ?
            """;

    // Ejecutar la consulta.
    try (Connection connection = Database.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        // Asignar el ID al parámetro de la consulta.
        statement.setInt(1, id);

        // Obtener el resultado.
        try (ResultSet resultSet = statement.executeQuery()) {

            // Comprobar si se encontró una unidad.
            if (resultSet.next()) {

                // Construir el objeto con los datos encontrados.
                Unidad unidad = new Unidad(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getString("placa")
                );

                return Optional.of(unidad);
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException("Error al buscar la unidad", e);
    }

    // Devolver Optional vacío si no existe.
    return Optional.empty();
}


}
