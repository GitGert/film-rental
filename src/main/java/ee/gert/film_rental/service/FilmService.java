package ee.gert.film_rental.service;

import ee.gert.film_rental.entity.Film;
import ee.gert.film_rental.entity.FilmType;
import ee.gert.film_rental.repository.FilmRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class FilmService {
    @Autowired
    FilmRepository filmRepository;

    public List<Film> getFilms() {
        //TODO: System.out.println
        return filmRepository.findByOrderByIdAsc();
    }

    public List<Film> addFilm(Film film) {
        film.setRentedDays(0);
        film.setInitialDays(0);
        film.setAvailable(true);
        filmRepository.save(film);
        return filmRepository.findByOrderByIdAsc();
    }

    public List<Film> deleteFilm(Long id) {
        filmRepository.deleteById(id);
        return filmRepository.findByOrderByIdAsc();
    }

    public List<Film> changeFilmType(Long id, FilmType newType) {
        //TODO: change film type
        Film film = filmRepository.findById(id).orElseThrow();
        film.setType(newType);
        filmRepository.save(film);
        return filmRepository.findByOrderByIdAsc();
    }

    public List<Film> getAvailableFilms() {
        return filmRepository.findByAvailableOrderByIdAsc(true);
    }
}
