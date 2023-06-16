package learningtask.learningnew.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@MappedSuperclass
@EntityListeners (AuditingEntityListener.class)
public abstract class AuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column (name = "CREATED_BY")
    private Long createdBy;

    @CreatedDate
    @Column (name = "CREATE_TIME")
    private OffsetDateTime createTime = OffsetDateTime.now();

    @Column (name = "UPDATED_BY")
    private Long updatedBy;

    @LastModifiedDate
    @Column (name = "LAST_UPDATE_TIME")
    private OffsetDateTime lastUpdateTime = OffsetDateTime.now();
}