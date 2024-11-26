package ee.gert.film_rental.controller;

import ee.gert.film_rental.entity.Rental;
import ee.gert.film_rental.repository.FilmRepository;
import ee.gert.film_rental.repository.RentalRepository;
import ee.gert.film_rental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentalController {

    @Autowired
    RentalService rentalService;

//    int bonusPoints;

    @GetMapping("get-points")
    public int getBonusPoints(){
        return rentalService.getBonusPoints();
    }

    @PostMapping("start-rent")
    public Rental startRental(@RequestBody Rental rental){
        return rentalService.startRental(rental);
    }

    @PutMapping("end-rent")
    public Rental endFilmRental(@RequestParam Long filmId, int rentedDays,  Long rentalId){
        return rentalService.endFilmREntal(rentalId ,filmId, rentedDays);
    }

    @GetMapping("rentals")
    public List<Rental> getRentals(){
        return rentalService.getAllRentals();
    }
}
