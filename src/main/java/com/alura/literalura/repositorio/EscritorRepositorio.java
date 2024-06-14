package com.alura.literalura.repositorio;

import com.alura.literalura.modelo.Escritor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface EscritorRepositorio extends JpaRepository<Escritor, Long> {
    Optional<Escritor> findByNombre(String nombre);

    @Query("SELECT e FROM Escritor e WHERE e.fecha_nacimiento <= :anio AND e.fecha_deceso >= :anio")
    List<Escritor> listaEscritoresVivosPorAnio(Integer anio);
}