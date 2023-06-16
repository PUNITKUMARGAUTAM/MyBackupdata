package learningtask.learningnew.servicve;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import learningtask.learningnew.dto.ErrorResponse;
import learningtask.learningnew.dto.UpdatePasswordRequest;
import learningtask.learningnew.dto.UserDefineExceptions;
import learningtask.learningnew.dto.UserDto;
import learningtask.learningnew.entity.UserEntity;
import learningtask.learningnew.entity.UserStatus;
import learningtask.learningnew.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity createData(UserEntity userEntity) {
        try {
            userEntity.setStatus(UserStatus.ACTIVE);
            userRepository.save(userEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder().statusCode(500).message("Error In Create User").build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserEntity> findAllDetails() {

        try {
            List<UserEntity> userEntities = userRepository.findAll();
            return userEntities;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ResponseEntity updateData(UserEntity userEntity) {
        try {
            userRepository.save(userEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserDefineExceptions e) {
            ErrorResponse errorResponse = ErrorResponse.builder().statusCode(500).message("Exception occured during update data").build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //    @Override
    //    public String updateData(UserEntity userEntity) {
    //        try {
    //
    //            userRepository.save(userEntity);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return "Update successfully";
    //    }

    @Override
    public void deleteData(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<UserEntity> getById(Long id) {
        Optional<UserEntity> optionalUserEntity = null;
        try {
            optionalUserEntity = userRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return optionalUserEntity;

    }

    @Override
    public UserDto findDetailByEmail(String userEmail, String password) {
        Optional<UserEntity> optionalUserEntity = null;
        UserEntity userEntity = null;
        UserDto userDto = new UserDto();

        try {
            optionalUserEntity = userRepository.findByUserEmailAndPassword(userEmail, password);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for the provided email");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving user by email", ex);
        }
        if (!optionalUserEntity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for the provided email and Password");
        }
        userEntity = optionalUserEntity.get();
        if (userEntity.isIsloggedIn()) {
            userDto.setMessage("User Already logged in...");
        } else {
            userEntity.setIsloggedIn(true);
            userEntity = userRepository.save(userEntity);
            userDto.setMessage("User Successfully logged in...");
        }

        userDto.setStatus("200");

        return userDto;
    }

    @Override
    public ResponseEntity findDetailByEmailandPassword(String userEmail, String password) {
        Optional<UserEntity> optionalUserEntity = null;
        UserEntity userEntity = null;
        try {
            optionalUserEntity = userRepository.findByUserEmailAndPassword(userEmail, password);
            if (!optionalUserEntity.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for the provided email and Password");
            }
            userEntity = optionalUserEntity.get();
            userEntity.setIsloggedIn(false);
            userRepository.save(userEntity);
            //UserResponse userResponse=modelMapper.mapModels(userEntity,UserResponse.class);
            return new ResponseEntity<>("LogOut Successfully", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for the provided email");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving user by email", ex);
        }

    }

    @Override
    public UserDto forgotPassword(UpdatePasswordRequest updatePasswordRequest) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserEmail(updatePasswordRequest.getUserEmail());
        if (!userEntityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserEntity userEntity = userEntityOptional.get();
        userEntity.setPassword(updatePasswordRequest.getNewPassword());
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserDto userDto = new UserDto();
        userDto.setStatus("200");
        userDto.setMessage("password updated");

        return userDto;

    }

}

