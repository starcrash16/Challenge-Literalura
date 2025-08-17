package com.literalura.demo.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroDB {
    private final String url = "jdbc:postgresql://localhost:5432/literalura";
    private final String user = "postgres";
    private final String password = "Rosendo.1";        //CAMBIAR

    public LibroDB() {
        crearTabla();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void crearTabla() {
        String sql = """
            CREATE TABLE IF NOT EXISTS libros (
                id INTEGER PRIMARY KEY,
                titulo TEXT NOT NULL,
                autores TEXT,
                lenguaje TEXT,
                anio_nacimiento INTEGER,
                anio_muerte INTEGER
            );
            """;
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    public void guardar(Libro libro, List<Autor> autores) {
        String sql = "INSERT INTO libros (id, titulo, autores, lenguaje, anio_nacimiento, anio_muerte) " +
                "VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Extraer primer autor, si existe
            Autor autor = (autores != null && !autores.isEmpty()) ? autores.get(0) : null;

            pstmt.setInt(1, libro.getId());
            pstmt.setString(2, libro.getTitulo());

            // Nombres separados por coma si hay varios autores
            String autoresString = autores.stream()
                    .map(Autor::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Desconocido");
            pstmt.setString(3, autoresString);

            pstmt.setString(4, libro.getLenguajesString());

            pstmt.setObject(5, autor != null ? autor.getAnioNacido() : null, Types.INTEGER);
            pstmt.setObject(6, autor != null ? autor.getAnioFallecido() : null, Types.INTEGER);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar libro: " + e.getMessage());
        }
    }


    public List<Libro> obtenerTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("lenguaje"),
                        rs.getString("autores"),
                        rs.getString("anio_nacimiento"),
                        rs.getString("anio_muerte")
                );
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener libros: " + e.getMessage());
        }
        return lista;
    }

    public Map<String, List<String>> obtenerAutoresConLibros() {
        Map<String, List<String>> autoresLibros = new HashMap<>();

        String sql = "SELECT autores, titulo FROM libros ORDER BY autores, titulo";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String autor = rs.getString("autores");
                String titulo = rs.getString("titulo");

                autoresLibros.computeIfAbsent(autor, k -> new ArrayList<>()).add(titulo);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar autores: " + e.getMessage());
        }

        return autoresLibros;
    }

    public Map<String, List<String>> obtenerAutoresVivosEnAnio(int anio) {
        Map<String, List<String>> autoresVivos = new HashMap<>();

        String sql = """
            SELECT autores, titulo
            FROM libros
            WHERE anio_nacimiento <= ? AND (anio_muerte IS NULL OR anio_muerte > ?)
            ORDER BY autores, titulo
            """;

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, anio);
            pstmt.setInt(2, anio);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String autor = rs.getString("autores");
                String titulo = rs.getString("titulo");
                autoresVivos.computeIfAbsent(autor, k -> new ArrayList<>()).add(titulo);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar autores: " + e.getMessage());
        }

        return autoresVivos;
    }

    public List<Libro> obtenerLibrosPorIdioma(String idioma) {
        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT * FROM libros WHERE lenguaje LIKE ?";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + idioma + "%"); // para coincidir parcialmente

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("lenguaje"),
                        rs.getString("autores"),
                        rs.getString("anio_nacimiento"),
                        rs.getString("anio_muerte")
                );
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener libros por idioma: " + e.getMessage());
        }

        return libros;
    }


}