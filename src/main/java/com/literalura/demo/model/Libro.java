package com.literalura.demo.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Libro {
    private int id;
    String titulo;
    List<Autor> autores;
    List<String> lenguajes;

    public Libro(DatosLibro l) {
        this.id = l.id();
        this.titulo = l.title();

        //this.autores = l.authors();
        this.autores = l.authors().stream()
                .map(a -> new Autor(a))
                .toList();

        this.lenguajes = l.languages();
    }

    public Libro(int id, String titulo, String lenguaje, String autorNombre, String anioNacimiento, String anioMuerte) {
        this.id = id;
        this.titulo = titulo;
        this.lenguajes = List.of(lenguaje);

        Integer nacimiento = (anioNacimiento != null && !anioNacimiento.isBlank()) ? Integer.parseInt(anioNacimiento) : null;
        Integer muerte = (anioMuerte != null && !anioMuerte.isBlank()) ? Integer.parseInt(anioMuerte) : null;

        this.autores = List.of(new Autor(autorNombre, nacimiento, muerte));
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }

    @Override
    public String toString() {
        return "===== LIBRO =====" + '\n' +
                "id=" + id + '\n' +
                "titulo='" + titulo + '\n' +
                "autores=" + autores + '\n' +
                "lenguajes=" + lenguajes +  '\n';
    }

    public String getAutoresString() {
        return autores.stream()
                .map(Autor::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Sin autores");
    }

    public String getLenguajesString() {
        return String.join(", ", lenguajes);
    }
}
