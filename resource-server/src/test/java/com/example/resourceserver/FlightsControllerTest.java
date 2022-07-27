package com.example.resourceserver;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = "logging.level.org.springframework.security=TRACE")
class FlightsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FlightRepository flightRepository;

    @Nested
    class Forbidden {
        @Test
        @SneakyThrows
        @WithMockUser(authorities = {"flights:read", "flights:write"})
        void invalidAuthoritiesThenForbidden() {
            mockMvc.perform(get("/flights/all"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @SneakyThrows
        @WithMockUser(authorities = {"flights:read", "flights:write"})
        void invalidCsrfTokenThenForbidden() {
            mockMvc.perform(put("/flights/{flightNumber}/taxi", 102))
                    .andExpect(status().isForbidden());
        }

        @Test
        @SneakyThrows
        @WithMockUser(username = "khang", authorities = {"flights:read", "flights:write"})
        void taxiWhenPilotIdNotEqualToAuthenticationNameThenForbidden() {
            when(flightRepository.findByFlightNumber(anyString()))
                    .thenReturn(new Flight("103", "trong", Flight.Status.BOARD));

            mockMvc.perform(put("/flights/{flightNumber}/taxi", 103).with(csrf().asHeader()))
                    .andExpect(status().isForbidden());
        }
    }


    @Nested
    class Authorized {
        @Test
        @SneakyThrows
        @WithMockUser(authorities = "flights:all")
        void requestProtectedAllFlightsWithUser() {
            mockMvc.perform(get("/flights/all"))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        @WithMockUser(authorities = "flights:read")
        void requestProtectedFlightsWithUser() {
            mockMvc.perform(get("/flights"))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        @WithMockUser(username = "khang", authorities = {"flights:read", "flights:write"})
        void requestProtectedTaxiRequestWithCsrf() {
            when(flightRepository.findByFlightNumber(anyString()))
                    .thenReturn(new Flight("102", "khang", Flight.Status.BOARD));

            mockMvc.perform(put("/flights/{flightNumber}/taxi", 102).with(csrf().asHeader()))
                    .andExpect(status().isOk());
        }
    }
}
