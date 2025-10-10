package com.cts.controller;

import com.cts.dto.MechanicRequestDTO;
import com.cts.dto.MechanicResponseDTO;
import com.cts.entity.Mechanic.Availability;
import com.cts.entity.Mechanic.Rating;
import com.cts.entity.Mechanic.Status;
import com.cts.entity.Mechanic.VerificationStatus;
import com.cts.service.MechanicService;
import com.cts.mapper.MechanicMapper;
import com.cts.entity.Mechanic;
import com.cts.entity.Auth;
import com.cts.entity.ServiceCenter;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MechanicController.class)
public class MechanicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MechanicService mechanicService;

    @Autowired
    private ObjectMapper objectMapper;

    private MechanicRequestDTO createRequestDTO() {
        return new MechanicRequestDTO(
                "mech@test.com",
                101,
                "Mech Name",
                "Engine Specialist",
                "Oil Change, Diagnostics",
                Availability.Available,
                Rating.Excellent,
                VerificationStatus.yes,
                Status.active,
                "123 Street, City",
                "9876543210"
        );
    }

    private Mechanic createMechanicEntity() {
        Auth auth = new Auth();
        auth.setEmail("mech@test.com");

        ServiceCenter center = new ServiceCenter();
        center.setServicecenterId(101);

        Mechanic mechanic = new Mechanic();
        mechanic.setId(1);
        mechanic.setAuth(auth);
        mechanic.setServiceCenter(center);
        mechanic.setName("Mech Name");
        mechanic.setExpertise("Engine Specialist");
        mechanic.setSkills("Oil Change, Diagnostics");
        mechanic.setAvailability(Availability.Available);
        mechanic.setRating(Rating.Excellent);
        mechanic.setIsVerified(VerificationStatus.yes);
        mechanic.setStatus(Status.active);
        mechanic.setAddress("123 Street, City");
        mechanic.setPhone("9876543210");
        return mechanic;
    }

    private MechanicResponseDTO createResponseDTO() {
        MechanicResponseDTO dto = new MechanicResponseDTO();
        dto.setId(1);
        dto.setEmail("mech@test.com");
        dto.setCenterId(101);
        dto.setName("Mech Name");
        dto.setExpertise("Engine Specialist");
        dto.setSkills("Oil Change, Diagnostics");
        dto.setAvailability(Availability.Available);
        dto.setRating(Rating.Excellent);
        dto.setIsVerified(VerificationStatus.yes);
        dto.setStatus(Status.active);
        dto.setAddress("123 Street, City");
        dto.setPhone("9876543210");
        return dto;
    }

    @Test
    void testCreateMechanic_Success() throws Exception {
        MechanicRequestDTO requestDTO = createRequestDTO();
        Mechanic mechanic = createMechanicEntity();
        MechanicResponseDTO responseDTO = createResponseDTO();

        Mockito.when(mechanicService.createMechanic(any(MechanicRequestDTO.class))).thenReturn(mechanic);

        try (MockedStatic<MechanicMapper> mockedMapper = Mockito.mockStatic(MechanicMapper.class)) {
            mockedMapper.when(() -> MechanicMapper.toDTO(mechanic)).thenReturn(responseDTO);

            mockMvc.perform(post("/api/mechanic")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.email").value("mech@test.com"))
                    .andExpect(jsonPath("$.name").value("Mech Name"))
                    .andExpect(jsonPath("$.availability").value("Available"))
                    .andExpect(jsonPath("$.rating").value("Excellent"));
        }
    }

    @Test
    void testUpdateMechanic_Success() throws Exception {
        String email = "mech@test.com";
        MechanicRequestDTO requestDTO = createRequestDTO();
        Mechanic mechanic = createMechanicEntity();
        MechanicResponseDTO responseDTO = createResponseDTO();

        Mockito.when(mechanicService.updateMechanic(eq(email), any(MechanicRequestDTO.class))).thenReturn(mechanic);

        try (MockedStatic<MechanicMapper> mockedMapper = Mockito.mockStatic(MechanicMapper.class)) {
            mockedMapper.when(() -> MechanicMapper.toDTO(mechanic)).thenReturn(responseDTO);

            mockMvc.perform(put("/api/mechanic/{email}", email)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value(email))
                    .andExpect(jsonPath("$.status").value("active"));
        }
    }

    @Test
    void testGetMechanicByEmail_Success() throws Exception {
        String email = "mech@test.com";
        Mechanic mechanic = createMechanicEntity();
        MechanicResponseDTO responseDTO = createResponseDTO();

        Mockito.when(mechanicService.getMechanicByEmail(email)).thenReturn(mechanic);

        try (MockedStatic<MechanicMapper> mockedMapper = Mockito.mockStatic(MechanicMapper.class)) {
            mockedMapper.when(() -> MechanicMapper.toDTO(mechanic)).thenReturn(responseDTO);

            mockMvc.perform(get("/api/mechanic/{email}", email))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value(email))
                    .andExpect(jsonPath("$.expertise").value("Engine Specialist"));
        }
    }

    @Test
    void testGetMechanicByEmail_NotFound() throws Exception {
        String email = "notfound@test.com";

        Mockito.when(mechanicService.getMechanicByEmail(email)).thenReturn(null);

        mockMvc.perform(get("/api/mechanic/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}