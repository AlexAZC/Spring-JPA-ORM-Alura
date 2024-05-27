package com.aluraSpring.screenmatchSeries.service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}
