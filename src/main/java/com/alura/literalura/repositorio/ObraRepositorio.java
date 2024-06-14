package com.alura.literalura.repositorio;

import com.alura.literalura.modelo.Idiomas;
import com.alura.literalura.modelo.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ObraRepositorio extends JpaRepository<Obra, Long> {
    List<Obra> findByLenguaje(Idiomas idioma);

    Optional<Obra> findByTitulo(String titulo);

    @Query("SELECT o FROM Obra o ORDER BY o.numero_descargas DESC LIMIT 10")
    List<Obra> top10ObrasMasDescargadas();
}