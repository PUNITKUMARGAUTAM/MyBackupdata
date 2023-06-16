package learningtask.learningnew.entity;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Entity
@Component
@Table (name = "learningData")
public class UserEntity extends AuditLog {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    @Column (name = "id")
    private Long id;

    @NotNull
    @Column (name = "User_name")
    @Size (min = 4, message = "userName must be min 4 characters")
    private String userName;

    @NotNull
    @Column (name = "User_Email")
    @Pattern (regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid user Email Id")
    private String userEmail;

    @NotNull
    @Column (name = "User_Address")

    private String address;

    @NotNull
    @Column (name = "user_MobileNumber")
    @Pattern (regexp = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$", message = "Invalid mobileNumber ....")
    private String mobileNumber;

    @Column (name = "user_STATUS")
    private UserStatus status;

    @Column (name = "user_isLoogedIn")

    private boolean isloggedIn;

    @Column (name = "user_password")
    private String password;
}
