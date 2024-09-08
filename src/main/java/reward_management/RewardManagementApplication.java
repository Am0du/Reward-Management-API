package reward_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RewardManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardManagementApplication.class, args);
	}

}
