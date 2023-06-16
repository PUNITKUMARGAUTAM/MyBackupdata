package learningtask.learningnew.controller;

import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import learningtask.learningnew.dto.UpdatePasswordRequest;
import learningtask.learningnew.dto.UserDefineExceptions;
import learningtask.learningnew.dto.UserDto;
import learningtask.learningnew.entity.UserEntity;
import learningtask.learningnew.repository.UserRepository;
import learningtask.learningnew.servicve.UserService;

@RequestMapping ("/learn")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping ("/createData")
    public ResponseEntity createData(@RequestBody UserEntity userEntity) {

        return userService.createData(userEntity);

    }

    @GetMapping ("/findAll")
    public List<UserEntity> findAllDetails() {

        return userService.findAllDetails();
    }

    @PostMapping ("update")
    public ResponseEntity updateData(@RequestBody UserEntity userEntity) {
        return userService.updateData(userEntity);
    }

    @DeleteMapping ("/delete/{id}")
    public void deleteData(@PathVariable Long id) {
        userService.deleteData(id);
    }

    @GetMapping ("/getById/{id}")
    public Optional<UserEntity> getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping ("/login")
    public ResponseEntity<?> getByEmail(@RequestParam String userEmail, @RequestParam String password) {
        UserDto userEntity = userService.findDetailByEmail(userEmail, password);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping ("/logout")
    public ResponseEntity<?> logout(@RequestParam String userEmail, @RequestParam String password) {
        return userService.findDetailByEmailandPassword(userEmail, password);

    }

    @PostMapping ("/update-password")
    public ResponseEntity<?> forgotPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        if (!StringUtils.isNotBlank(updatePasswordRequest.getUserEmail())) {

            throw new UserDefineExceptions("Please provide the email details");
        }
        if (!StringUtils.isNotBlank(updatePasswordRequest.getNewPassword())) {

            throw new UserDefineExceptions("Please provide the new password");
        }
        UserDto userDto = userService.forgotPassword(updatePasswordRequest);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}







