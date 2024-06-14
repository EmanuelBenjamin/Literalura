package com.alura.literalura.principal;

import com.alura.literalura.modelo.*;
import com.alura.literalura.repositorio.EscritorRepositorio;
import com.alura.literalura.repositorio.ObraRepositorio;
import com.alura.literalura.servicio.ConsumeAPI;
import com.alura.literalura.servicio.ConvierteData;


import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumeAPI consumoApi = new ConsumeAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteData conversor = new ConvierteData();
    private ObraRepositorio repositoryObra;
    private EscritorRepositorio repositoryEscritor;
    private List<Escritor> escritores;
    private List<Obra> obras;

    public Principal(ObraRepositorio repositoryObra, EscritorRepositorio repositoryEscritor) {
        this.repositoryObra = repositoryObra;
        this.repositoryEscritor = repositoryEscritor;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("\033[34m\n======= Biblioteca Virtual ========\033[0m");
            System.out.println("\033[32m\nOperaciones con obras:" +
                    "\n > 1.- Buscar obras por título" +
                    "\n ★ 2.- Mostrar obras registradas" +
                    "\n🌐 3.- Buscar obras por idioma" +
                    "\n🔝 4.- Top 10 obras más descargadas" +
                    "\n📈 5.- Obra más descargada y menos descargada" +
                    "\n\nOperaciones con escritores:" +
                    "\n✎ 6.- Mostrar escritores registrados" +
                    "\n⌛7.- Escritores vivos en un año determinado" +
                    "\n\n < 0.- Salir\033[0m");

            while (!teclado.hasNextInt()) {
                System.out.println("Formato inválido, ingrese un número que este disponible en el menú!");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    buscarObra();
                    break;
                case 2:
                    mostrarObras();
                    break;
                case 6:
                    mostrarEscritores();
                    break;
                case 7:
                    escritoresVivosPorAnio();
                    break;
                case 3:
                    buscarObraPorIdioma();
                    break;
                case 4:
                    top10ObrasMasDescargadas();
                    break;
                case 5:
                    rankingObra();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicación");
                    break;
                default:
                    System.out.printf("Opción inválida\n");
            }
        }
    }

    private DatosBusqueda getBusqueda() {
        System.out.println("Escribe el nombre de la obra: ");
        var nombreObra = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreObra.replace(" ", "%20"));
        DatosBusqueda datos = conversor.obtenerDatos(json, DatosBusqueda.class);
        return datos;
    }

    private void buscarObra() {
        DatosBusqueda datosBusqueda = getBusqueda();
        if (datosBusqueda != null && !datosBusqueda.resultado().isEmpty()) {
            DatosObra primeraObra = datosBusqueda.resultado().get(0);

            Obra obra = new Obra(primeraObra);
            System.out.println("----- Obra -----");
            System.out.println(obra);
            System.out.println("-----------------");

            Optional<Obra> obraExiste = repositoryObra.findByTitulo(obra.getTitulo());
            if (obraExiste.isPresent()) {
                System.out.println("\nLa obra ya está registrada\n");
            } else {
                if (!primeraObra.escritor().isEmpty()) {
                    DatosEscritor escritor = primeraObra.escritor().get(0);
                    Escritor escritor1 = new Escritor(escritor);
                    Optional<Escritor> escritorOptional = repositoryEscritor.findByNombre(escritor1.getNombre());

                    if (escritorOptional.isPresent()) {
                        Escritor escritorExiste = escritorOptional.get();
                        obra.setEscritor(escritorExiste);
                        repositoryObra.save(obra);
                    } else {
                        Escritor escritorNuevo = repositoryEscritor.save(escritor1);
                        obra.setEscritor(escritorNuevo);
                        repositoryObra.save(obra);
                    }

                    Integer numeroDescargas = obra.getNumero_descargas() != null ? obra.getNumero_descargas() : 0;
                    System.out.println("---------- Obra ----------");
                    System.out.printf("Título: %s%nEscritor: %s%nIdioma: %s%nNúmero de Descargas: %s%n", obra.getTitulo(), escritor1.getNombre(), obra.getLenguaje(), obra.getNumero_descargas());
                    System.out.println("---------------------------\n");
                } else {
                    System.out.println("Sin escritor");
                }
            }
        } else {
            System.out.println("Obra no encontrada");
        }
    }

    private void mostrarObras() {
        obras = repositoryObra.findAll();
        obras.stream()
                .forEach(System.out::println);
    }

    private void mostrarEscritores() {
        escritores = repositoryEscritor.findAll();
        escritores.stream()
                .forEach(System.out::println);
    }

    private void escritoresVivosPorAnio() {
        System.out.println("Ingresa el año, de escritor(es) vivos, que deseas buscar: ");
        var anio = teclado.nextInt();
        escritores = repositoryEscritor.listaEscritoresVivosPorAnio(anio);
        escritores.stream()
                .forEach(System.out::println);
    }

    private List<Obra> datosBusquedaLenguaje(String idioma) {
        var dato = Idiomas.fromString(idioma);
        System.out.println("Lenguaje buscado: " + dato);

        List<Obra> obraPorIdioma = repositoryObra.findByLenguaje(dato);
        return obraPorIdioma;
    }

    private void buscarObraPorIdioma() {
        System.out.println("Selecciona el lenguaje/idioma que deseas buscar: ");

        var opcion = -1;
        while (opcion != 0) {
            var opciones = """
                    1. en - Inglés
                    2. es - Español
                    3. fr - Francés
                    4. pt - Portugués

                    0. Volver a las opciones anteriores
                    """;
            System.out.println(opciones);
            while (!teclado.hasNextInt()) {
                System.out.println("Formato inválido, ingrese un número que esté disponible en el menú");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    List<Obra> obrasEnIngles = datosBusquedaLenguaje("[en]");
                    obrasEnIngles.forEach(System.out::println);
                    break;
                case 2:
                    List<Obra> obrasEnEspanol = datosBusquedaLenguaje("[es]");
                    obrasEnEspanol.forEach(System.out::println);
                    break;
                case 3:
                    List<Obra> obrasEnFrances = datosBusquedaLenguaje("[fr]");
                    obrasEnFrances.forEach(System.out::println);
                    break;
                case 4:
                    List<Obra> obrasEnPortugues = datosBusquedaLenguaje("[pt]");
                    obrasEnPortugues.forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ningún idioma seleccionado");
            }
        }
    }

    private void top10ObrasMasDescargadas() {
        List<Obra> topObras = repositoryObra.top10ObrasMasDescargadas();
        topObras.forEach(System.out::println);
    }

    private void rankingObra() {
        obras = repositoryObra.findAll();
        IntSummaryStatistics est = obras.stream()
                .filter(o -> o.getNumero_descargas() > 0)
                .collect(Collectors.summarizingInt(Obra::getNumero_descargas));

        Obra obraMasDescargada = obras.stream()
                .filter(o -> o.getNumero_descargas() == est.getMax())
                .findFirst()
                .orElse(null);

        Obra obraMenosDescargada = obras.stream()
                .filter(o -> o.getNumero_descargas() == est.getMin())
                .findFirst()
                .orElse(null);
        System.out.println("------------------------------------------------------");
        System.out.printf("%nObra más descargada: %s%nNúmero de descargas: %d%n%nObra menos descargada: %s%nNúmero de descargas: %d%n%n", obraMasDescargada.getTitulo(), est.getMax(), obraMenosDescargada.getTitulo(), est.getMin());
        System.out.println("------------------------------------------------------");
    }
}