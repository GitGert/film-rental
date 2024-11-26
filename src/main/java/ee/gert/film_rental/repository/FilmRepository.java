package ee.gert.film_rental.repository;

import ee.gert.film_rental.entity.Film;
import ee.gert.film_rental.entity.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findByAvailableOrderByIdAsc(boolean available);

    List<Film> findByOrderByIdAsc();
}
