package com.literalura.demo.Principal;

import com.literalura.demo.model.ApiResponse;
import com.literalura.demo.model.DatosLibro;
import com.literalura.demo.model.Libro;
import com.literalura.demo.model.LibroDB;
import com.literalura.demo.services.ConsumirAPI;
import com.literalura.demo.services.ConversorDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    static Scanner teclado = new Scanner(System.in);
    private ConsumirAPI consumirAPI = new ConsumirAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private String libroUser = "";
    private ConversorDatos conversor = new ConversorDatos();
    private List<Libro> datosLibros = new ArrayList<>();
    private LibroDB libroDAO = new LibroDB();


    public void menu(){
        int opc = -1;
        var menu = """
                1- Buscar Libro en el API por Titulo.
                2- Listar libros de la Base de Datos.
                3- Listar Autores de la Base de Datos.
                4- Listar Autores en un determinado año [estando vivos].
                5- Listar Libros de la Base de Datos por Idioma.
                
                0- Finalizar Programa.
                """;

        while (opc != 0){
            System.out.println();
            System.out.println("========== MENÚ PRINCIPAL ==========");
            System.out.println(menu);
            System.out.print("Seleccione una Opcion: ");
            try{
                opc = teclado.nextInt();
                teclado.nextLine();

                switch (opc) {
                    case 1:
                        apiLibro();
                        break;
                    case 2:
                        librosDB();
                        break;
                    case 3:
                        autoresDB();
                        break;
                    case 4:
                        autoresFechaDB();
                        break;
                    case 5:
                        librosIdiomaDB();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }catch (Exception e){
                System.out.println("Error: " + e);
                teclado.nextLine();
            }

        }
    }

    private void librosIdiomaDB() {
        System.out.print("Ingrese el idioma (en, es, fr ...): ");
        String idioma = teclado.nextLine().trim().toLowerCase();

        List<Libro> librosFiltrados = libroDAO.obtenerLibrosPorIdioma(idioma);

        if (librosFiltrados.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("Libros encontrados en el idioma: " + idioma);
            librosFiltrados.forEach(System.out::println);
        }
    }

    private void autoresFechaDB() {
        System.out.print("Ingrese el año a consultar: ");
        int anio = teclado.nextInt();
        teclado.nextLine(); // limpiar buffer

        var autoresVivos = libroDAO.obtenerAutoresVivosEnAnio(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }

        System.out.println("Autores vivos en el año " + anio + ":");
        for (var entry : autoresVivos.entrySet()) {
            System.out.println("=====AUTOR=====");
            System.out.println("    nombre: " + entry.getKey());
            for (String titulo : entry.getValue()) {
                System.out.println("   - Libro:  " + titulo);
            }
        }
    }

    private void autoresDB() {
        var autoresLibros = libroDAO.obtenerAutoresConLibros();

        if (autoresLibros.isEmpty()) {
            System.out.println("No hay autores en la base de datos.");
            return;
        }

        for (var entry : autoresLibros.entrySet()) {
            System.out.println("=====AUTOR=====");
            System.out.println("    nombre: " + entry.getKey());
            for (String titulo : entry.getValue()) {
                System.out.println("   - Libro:  " + titulo);
            }
        }
    }

    private void librosDB() {
        List<Libro> librosEnBD = libroDAO.obtenerTodos();
        if (librosEnBD.isEmpty()) {
            System.out.println("No hay libros guardados en la base de datos.");
        } else {
            librosEnBD.forEach(System.out::println);
        }
    }

    private void apiLibro() {
        System.out.print("Titulo del Libro: ");
        libroUser = teclado.nextLine();

        var json = consumirAPI.obtenerDatos(URL_BASE + libroUser.replace(" ", "+"));
        System.out.println("JSON: " + json);

        var respuesta = conversor.obtenerDatos(json, ApiResponse.class);
        if (respuesta.results().isEmpty()){
            System.out.println("No se encontró el Liro");
            return;
        }

        List<Libro> libros = respuesta.results().stream()
                        .map(r -> new Libro(r))
                                .toList();

        System.out.println("Libros Encontrados! ");
        for (Libro libro : libros) {
            System.out.println(libro);
            libroDAO.guardar(libro, libro.getAutores());
        }
        System.out.println("Libros guardados en la base de datos.");
    }
}
