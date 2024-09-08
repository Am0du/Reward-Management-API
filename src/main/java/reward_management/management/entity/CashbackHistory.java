package reward_management.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import reward_management.user.Entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cashback_history")
@EntityListeners(AuditingEntityListener.class)
public class CashbackHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_id")
    private int seqId;

    private UUID id;

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    private double amount;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    private void generatUUID(){
        if(id == null){
            id = UUID.randomUUID();
        }
    }
}
