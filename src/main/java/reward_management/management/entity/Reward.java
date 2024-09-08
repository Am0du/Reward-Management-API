package reward_management.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reward_management.user.Entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @Column(name = "seq_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seqId;

    @Column(name = "total_cashback", nullable = false)
    private double totalCashback;

    @Column(name = "current_balance", nullable = false)
    private double currentBalance;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "seq_id")
    private User user;
}
