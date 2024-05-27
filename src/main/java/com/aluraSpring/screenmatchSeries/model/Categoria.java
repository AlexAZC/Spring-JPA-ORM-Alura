package com.aluraSpring.screenmatchSeries.model;

public enum Categoria {

    ACCION("Action", "Accion"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy", "Comedia"),
    CRIMEN("Crime","Crimen"),
    DRAMA("Drama","Dramatica");

    private String categoriaOmdb;
    private String categoriaEspanol;


    Categoria(String categoriaOmdb, String categoriaEspanol){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    /*
       * Cada ENUM es representado como un metodo, ya sea para
            usar sus atributos que existe por detras de cada
            ENUM
       * Se utiliza los atributos dentro de las clases enum
            casi siempre para crear constructores como lo es
            el string de categoriaOmdb de arriba
       * Categoria.values() = devuelve un array que contiene
            todos los enum [ACCION, ROMANCE, COMEDIA,ETC [
    */



    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    public static Categoria fromEspanol(String text) {
        for (Categoria categoria : Categoria.values()) {
            if(categoria.categoriaEspanol.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    /*
    * En el metodo estatico fromString queremos pasar un argumento
    * para que compare los constructores ENUM de Arriba y el que
    * se asemeje el argumento text se devolver respectivo ENUM
    * */

    public String getCategoriaOmdb() {
        return categoriaOmdb;
    }




}

