package com.demo.tdd.service;

import com.demo.tdd.domain.Car;
import com.demo.tdd.domain.CarRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

   // @Cacheable("cars")
    public Car getCarDetails(String name) {
        Car car = carRepository.findByName(name);
        if(car == null) {
        						//car = new Car("prius", "hybrid"); 	// aby integration test działał db musi byc  
            throw new CarNotFoundException();
        }
        return car;
    }
}
