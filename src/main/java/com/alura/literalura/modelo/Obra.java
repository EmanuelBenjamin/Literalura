package com.alura.literalura.modelo;


import jakarta.persistence.*;


@Entity
@Table(name = "obras")
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    private Escritor escritor;
    @Enumerated(EnumType.STRING)
    private Idiomas lenguaje;
    private Integer numero_descargas;

    public Obra(){}

    public Obra(DatosObra datosObra){
        this.titulo = datosObra.titulo();
        this.lenguaje = Idiomas.fromString(datosObra.idiomas().toString().split(",")[0].trim());
        this.numero_descargas = datosObra.numero_descargas();
    }

    @Override
    public String toString() {
        String nombreAutor = (escritor != null) ? escritor.getNombre() : "Autor desconocido";
        return String.format("---------- Obra ----------%nTitulo:" +
                " %s%nEscritor: %s%nIdioma: %s%nNumero de Descargar:" +
                " %d%n---------------------------%n",titulo,nombreAutor,lenguaje,numero_descargas);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Escritor getEscritor() {
        return escritor;
    }

    public void setEscritor(Escritor escritor) {
        this.escritor = escritor;
    }

    public Idiomas getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Idiomas lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumero_descargas() {
        return numero_descargas;
    }

    public void setNumero_descargas(Integer numero_descargas) {
        this.numero_descargas = numero_descargas;
    }
}