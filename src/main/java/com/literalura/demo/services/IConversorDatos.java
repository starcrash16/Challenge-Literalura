package com.literalura.demo.services;

public interface IConversorDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
