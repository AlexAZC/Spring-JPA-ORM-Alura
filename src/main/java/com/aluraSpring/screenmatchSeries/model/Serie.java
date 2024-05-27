package com.aluraSpring.screenmatchSeries.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;


/*La anotacion ENTITY ES UNA INDICACION PARA HACERLE SABER A
 SPRING QUE DEBE SER GUARDADA EN UNA BASE DE DATOS*/
@Entity
@Table(name = "series") /* Luego de la anotación ENTITY,
se utiliza la anotación TABLE continuamente, ya sea para
cambiar el nombre o para especificar cierta caracteristicas
de dicha tabla que vamos a crear */

public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    /* Arriba de nuestro atributo Long ID creamos una anotacion
    @ID de jakarta, para hacerle saber a nuestra tabla de SQL
    que es nuestra Primary Key(PK) aumentando con cada valor
    que agreguemos con la anotacion de GENERATEDVALUE*/

    @Column(unique = true)//Pusimos un indicador Column
    private String titulo; // Al atributo titulo para que sea unico
    private Integer totalDeTemporadas;
    private Double evaluacion;
    private String fechaDeLanzamiento;

    @Enumerated(EnumType.STRING) //El indicador Enumerated sirve
    private Categoria genero;//para tener casillas predefinidad de nuestra clase ENUM
    private String sinopsis;
    private String poster;
    private String actores;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //Sirve para especificar que
    private List<Episodio> episodios;// todos estos episodios se
                                    // relacionan a una serie especifica

    public Serie(){}


    /* CONSTRUCTOR PASANDO LOS DATOS-SERIE QUE OBTUVIMOS
        AL CONVERTIRLO EN NUESTRO RECORD*/
    public Serie(DatosSerie datosSerie) {
        this.titulo = datosSerie.titulo();
        this.totalDeTemporadas = datosSerie.totalDeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
        this.fechaDeLanzamiento = datosSerie.fechaDeLanzamiento();
        this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
        this.poster = datosSerie.poster();
        this.actores = datosSerie.actores();
        this.sinopsis = datosSerie.sinopsis();
    }



// GETTERS Y SETTERS

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

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(String fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }


    // TOSTRING
    @Override
    public String toString() {
        return
                "titulo='" + titulo + '\'' +
                ", totalDeTemporadas=" + totalDeTemporadas +
                ", evaluacion=" + evaluacion +
                ", fechaDeLanzamiento='" + fechaDeLanzamiento + '\'' +
                ", genero=" + genero +
                ", sinopsis='" + sinopsis + '\'' +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", episodios='" + episodios + '\''
                ;
    }
}
