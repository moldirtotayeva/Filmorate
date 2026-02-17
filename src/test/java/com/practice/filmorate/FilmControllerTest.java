//package com.practice.filmorate;
//
//import com.practice.filmorate.controller.FilmController;
//import com.practice.filmorate.exceptions.ValidationException;
//import com.practice.filmorate.model.Film;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import java.time.LocalDate;
//import java.util.stream.Stream;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class FilmControllerTest {
//    FilmController filmController;
//
//    @BeforeEach
//    public void setup() {
//        filmController = new FilmController();
//    }
//
//    @Test
//    public void createFilmSuccess() {
//        Film film = new Film("film1", "kakoe-to opisanie", LocalDate.of(2010,04, 02), 196);
//        filmController.create(film);
//        assertEquals(1, filmController.findAll().size());
//    }
//
//    @ParameterizedTest
//    @MethodSource("filmProvider")
//    public void createFilmFail(Film film, String expectedMessage) {
//        ValidationException ex = assertThrows(ValidationException.class, () -> filmController.create(film));
//        assertEquals(expectedMessage, ex.getMessage());
//    }
//    static Stream<Arguments> filmProvider() {
//        return Stream.of(
//                Arguments.of(new Film("", "kakoe-to opisanie", LocalDate.of(2010, 04, 02), 196),
//                        "Название фильма не может быть пустым"),
//                Arguments.of(new Film(null, "kakoe-to opisanie", LocalDate.of(2010, 04, 02), 196),
//                        "Название фильма не может быть пустым"),
//                Arguments.of(new Film("film2", "kakoe-to opisanie", LocalDate.of(1890, 04, 02), 196),
//                        "Дата релиза фильма не должна быть раньше 28 декабря 1895 года"),
//                Arguments.of(new Film("film3", "!".repeat(201), LocalDate.of(2010, 04, 02), 196),
//                        "Максимальная длина описания — 200 символов"),
//                Arguments.of(new Film("film2", "kakoe-to opisanie", LocalDate.of(1990, 01, 15), -168),
//                        "Продолжительность фильма должна быть положительной")
//        );
//    }
//
//    @Test
//    public void shouldPassWhenDescriptionLengthIs200(){
//        Film film = new Film("film3", "!".repeat(200), LocalDate.of(2010, 04, 02), 196);
//        filmController.create(film);
//        assertEquals(true, filmController.getFilms().containsKey(film.getId()));
//    }
//
//    @Test
//    public void updateSuccess() {
//        Film film = new Film("Властелин колец", "opisanie", LocalDate.of(2001, 04, 02), 196);
//        filmController.create(film);
//        Film updatedFilm = new Film("Властелин колец. Братство кольца", "kakoe-to opisanie", LocalDate.of(2013, 04, 02), 196);
//        updatedFilm.setId(film.getId());
//        filmController.update(updatedFilm);
//        assertEquals(filmController.getFilms().get(film.getId()).getName(), updatedFilm.getName());
//    }
//}
