//package com.practice.filmorate;
//
//import com.practice.filmorate.controller.UserController;
//import com.practice.filmorate.exceptions.ValidationException;
//import com.practice.filmorate.model.User;
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
//public class UserControllerTest {
//    UserController userController;
//
////    @BeforeEach
////    public void setUp(){
////        userController = new UserController();
////    }
////
////    @Test
////    public void createUserSuccess(){
////        User user = new User("molly@gmail.com", "moly", "Moldir", LocalDate.of(1988, 04, 18));
////        userController.createUser(user);
////        assertEquals(1, userController.findAll().size());
////    }
////
////    @Test
////    public void shouldSetLoginWhenUserNameIsNull(){
////        User user = new User("molly@gmail.com", "moly", null, LocalDate.of(1988, 04, 18));
////        userController.createUser(user);
////        assertEquals(user.getLogin(), user.getName());
////    }
////
////    @Test
////    public void shouldSetLoginWhenUserNameIsEmpty(){
////        User user = new User("molly@gmail.com", "moly", "", LocalDate.of(1988, 04, 18));
////        userController.createUser(user);
////        assertEquals(user.getLogin(), user.getName());
////    }
////
////    @ParameterizedTest
////    @MethodSource("userProvider")
////    public void createUserFail(User user, String expectedMessage){
////
////        ValidationException ex = assertThrows(ValidationException.class, () -> userController.createUser(user));
////        assertEquals(expectedMessage, ex.getMessage());
////    }
////
////    static Stream<Arguments> userProvider() {
////        return Stream.of(
////                Arguments.of(new User("mollygmail.com", "moly", "Moldir", LocalDate.of(1988, 04, 18)),
////                        "Электронная почта не может быть пустой и должна содержать символ @"),
////                Arguments.of(new User( "", "moly", "Moldir", LocalDate.of(1988, 04, 18)),
////                        "Электронная почта не может быть пустой и должна содержать символ @"),
////                Arguments.of(new User(null, "moly", "Moldir", LocalDate.of(1988, 04, 18)),
////                        "Электронная почта не может быть пустой и должна содержать символ @"),
////                Arguments.of(new User("molly@gmail.com", "", "Moldir", LocalDate.of(1988, 04, 18)),
////                        "Логин не может быть пустым и содержать пробелы"),
////                Arguments.of(new User("molly@gmail.com", "moly", "Moldir", LocalDate.of(2028, 04, 18)),
////                        "Дата рождения не может быть в будущем")
////        );
////    }
////
////    @Test
////    public void updateUserSuccess(){
////        User user = new User("molly@gmail.com", "moly", "Moldir", LocalDate.of(1988, 04, 18));
////        userController.createUser(user);
////        User updatedUser = new User("molly-polly@gmail.com", "moly", "Moldir", LocalDate.of(1988, 04, 18));
////        updatedUser.setId(user.getId());
////        userController.updateUser(updatedUser);
////        assertEquals(updatedUser.getEmail(), userController.getUsers().get(user.getId()).getEmail());
////    }
//}
