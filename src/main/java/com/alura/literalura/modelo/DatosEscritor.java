package com.alura.literalura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosEscritor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaFallecimiento){

}