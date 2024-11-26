package ee.gert.film_rental.service;

import ee.gert.film_rental.entity.Film;
import ee.gert.film_rental.entity.FilmType;
import ee.gert.film_rental.entity.Rental;
import ee.gert.film_rental.repository.FilmRepository;
import ee.gert.film_rental.repository.RentalRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Getter
@Service
public class RentalService {
    double premiumPrice = 4;
    double basicPrice = 3;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Getter
     int bonusPoints; //todo mby doesn't need to be public

    public Rental startRental(Rental rental) {
        rental.setLateFee(0);
        double sum = 0;
        for (Film film : rental.getFilms()){
            Film dbFilm = filmRepository.findById(film.getId()).orElseThrow();
            dbFilm.setRentedDays(0);
            dbFilm.setAvailable(false);
            dbFilm.setInitialDays(film.getInitialDays());
            double filmPrice = calculateSum(dbFilm);
            sum += filmPrice;
            filmRepository.save(dbFilm);
            System.out.printf("%s (%s) %d days %.2f",dbFilm.getName(), getTypeName(dbFilm.getType()), dbFilm.getInitialDays(), filmPrice);
            System.out.println();
        }

        rental.setSum(sum);
        System.out.println("Total Price " + sum + "EUR");
        return rentalRepository.save(rental);
    }

    private String getTypeName(FilmType filmType){

        return switch (filmType){
            case NEW -> "New release";
            case REGULAR -> "Regular rental";
            case OLD -> "Old film";
        };
    }


    public Rental endFilmREntal(Long rentalId, Long filmId, int rentedDays) {
        Film film = filmRepository.findById(filmId).orElseThrow();

        Rental rental = rentalRepository.findById(rentalId).orElseThrow();

        if (film.isAvailable()){
            return rental;
        }

        film.setRentedDays(rentedDays);
        double lateFee = rental.getLateFee();
        double additionLateFee = calculatePenalty(film);
        rental.setLateFee(lateFee + additionLateFee);

        if(film.getType() == FilmType.NEW){
            this.bonusPoints += 2;
        }else {
            this.bonusPoints +=1;
        }

        film.setAvailable(true);
        film.setRentedDays(0);
        film.setInitialDays(0);
//        filmRepository.save(film);// FIXME: do I need this?
        return rentalRepository.save(rental);
    }


    private double calculateSum(Film film){
        if (film.getType().equals(FilmType.NEW)){
            return film.getInitialDays()*premiumPrice;
        }else if(film.getType().equals(FilmType.REGULAR)){
            if (film.getInitialDays() <= 3){
                return basicPrice;
            }
            return basicPrice + (film.getInitialDays()-3) * basicPrice;
        }else if(film.getType().equals(FilmType.OLD)){
            if (film.getInitialDays() <= 5){
                return basicPrice;
            }
            return basicPrice + (film.getInitialDays()-5) * basicPrice;
        }else{
            return 0;
        }
    }

    private double calculatePenalty(Film film){
        int lateDays = film.getRentedDays()-film.getInitialDays();
        if (lateDays <=0){
            return 0;
        }
        if (film.getType().equals(FilmType.NEW)){

            return lateDays*premiumPrice;
        }else if(film.getType().equals(FilmType.REGULAR) || film.getType().equals(FilmType.OLD)) {
            return lateDays * basicPrice;
        }else{
            return 0;
        }
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
}
