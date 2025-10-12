package com.cts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.dto.UsersRequestDTO;
import com.cts.dto.UsersResponseDTO;
import com.cts.dto.VehicleRequestDTO;
import com.cts.dto.VehicleResponseDTO;
import com.cts.entity.Users.Status;
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.entity.Vehicles.VehicleType;
import com.cts.mapper.UsersMapper;
import com.cts.mapper.VehicleMapper;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @MockitoBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsersRequestDTO createUserRequestDTO() {
        UsersRequestDTO dto = new UsersRequestDTO();
        dto.setEmail("user@test.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setAddress("123 Street");
        dto.setPhone("9876543210");
        dto.setStatus(Status.active);
        return dto;
    }

    private UsersResponseDTO createUserResponseDTO() {
        UsersResponseDTO dto = new UsersResponseDTO();
        dto.setId(1);
        dto.setEmail("user@test.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setAddress("123 Street");
        dto.setPhone("9876543210");
        dto.setStatus(Status.active); 
        return dto;
    }

    private VehicleRequestDTO createVehicleRequestDTO() {
        VehicleRequestDTO dto = new VehicleRequestDTO();
        dto.setEmail("user@test.com");
        dto.setMake("Toyota");
        dto.setModel("Corolla");
        dto.setYear(2022);
        dto.setRegistrationNumber("TN01AB1234");
        dto.setVehicleType(VehicleType.car);
        dto.setEngine("Petrol");
        dto.setAbs("Yes");
        dto.setDoors("4");
        dto.setAc("Yes");
        dto.setTransmission("Manual");
        dto.setFuel("Petrol");
        return dto;
    }

    private VehicleResponseDTO createVehicleResponseDTO() {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setVehicleId(1);
        dto.setEmail("user@test.com");
        dto.setMake("Toyota");
        dto.setModel("Corolla");
        dto.setYear(2022);
        dto.setRegistrationNumber("TN01AB1234");
        dto.setVehicleType(VehicleType.car);
        dto.setEngine("Petrol");
        dto.setAbs("Yes");
        dto.setDoors("4");
        dto.setAc("Yes");
        dto.setTransmission("Manual");
        dto.setFuel("Petrol");
        return dto;
    }

    @Test
    void testCreateUserProfile_Success() throws Exception {
        UsersRequestDTO requestDTO = createUserRequestDTO();
        UsersResponseDTO responseDTO = createUserResponseDTO();
        Users user = new Users();

        Mockito.when(usersService.createProfile(any(UsersRequestDTO.class))).thenReturn(user);

        try (MockedStatic<UsersMapper> mockedMapper = Mockito.mockStatic(UsersMapper.class)) {
            mockedMapper.when(() -> UsersMapper.toDTO(user)).thenReturn(responseDTO);

            mockMvc.perform(post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("user@test.com"))
                    .andExpect(jsonPath("$.firstName").value("John"));
        }
    }

    @Test
    void testUpdateUserProfile_Success() throws Exception {
        String email = "user@test.com";
        UsersRequestDTO requestDTO = createUserRequestDTO();
        UsersResponseDTO responseDTO = createUserResponseDTO();
        Users user = new Users();

        Mockito.when(usersService.updateProfile(eq(email), any(UsersRequestDTO.class))).thenReturn(user);

        try (MockedStatic<UsersMapper> mockedMapper = Mockito.mockStatic(UsersMapper.class)) {
            mockedMapper.when(() -> UsersMapper.toDTO(user)).thenReturn(responseDTO);

            mockMvc.perform(put("/api/users/{email}", email)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value(email))
                    .andExpect(jsonPath("$.lastName").value("Doe"));
        }
    }

    @Test
    void testViewUserProfile_Success() throws Exception {
        String email = "user@test.com";
        UsersResponseDTO responseDTO = createUserResponseDTO();
        Users user = new Users();

        Mockito.when(usersService.viewProfile(email)).thenReturn(user);

        try (MockedStatic<UsersMapper> mockedMapper = Mockito.mockStatic(UsersMapper.class)) {
            mockedMapper.when(() -> UsersMapper.toDTO(user)).thenReturn(responseDTO);

            mockMvc.perform(get("/api/users/{email}", email))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value(email))
                    .andExpect(jsonPath("$.address").value("123 Street"));
        }
    }

    @Test
    void testRegisterVehicle_Success() throws Exception {
        String email = "user@test.com";
        VehicleRequestDTO requestDTO = createVehicleRequestDTO();
        VehicleResponseDTO responseDTO = createVehicleResponseDTO();
        Vehicles vehicle = new Vehicles();

        Mockito.when(vehicleService.registerVehicle(any(VehicleRequestDTO.class), eq(email))).thenReturn(vehicle);

        try (MockedStatic<VehicleMapper> mockedMapper = Mockito.mockStatic(VehicleMapper.class)) {
            mockedMapper.when(() -> VehicleMapper.toDTO(vehicle)).thenReturn(responseDTO);

            mockMvc.perform(post("/api/users/{email}/vehicle", email)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.make").value("Toyota"))
                    .andExpect(jsonPath("$.registrationNumber").value("TN01AB1234"));
        }
    }

    @Test
    void testUpdateVehicle_Success() throws Exception {
        String email = "user@test.com";
        Integer id = 1;
        VehicleRequestDTO requestDTO = createVehicleRequestDTO();
        VehicleResponseDTO responseDTO = createVehicleResponseDTO();
        Vehicles vehicle = new Vehicles();

        Mockito.when(vehicleService.updateVehicle(eq(email), eq(id), any(VehicleRequestDTO.class))).thenReturn(vehicle);

        try (MockedStatic<VehicleMapper> mockedMapper = Mockito.mockStatic(VehicleMapper.class)) {
            mockedMapper.when(() -> VehicleMapper.toDTO(vehicle)).thenReturn(responseDTO);

            mockMvc.perform(put("/api/users/{email}/vehicle/{id}", email, id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.model").value("Corolla"));
        }
    }

    @Test
    void testViewVehicleById_Success() throws Exception {
        String email = "user@test.com";
        Integer id = 1;
        VehicleResponseDTO responseDTO = createVehicleResponseDTO();
        Vehicles vehicle = new Vehicles();

        Mockito.when(vehicleService.viewVehicle(email, id)).thenReturn(vehicle);

        try (MockedStatic<VehicleMapper> mockedMapper = Mockito.mockStatic(VehicleMapper.class)) {
            mockedMapper.when(() -> VehicleMapper.toDTO(vehicle)).thenReturn(responseDTO);

            mockMvc.perform(get("/api/users/{email}/vehicle/{id}", email, id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.vehicleId").value(1))
                    .andExpect(jsonPath("$.fuel").value("Petrol"));
        }
    }

    @Test
    void testGetAllVehiclesByUser_Success() throws Exception {
        String email = "user@test.com";
        Vehicles vehicle = new Vehicles();
        VehicleResponseDTO responseDTO = createVehicleResponseDTO();

        Mockito.when(vehicleService.getAllVehiclesByEmail(email)).thenReturn(List.of(vehicle));

        try (MockedStatic<VehicleMapper> mockedMapper = Mockito.mockStatic(VehicleMapper.class)) {
            mockedMapper.when(() -> VehicleMapper.toDTO(vehicle)).thenReturn(responseDTO);

            mockMvc.perform(get("/api/users/{email}/vehicle", email))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].make").value("Toyota"))
                    .andExpect(jsonPath("$[0].vehicleType").value("car"));
        }
    }
}