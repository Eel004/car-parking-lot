package com.example.carparkinglot.infrastructure.restful;

import com.example.carparkinglot.CarParkingLotApplication;
import com.example.carparkinglot.domain.entity.ParkingLot;
import com.example.carparkinglot.domain.repository.ParkingLotRepository;
import com.example.carparkinglot.infrastructure.hdb.HdbCarParkProvider;
import com.example.carparkinglot.infrastructure.onemap.OneMapConverter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CarParkingLotApplication.class)
@AutoConfigureMockMvc
public class CarParkControllerTest {

  private static final String URL = "http://localhost:8080/carparks/nearest";

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Mock
  private HdbCarParkProvider hdbCarParkProvider;

  @Mock
  private ParkingLotRepository parkingLotRepository;

  @Mock
  private OneMapConverter oneMapConverter;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }


  @Test
  public void givenMissingLongitude_whenFindNearestCarPark_thenReturnErrorMsg() throws Exception {
    mockMvc.perform(get(URL + "?latitude=14.192920&page=1&per_page=10"))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.errorId", Matchers.is(400)))
        .andExpect(jsonPath("$.message", Matchers.is("Missing request parameters")));
  }

  @Test
  public void givenMissingLatitude_whenFindNearestCarPark_thenReturnErrorMsg() throws Exception {
    mockMvc.perform(get(URL + "?longitude=104.192920&page=1&per_page=10"))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.errorId", Matchers.is(400)))
        .andExpect(jsonPath("$.message", Matchers.is("Missing request parameters")));
  }

  @Test
  public void givenMissingPaging_whenFindNearestCarPark_thenReturnErrorMsg() throws Exception {
    mockMvc.perform(get(URL + "?longitude=104.192920&latitude=1.213213"))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.errorId", Matchers.is(400)))
        .andExpect(jsonPath("$.message", Matchers.is("Missing request parameters")));
  }

  @Test
  public void givenValidParam_whenFindNearestCarPark_thenReturnResult() throws Exception {

    var pl = ParkingLot.builder()
        .carParkNo("CARNO")
        .address("address")
        .xCoord(12.2)
        .yCoord(11.1)
        .carParkType("Park Type")
        .typeOfParkingSystem("type of parking system")
        .shortTermParking("shortTermParking")
        .nightParking("nightParking")
        .carParkDecks(1)
        .gantryHeight(9.9)
        .carParBasement("car park basement")
        .totalLots(4)
        .lotType("C")
        .lotsAvailable(2)
        .updateDateTime(OffsetDateTime.now())
        .build();

    when(parkingLotRepository.findParkingLotsByLocation(anyDouble(), anyDouble(), anyDouble(), any(Pageable.class)))
        .thenReturn(List.of(pl));

    mockMvc.perform(get(URL + "?latitude=1.213213&longitude=104.192920&page=1&per_page=1"))
        .andExpect(status().isOk())
        .andDo(print());
  }

}
