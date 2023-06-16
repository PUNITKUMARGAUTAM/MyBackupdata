package learningtask.learningnew.controller;

import static learningtask.learningnew.entity.UserStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import learningtask.learningnew.entity.UserEntity;
import learningtask.learningnew.servicve.UserService;

//@SpringBootTest
@RunWith (MockitoJUnitRunner.class)
public class ControllerTest {
    //    @Autowired
    //    ObjectMapper objectMapper;
    @Mock
    UserService userService;

    @Autowired
    UserController userController;

    //    @Test
    //    public void createData() {
    //        UserEntity learningEntity = getUserDetails();
    //        when(learningService.createData(learningEntity)).thenReturn(str);
    //        String str = learningController.createData(learningEntity);
    //        assertEquals(str, );
    //
    //    }

    //    @Test
    //    void testCreateData() {
    //        UserEntity userEntity = getUserDetails();
    //        when(userService.createData(any(UserEntity.class))).thenReturn(ResponseEntity.ok().build());
    //        ResponseEntity response = userController.createData(userEntity);
    //        //        ((userService.createData(userEntity).getStatusCode());
    //        assertEquals(HttpStatus.OK, response.getStatusCode());
    //
    //    }

    //    @Test
    //        public void addCreatDataTest(){
    //           String str = "{\n" + "  \"result\": \"Data saved succesfully\"\n" + "}";
    //            HustleEntity hustleEntity=new HustleEntity(1,"Ajeet","Kumar","Varanasi",975867435,"ajeet@gmail.com","ajeet@123");
    //            when(hustleServiceImpl.addHustle(hustleEntity)).thenReturn(str);
    //           String str1 =  hustleController.addHustle(hustleEntity);
    //           assertEquals(str1,str);
    //        }

    @Test
    public void findAllTest() {
        List<UserEntity> userEntitiesList = new ArrayList();
        //        userEntitiesList.add(new UserEntity(1L, "123", "ajeet@gmail.com", "Ajeet",ACTIVE);
        when(userService.findAllDetails()).thenReturn(userEntitiesList);
        List<UserEntity> hustleEntities = userController.findAllDetails();

    }

    //    @Test
    //    void testUpdateData_ReturnsOkResponse() {
    //        // Arrange
    //        UserEntity userEntity = new UserEntity();
    //        // Set up any required properties or values on the userEntity
    //
    //        when(userService.updateData(userEntity)).thenReturn(ResponseEntity.ok().build());
    //
    //        // Act
    //        ResponseEntity response = userController.updateData(userEntity);
    //
    //        // Assert
    //        assertEquals(HttpStatus.OK, response.getStatusCode());
    //        verify((userService.updateData(userEntity));
    //    }

    @Test
    void testUpdateData_ReturnsOkResponse() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        // Set up any required properties or values on the userEntity

        when(userService.updateData(userEntity)).thenReturn(ResponseEntity.ok().build());

        // Act
        ResponseEntity response = userController.updateData(userEntity);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // No verification of userService.updateData is performed in this version
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
}
