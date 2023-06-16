package learningtask.learningnew.servicve;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import learningtask.learningnew.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserResponse {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String userEmail;

    private String password;

    private UserStatus status;

    private String isloggedIn;
}