package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repositorio.EscritorRepositorio;
import com.alura.literalura.repositorio.ObraRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {
	@Autowired
	private ObraRepositorio  repositoryObra;

	@Autowired
	private EscritorRepositorio repositoryEscritor;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryObra, repositoryEscritor);
		principal.muestraElMenu();

	}
}