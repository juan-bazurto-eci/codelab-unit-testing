package org.adaschool.Weather.controller;

import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

public class WeatherReportControllerTest {

    @Mock
    private WeatherReportService weatherReportService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new WeatherReportController(weatherReportService)).build();
    }

    @Test
    void getWeatherReport_shouldReturnWeatherReport() throws Exception {
        double latitude = 37.8267;
        double longitude = -122.4233;
        WeatherReport weatherReport = new WeatherReport();
        weatherReport.setHumidity(84.0);
        weatherReport.setTemperature(0.0);
        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(weatherReport);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/weather-report")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json ("{\n" + "\"temperature\": 0.0,\n" +
                        "\"humidity\": 84.0\n" + "}"));
    }

    @Test
    void getWeatherReport_shouldReturnBadRequest_whenMissingParams() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/weather-report"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
