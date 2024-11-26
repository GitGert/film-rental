package ee.gert.film_rental;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.assertEquals;


import ee.gert.film_rental.entity.Film;
import ee.gert.film_rental.entity.FilmType;
import ee.gert.film_rental.entity.Rental;
import ee.gert.film_rental.repository.FilmRepository;
import ee.gert.film_rental.repository.RentalRepository;
import ee.gert.film_rental.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


//import static org.junit.Assert.*;

@SpringBootTest
public class FilmRentalApplicationTests {

//	public static void main(String[] args) {
//		System.out.println("Hello, World!");
//	}

	@Mock
	FilmRepository filmRepository;

	@Mock
	RentalRepository rentalRepository;

	@InjectMocks
	RentalService rentalService;

	Rental insertedRental = new Rental();
//	Film film1 = new Film(1L, "Avatar", FilmType.OLD, 5, 0, true);
	Film film1 = new Film();
	Film film2 = new Film();

	//	{
//		"id": 1,
//			"name": "fake_film",
//			"type": "REGULAR",
//			"initialDays": 5,
//			"rentedDays": 0,
//			"available": true
//	},

	@Test
	void contextLoads() {
		MockitoAnnotations.openMocks(this);

	}

	@BeforeEach
	void beforeEach(){

		insertedRental.setId(1L);

		film1.setId(1L);
		film1.setName("Avatar");
		film1.setType(FilmType.OLD);
		film1.setRentedDays(100);
		film1.setAvailable(true);
		film1.setInitialDays(5);


		film2.setId(2L);
		film2.setName("Smile");
		film2.setType(FilmType.NEW);
		film2.setRentedDays(200);
		film2.setInitialDays(10);

		insertedRental.setSum(99);
		insertedRental.setFilms(Arrays.asList(film1, film2));

		when(filmRepository.findById(1L)).thenReturn(Optional.of(film1)); // jäljendame andmebaasi
		when(filmRepository.findById(2L)).thenReturn(Optional.of(film2));
		when(rentalRepository.save(any())).thenReturn(insertedRental);

	}



	@Test
	void given_whenRentalIsStarted_thenRentedDaysAreZero(){
		System.out.println(film1.getRentedDays());

		Rental rental = rentalService.startRental(insertedRental);

		Film film = rental.getFilms().getFirst();
		System.out.println(film1.getRentedDays());
		assertEquals(0, film.getRentedDays());
	}


	@Test
	void givenFilmOld10daysAndNew5Days_whenRentalIsStarted_thenSumIs(){
		System.out.println(film1.getRentedDays());

		Rental rental = rentalService.startRental(insertedRental);
		assertEquals(43, rental.getSum());
	}
}
