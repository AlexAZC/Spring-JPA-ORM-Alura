package com.aluraSpring.screenmatchSeries;

import com.aluraSpring.screenmatchSeries.principal.Principal;
import com.aluraSpring.screenmatchSeries.repositorio.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchSeriesApplication {

	@Autowired
	private SerieRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchSeriesApplication.class, args);
	}
}

