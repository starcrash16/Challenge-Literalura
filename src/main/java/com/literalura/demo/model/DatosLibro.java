package com.literalura.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(int id,
                         String title,
                         List<DatosAutor> authors,
                         List<String> languages) {}
