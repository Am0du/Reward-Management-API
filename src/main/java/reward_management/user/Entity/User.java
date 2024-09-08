package reward_management.user.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reward_management.management.entity.CashbackHistory;
import reward_management.management.entity.Reward;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "seq_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seqId;

    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CashbackHistory> cashBacks;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reward reward;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void addRoles(Role role){
        if(roles == null)
            roles = new ArrayList<>();

        role.setUser(this);
        roles.add(role);
    }

    public void addCashBackHistory(CashbackHistory cashbackHistory){
        if(cashBacks == null)
            cashBacks = new ArrayList<>();

        cashbackHistory.setUser(this);
        cashBacks.add(cashbackHistory);
    }

    @PrePersist
    private void prePersist(){
        id = UUID.randomUUID();
        reward.setUser(this);
    }
}
