package com.alura.literalura.servicio;

public interface IConvierteData {
    <T> T obtenerDatos(String json, Class<T> clase);
}