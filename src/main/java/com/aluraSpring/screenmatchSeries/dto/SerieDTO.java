package com.aluraSpring.screenmatchSeries.dto;

import com.aluraSpring.screenmatchSeries.model.Categoria;


public record SerieDTO(
         Long id,
         String titulo,
         Integer totalDeTemporadas,
         Double evaluacion,
         String fechaDeLanzamiento,
         Categoria genero,
         String sinopsis,
         String poster,
         String actores) {
}
