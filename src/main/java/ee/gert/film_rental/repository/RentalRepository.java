package ee.gert.film_rental.repository;

import ee.gert.film_rental.entity.Film;
import ee.gert.film_rental.entity.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    Rental findByFilms_Id(Long id);

    Rental findByFilms(Film films);

}
