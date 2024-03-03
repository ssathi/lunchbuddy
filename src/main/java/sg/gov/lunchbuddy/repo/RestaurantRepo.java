package sg.gov.lunchbuddy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.gov.lunchbuddy.domain.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
}
