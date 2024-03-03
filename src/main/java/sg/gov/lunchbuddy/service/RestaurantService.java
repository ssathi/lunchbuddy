package sg.gov.lunchbuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.gov.lunchbuddy.domain.Restaurant;
import sg.gov.lunchbuddy.repo.RestaurantRepo;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo repo;

    public Restaurant create(Restaurant restaurant) {
        return repo.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return repo.findAll();
    }
}
