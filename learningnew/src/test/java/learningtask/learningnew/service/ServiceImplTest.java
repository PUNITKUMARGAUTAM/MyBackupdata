package learningtask.learningnew.service;

import static learningtask.learningnew.entity.UserStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import learningtask.learningnew.dto.UpdatePasswordRequest;
import learningtask.learningnew.dto.UserDto;
import learningtask.learningnew.entity.UserEntity;
import learningtask.learningnew.repository.UserRepository;
import learningtask.learningnew.servicve.UserServiceImpl;

@SpringBootTest
@RunWith (MockitoJUnitRunner.class)
public class ServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateData() {
        UserEntity userEntity = getUserDetails();
        when(userRepository.save(userEntity)).thenReturn(any());
        ResponseEntity str1 = userService.createData(userEntity);
        assertEquals(str1.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testCreateDataForException() {

        UserEntity userEntity = getUserDetails();
        when(userRepository.save(userEntity)).thenThrow(new RuntimeException());
        ResponseEntity str1 = userService.createData(userEntity);
        assertEquals(str1.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testFindAllUsers() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(getUserDetails());
        when(userRepository.findAll()).thenReturn(userEntities);
        List<UserEntity> userEntities1 = userService.findAllDetails();
        assertEquals(userEntities1, userEntities);
    }

    @Test
    public void testFindAllUsersException() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(getUserDetails());
        when(userRepository.findAll()).thenThrow(new RuntimeException());
        userService.findAllDetails();
        assertTrue(true);
    }

    @Test
    public void testUpdateById() {
        UserEntity userEntity = getUserDetails();
        when(userRepository.save(userEntity)).thenReturn(any());
        ResponseEntity str2 = userService.updateData(userEntity);
        assertEquals(str2.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateByIdException() {
        UserEntity userEntity = getUserDetails();
        when(userRepository.save(userEntity)).thenThrow(new RuntimeException());
        ResponseEntity str2 = userService.updateData(userEntity);
        assertEquals(str2.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteByIdTest() {
        UserEntity userEntity = getUserDetails();
        userEntity.setId(69L);
        userRepository.deleteById(userEntity.getId());
        userService.deleteData(userEntity.getId());

    }

    @Test
    public void testDeleteDataException() {
        UserEntity userEntity = getUserDetails();
        Long id = 123L;
        Mockito.doThrow(new RuntimeException("Delete error")).when(userRepository).deleteById(id);
        userService.deleteData(id);
        Mockito.verify(userRepository).deleteById(id);
    }

    @Test
    public void getByIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(19L);
        when(userRepository.findById(userEntity.getId())).thenReturn(any());
        userService.getById(userEntity.getId());
    }

    @Test
    public void getByIdTestException() {
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(19L)).thenThrow(new RuntimeException("Database connection error"));
        userService.getById(userEntity.getId());
    }

    @Test
    public void testFindDetailByEmail_LoggedInUser() {

        String userEmail = "test@example.com";
        String password = "password";
        UserEntity loggedInUser = new UserEntity();
        loggedInUser.setIsloggedIn(true);
        //        loggedInUser.setMessage("User Already logged in...");
        Optional<UserEntity> optionalUserEntity = null;
        //        Optional<UserEntity> optionalUserEntity = getUserDetails();
        Mockito.when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenReturn(optionalUserEntity);

        // Act
        UserDto result = userService.findDetailByEmail(userEmail, password);

        // Assert
        Assertions.assertEquals(loggedInUser, result.getMessage());
        Assertions.assertEquals("200", result.getStatus());
    }

    @Test
    public void testFindDetailByEmail_SuccessfulLogin() {
        // Arrange
        String userEmail = "mohit123@gmail.com";
        String password = "punit@123";
        UserEntity userEntity = new UserEntity();
        userEntity.setIsloggedIn(false);

        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        Mockito.when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenReturn(optionalUserEntity);
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Act
        UserDto result = userService.findDetailByEmail(userEmail, password);

        // Assert
        Assertions.assertEquals("User Successfully logged in...", result.getMessage());
        Assertions.assertEquals("200", result.getStatus());
    }

    @Test
    public void testFindDetailByEmail_UserNotFound() {
        // Arrange
        String userEmail = "manmohan123@gmail.com";
        String password = "mn@1234";
        Optional<UserEntity> optionalUserEntity = Optional.empty();
        Mockito.when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenReturn(optionalUserEntity);

        // Assert
        assertThrows(ResponseStatusException.class, () -> {
            // Act
            userService.findDetailByEmail(userEmail, password);
        });
    }

    @Test
    public void testFindDetailByEmail_InternalServerError() {
        // Arrange
        String userEmail = "amit@gmail.com";
        String password = "punit@1234";
        Mockito.when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenThrow(new RuntimeException("Database error occurred."));

        // Assert
        assertThrows(ResponseStatusException.class, () -> {
            // Act
            userService.findDetailByEmail(userEmail, password);
        });
    }

    //    @Test
    //    public void testFindDetailByEmailAndPassword_UserFound() {
    //        // Arrange
    //        String userEmail = "rohit@gmail.com";
    //        String password = "punit@123";
    //        UserEntity userEntity = new UserEntity();
    //        userEntity.setIsloggedIn(true);
    //
    //        when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenReturn(Optional.of(userEntity));
    //        ReflectionTestUtils.setField(userEntity, "punit", 10L); // Set the ID manually
    //
    //        // Act
    //        ResponseEntity response = userService.findDetailByEmailandPassword(userEmail, password);
    //
    //        // Assert
    //        //        verify(userRepository, times(1)).save(userEntity);
    //        assertEquals(HttpStatus.OK, response.getStatusCode());
    //        assertEquals("LogOut Successfully", response.getBody());
    //        assertFalse(userEntity.isIsloggedIn());
    //    }

    @Test
    public void testFindDetailByEmailAndPassword_UserNotFound() {
        // Arrange
        String userEmail = "raj@gmail.com";
        String password = "rj@12345";

        when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> {
            userService.findDetailByEmailandPassword(userEmail, password);
        });
    }

    @Test
    public void testFindDetailByEmailAndPassword_UserNotFoundByEmail() {
        // Arrange
        String userEmail = "punit12@gmail.com";
        String password = "ar@12345";

        when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenThrow(EmptyResultDataAccessException.class);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> {
            userService.findDetailByEmailandPassword(userEmail, password);
        });
    }

    @Test
    public void testFindDetailByEmailAndPassword_InternalServerError() {
        // Arrange
        String userEmail = "avanish@123.com";
        String password = "avs@123";

        when(userRepository.findByUserEmailAndPassword(userEmail, password)).thenThrow(RuntimeException.class);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> {
            userService.findDetailByEmailandPassword(userEmail, password);
        });
    }

