package com.aluraSpring.screenmatchSeries.principal;

import com.aluraSpring.screenmatchSeries.model.*;
import com.aluraSpring.screenmatchSeries.repositorio.SerieRepository;
import com.aluraSpring.screenmatchSeries.service.ConsumoAPI;
import com.aluraSpring.screenmatchSeries.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b43e337b";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;


    //Utilizacion de inyecciones de dependencias construcora
    public Principal(SerieRepository repository){
        this.repositorio = repository;
    }


    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Top 5 mejores series
                    6 - Buscar series por categoria
                    7 - Buscar series por puntaje 
                    8 - Buscar series por un total de temporadas
                    9 - Buscar serie por puntaje y temporadas
                    10 - Buscar episodios por titulo
                    11 - Top 5 episodios por Serie
                                       
                    0 - Salir 
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    buscarSeriesPorEvaluacion();
                    break;
                case 8:
                    buscarSeriesPorTemporadas();
                    break;
                case 9:
                    buscarSeriesPorTemporadasYEvaluacion();
                    break;
                case 10:
                    buscarEpisodiosPorTitulo();
                    break;
                case 11:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opción invalida");

            }
        }
    }


    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }


    private void buscarEpisodioPorSerie(){
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie que desee ver sus episodios");
        var nombreSerie = teclado.nextLine();


        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ","+") + "&Season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(),e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
    }


    private void buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        //Utilizacion de los metodos que trae el repositorio
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);

    }


    private void mostrarSeriesBuscadas() {
         series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo(){
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
         serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if(serieBuscada.isPresent()){
            System.out.println("La serie buscada es: " + serieBuscada.get());
        } else {
            System.out.println("Serie no encontrada");
        }
    }


    private void  buscarTop5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s ->
                System.out.println("Serie: " + s.getTitulo() + ", Evaluacion: " + s.getEvaluacion()));
    }


    private void buscarSeriesPorCategoria(){
        System.out.println("Escriba el genero/categoria que desea buscar");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series con la categoria " + genero + " son:");
        seriesPorCategoria.forEach(s -> System.out.println(s.getTitulo()));
    }


    private void buscarSeriesPorEvaluacion(){
        System.out.println("Escriba el filtro de puntaje para nuestras series");
        var puntaje = teclado.nextDouble();
        List<Serie> filtroPuntajeSeries = repositorio.findByEvaluacionGreaterThanEqual(puntaje);
        filtroPuntajeSeries.forEach(s -> System.out.println("Serie: " + s.getTitulo() + ", Con una evaluación de " + s.getEvaluacion()));
    }


    private void buscarSeriesPorTemporadas(){
        System.out.println("Filtre las series por un numero de temporadas");
        var numeroTemporada = teclado.nextInt();
        List<Serie> filtroPorTemporadas = repositorio.findByTotalDeTemporadasLessThanEqual(numeroTemporada);
        filtroPorTemporadas.forEach(s -> System.out.println("Serie: " + s.getTitulo() + ", temporadas: " + s.getTotalDeTemporadas()));
    }


    private void buscarSeriesPorTemporadasYEvaluacion(){
        System.out.println("Filtrar series por temporadas");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();
        System.out.println("Con una evaluación de: ");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);
        filtroSeries.forEach(s -> System.out.println(s.getTitulo() + ", " + s.getTotalDeTemporadas() + ", " + s.getEvaluacion()));
    }


    private void buscarEpisodiosPorTitulo(){
        System.out.println("Escribe el nombre del episodio que deseas buscar");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Serie: %s Temporada %s Episodio %s Evaluación %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }


    private void buscarTop5Episodios(){
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e ->
            System.out.printf("Serie: %s Temporada %s Episodio %s Evaluación %s\n",
                    e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
        }
    }
























} // Acaba la clase Principal
