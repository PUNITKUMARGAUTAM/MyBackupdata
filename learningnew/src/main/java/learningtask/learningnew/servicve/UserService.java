package learningtask.learningnew.servicve;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import learningtask.learningnew.dto.UpdatePasswordRequest;
import learningtask.learningnew.dto.UserDto;
import learningtask.learningnew.entity.UserEntity;

public interface UserService {
    ResponseEntity createData(UserEntity learningEntity);

    List<UserEntity> findAllDetails();

    ResponseEntity updateData(UserEntity learningEntity);

    void deleteData(Long id);

    Optional<UserEntity> getById(Long id);

    UserDto findDetailByEmail(String email, String password);

    ResponseEntity findDetailByEmailandPassword(String userEmail, String password);

    UserDto forgotPassword(UpdatePasswordRequest updatePasswordRequest);
}