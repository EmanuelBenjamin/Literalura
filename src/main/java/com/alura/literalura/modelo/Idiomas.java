package com.alura.literalura.modelo;

public enum Idiomas {
    en("[en]", "Ingles"),
    es("[es]", "Español"),
    fr("[fr]", "Frances"),
    pt("[pt]", "Portugues");

    private String idiomaGutendex;
    private String idiomaEspanol;

    Idiomas(String idiomaGutendex, String idiomaEspanol){
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaEspanol = idiomaEspanol;

    }

    public static Idiomas fromString(String text){
        for (Idiomas idioma : Idiomas.values()){
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("El idioma no fue  encontrado: " + text);
    }

    public static Idiomas fromEspanol(String text){
        for (Idiomas idioma : Idiomas.values()){
            if (idioma.idiomaEspanol.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    public String getIdiomaGutendex() {
        return idiomaGutendex;
    }

    public String getIdiomaEspanol() {
        return idiomaEspanol;
    }

}