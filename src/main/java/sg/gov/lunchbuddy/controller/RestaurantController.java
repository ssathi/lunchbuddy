package sg.gov.lunchbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.gov.lunchbuddy.domain.Restaurant;
import sg.gov.lunchbuddy.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @GetMapping("/")
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @PostMapping("/")
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return service.create(restaurant);
    }
}
