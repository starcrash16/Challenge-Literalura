package com.literalura.demo.model;

public class Autor {
    String nombre;
    Integer  anio_nacimiento;
    Integer anio_muerte;

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.name();
        this.anio_nacimiento = datosAutor.birth_year();
        this.anio_muerte = datosAutor.death_year();
    }

    public Autor(String nombre) {
        this.nombre = nombre;
        this.anio_nacimiento = null;
        this.anio_muerte = null;
    }

    public Autor(String nombre, Integer anioNacimiento, Integer anioMuerte) {
        this.nombre = nombre;
        this.anio_nacimiento = anioNacimiento;
        this.anio_muerte = anioMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacido() {
        return anio_nacimiento;
    }

    public void setAnioNacido(Integer anio_nacimiento) {
        this.anio_nacimiento = anio_nacimiento;
    }

    public Integer getAnioFallecido() {
        return anio_muerte;
    }

    public void setAnioFallecido(Integer anio_muerte) {
        this.anio_muerte = anio_muerte;
    }

    @Override
    public String toString() {
        return  '\t' + "nombre=" + nombre +
                ", anio_nacimiento=" + anio_nacimiento +
                ", anio_muerte=" + anio_muerte + ',';
    }
}