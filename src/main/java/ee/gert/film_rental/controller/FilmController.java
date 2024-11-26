package ee.gert.film_rental.controller;

import ee.gert.film_rental.entity.Film;
import ee.gert.film_rental.entity.FilmType;
import ee.gert.film_rental.repository.FilmRepository;
import ee.gert.film_rental.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class FilmController {

    @Autowired
    FilmService filmService;

    @GetMapping("films")
    public List<Film> getFilms(){
        return filmService.getFilms();
    }

    @PostMapping("film")
    public List<Film> addFilm(@RequestBody Film film){
        return filmService.addFilm(film);
    }

    @DeleteMapping("film/{id}")
    public List<Film> addFilm(@PathVariable Long id){
        return filmService.deleteFilm(id);
    }

    @PatchMapping("films")
    public List<Film> changeFilmType(@RequestParam Long id, @RequestParam FilmType newType){
        return filmService.changeFilmType(id, newType);
    }

    @GetMapping("available-films")
    public List<Film> getAvailableFilms(){
        return filmService.getAvailableFilms();
    }

}
