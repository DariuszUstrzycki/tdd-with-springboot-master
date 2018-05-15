package com.demo.tdd.web;

import com.demo.tdd.domain.Car;
import com.demo.tdd.service.CarNotFoundException;
import com.demo.tdd.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {
	// more controller-oriented than integration test, more scenarious, eg what if car not found

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService service;

    @Test
    public void getCar_ReturnsCarDetails() throws Exception {
        given(service.getCarDetails(anyString())).willReturn(new Car("prius", "hybrid"));
     // mockujemy service (collaborator/dependency of controllera. Wystarczy ze na ten moment service ma tylko
     		// noezaimplementowana metodę getCarDetails()
     		
     		// similar to integration test, but not the whole app is loaded, no server, much faster
        mockMvc.perform(get("/cars/prius")).andExpect(status().isOk())
                .andExpect(jsonPath("name").value("prius"))
                .andExpect(jsonPath("type").value("hybrid"));
    }

    @Test
    public void getCar_notFound() throws Exception {

        given(service.getCarDetails(anyString())).willThrow(new CarNotFoundException());

        mockMvc.perform(get("/cars/prius")).andExpect(status().isNotFound());


    }


}
