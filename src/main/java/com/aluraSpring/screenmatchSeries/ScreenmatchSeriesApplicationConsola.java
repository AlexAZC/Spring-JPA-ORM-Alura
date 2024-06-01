//package com.aluraSpring.screenmatchSeries;
//
//import com.aluraSpring.screenmatchSeries.principal.Principal;
//import com.aluraSpring.screenmatchSeries.repositorio.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchSeriesApplicationConsola implements CommandLineRunner {
//
///* Aqui utilizamos las inyecciones de dependecias con
//* constructores, importando la clase SerieRepository y pasandola
//* como parametro en la clase Principal para que pueda utilizar
//* sus respectivos metodos
//* */
//
//	@Autowired
//	private SerieRepository repository;
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchSeriesApplicationConsola.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//
//		Principal principal = new Principal(repository);
//		principal.muestraElMenu();
//
//	}
//}