    @Test
    void testForgotPassword_UserFound() {
        // Arrange
        String userEmail = "ajeet@1234.com";
        String newPassword = "ajeet@123";

        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(userEmail);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setUserEmail(userEmail);
        updatePasswordRequest.setNewPassword(newPassword);

        when(userRepository.findByUserEmail(userEmail)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        UserDto result = userService.forgotPassword(updatePasswordRequest);

        // Assert
        assertNotNull(result);
        assertEquals("200", result.getStatus());
        assertEquals("password updated", result.getMessage());
        assertEquals(newPassword, userEntity.getPassword());
        //        verify(userRepository.save);
    }

    @Test
    void testForgotPassword_UserNotFound() {
        // Arrange
        String userEmail = "nonexistent@example.com";

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setUserEmail(userEmail);
        updatePasswordRequest.setNewPassword("newPassword");

        when(userRepository.findByUserEmail(userEmail)).thenReturn(Optional.empty());

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.forgotPassword(updatePasswordRequest));
        assertEquals(HttpStatus.NOT_FOUND, exception.getMessage());
        assertEquals("User not found", exception.getReason());
        //        verify(userRepository.save(any(UserEntity.class));
    }

    private UserEntity getUserDetails() {
        final UserEntity user = new UserEntity();
        user.setPassword("123");
        user.setUserEmail("punit@gmail.com");
        user.setId(1L);
        user.setUserName("punit123");
        user.setStatus(ACTIVE);
        user.setIsloggedIn(false);
        return user;
    }

    //    private Optional<UserEntity> getUserDetailslogin() {
    //        Optional<UserEntity> optionalUserEntity = null;
    //        optionalUserEntity.setPassword("123");
    //        optionalUserEntity.setUserEmail("punit@gmail.com");
    //        optionalUserEntity.setId(1L);
    //        optionalUserEntity.setUserName("punit123");
    //        optionalUserEntity.setStatus(ACTIVE);
    //        optionalUserEntity.setIsloggedIn(false);
    //        return optionalUserEntity;
    //    }

}
