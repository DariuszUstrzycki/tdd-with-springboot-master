package com.demo.tdd.service;

import com.demo.tdd.domain.Car;
import com.demo.tdd.domain.CarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)  /// !!!!!!!!! PURE UNIT TEST WITHOUT INVOLVING SPRING AT ALL
public class CarServiceTest {  //Mock when MockitoJUnitRunner, very lighweight, not using Spring at all

    @Mock // Mockito annotation
    private CarRepository carRepository;

    private CarService carService;

    @Before
    public void setUp() throws Exception {
        carService = new CarService(carRepository);
    }

    @Test
    public void getCarDetails_returnsCarInfo() {          // w controllerze czytanie było z json object
        given(carRepository.findByName("prius")).willReturn(new Car("prius", "hybrid"));

        Car car = carService.getCarDetails("prius");

        assertThat(car.getName()).isEqualTo("prius");
        assertThat(car.getType()).isEqualTo("hybrid");
    }

    @Test(expected = CarNotFoundException.class)
    public void getCarDetails_whenCarNotFound() throws Exception {
        given(carRepository.findByName("prius")).willReturn(null);

        carService.getCarDetails("prius");
    }
}