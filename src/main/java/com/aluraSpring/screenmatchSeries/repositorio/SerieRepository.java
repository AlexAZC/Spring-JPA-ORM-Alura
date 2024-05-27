package com.aluraSpring.screenmatchSeries.repositorio;

import com.aluraSpring.screenmatchSeries.model.Categoria;
import com.aluraSpring.screenmatchSeries.model.Episodio;
import com.aluraSpring.screenmatchSeries.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/*
* Se utiliza un repositorio proveniente de Spring llamado
* JpaRepository para realizar operaciones CRUD en nuestra
* base de datos, para ello creamos una interfaz que extiende
* de ese repositorio:
*
* En sus argumentos del repositio de coloca la ENTITY, que
* seria nuestra tabla de la clase SERIE y el tipo de nuestro
* ID que colocamos Long en la clase SERIE
*/

public interface SerieRepository extends JpaRepository<Serie,Long> {

   /* APLICANDO JPA QUERY METHODS DE SPRING */
   Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

   List<Serie> findTop5ByOrderByEvaluacionDesc();

   List<Serie> findByGenero(Categoria categoria);

   List<Serie> findByEvaluacionGreaterThanEqual(Double puntaje);

   List<Serie> findByTotalDeTemporadasLessThanEqual(Integer numeroDeTemporadas);


   /* APLICANDO UNA QUERY SQL-NATIVA */
   /* Utilizando SQL-Nativa simplemente tendremos que escribir
   * lo que deseamos filtrar o buscar con el lenguage de
   * programacion SQL.
   * Tiene mejor rendimiento
   * No puede utilizar parametros */
   //@Query(value = "SELECT * FROM Series WHERE series.total_de_temporadas <= 6 AND series.evaluacion >= 9", nativeQuery = true)
   //List<Serie> seriesPorTemporadaYEvaluacion(Integer numeroDeTemporadas, Double puntaje);


   /* APLICANDO JPQL LENGUAGE DE QUERYS CON JAVA */
   /* Al utilizar JPQL debemos escribir con codigo de SQL
   * Pero con ciertas asignaturas como en ves de tipear
   * el signo (*) ponemos una abreviacion que en este caso
   * es (s), luego del FROM Serie, ponemos la abreviatura
   * para que JPQL sepa que es la clase Serie y para poder
   * asignar parametros a cierta funcion se debe escribir el
   * signo (:) con el mismo nombre que declaramos a la funcion */
   @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :numeroDeTemporadas AND s.evaluacion >= :puntaje")
   List<Serie> seriesPorTemporadaYEvaluacion(Integer numeroDeTemporadas, Double puntaje);

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio")
   List<Episodio> episodiosPorNombre(String nombreEpisodio);

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
   List<Episodio> top5Episodios(Serie serie);

}
