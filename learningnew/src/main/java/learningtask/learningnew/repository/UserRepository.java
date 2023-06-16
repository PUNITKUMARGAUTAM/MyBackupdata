package learningtask.learningnew.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import learningtask.learningnew.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //    UserEntity findByEmailAndPassword(String userEmail, String password);

    //    UserEntity findByEmail(String userEmail);

    @Query (value = "select * from learning_data where user_email = ?1 and user_password = ?2", nativeQuery = true)
    Optional<UserEntity> findByUserEmailAndPassword(String userEmail, String password);

    Optional<UserEntity> findByUserEmail(String userEmail);
}


