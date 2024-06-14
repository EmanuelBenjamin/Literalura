package com.alura.literalura.modelo;


import jakarta.persistence.*;

import java.util.List;



@Entity
@Table(name = "escritores")
public class Escritor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fecha_nacimiento;
    private Integer fecha_deceso;
    @OneToMany(mappedBy = "escritor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Obra> obra;

    public Escritor(){}

    public Escritor(DatosEscritor datosEscritor){
        this.nombre=datosEscritor.nombre();
        this.fecha_nacimiento = datosEscritor.fechaNacimiento();
        this.fecha_deceso = datosEscritor.fechaFallecimiento();
    }

    @Override
    public String toString() {
        StringBuilder librosStr = new StringBuilder();
        librosStr.append("obras: ");

        for(int i = 0; i < obra.size() ; i++) {
            librosStr.append(obra.get(i).getTitulo());
            if (i < obra.size() - 1 ){
                librosStr.append(", ");
            }
        }
        return String.format("---------- Escritor ----------%nNombre:" +
                " %s%n%s%nFecha de Nacimiento: %s%nFecha de Deceso:" +
                " %s%n---------------------------%n",nombre,librosStr.toString(),fecha_nacimiento,fecha_deceso);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Integer fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Integer getFecha_deceso() {
        return fecha_deceso;
    }

    public void setFecha_deceso(Integer fecha_deceso) {
        this.fecha_deceso = fecha_deceso;
    }

    public List<Obra> getObra() {
        return obra;
    }

    public void setObra(List<Obra> obra) {
        this.obra = obra;
    }
}