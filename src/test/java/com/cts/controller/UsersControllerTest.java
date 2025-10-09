package com.cts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.entity.Vehicles.VehicleType;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UsersController.class)
public class UsersControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockitoBean
	UsersService userser;
	
	@MockitoBean
    VehicleService vehicleService;
	
	private Vehicles createTestVehicle(Integer id) {
        Vehicles vehicle = new Vehicles(
            id, 
            new Users(),
            "Maruti", 
            "Swift", 
            2021, 
            "KA01XX1234", 
            VehicleType.car, 
            "1.2L Petrol", 
            "Yes", 
            "4", 
            "Yes", 
            "Manual", 
            "Petrol"
        );
        return vehicle;
    }
	
	@Test
	void testCreateProfile_Success() throws Exception{
	    String targetEmail = "lankesh@ramayan.com";
	    Users user = new Users("Lankesh" , "Ravan" , "Sri Lanka" , "1111111111" , targetEmail);
	    Mockito.when(userser.createProfile(Mockito.any(Users.class))).thenReturn(user);
	    
	    mockMvc.perform(post("/api/users")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(user)))
	            .andExpect(status().isOk()) 
	            
	            .andExpect(jsonPath("$.email").value(targetEmail));
	}
	@Test
	void testGetUsersByEmail_Success() throws Exception {
		String targetEmail = "lankesh@ramayan.com";
		Users user = new Users("Lankesh" , "Ravan" , "Sri Lanka" , "1111111111" , targetEmail);
		
		Mockito.when(userser.viewProfile(targetEmail)).thenReturn(user);
		
		mockMvc.perform(get("/api/users/{email}", targetEmail))
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.email").value(targetEmail));
	}
	@Test
	void testUpdateUsers_Success() throws Exception {
	    String targetEmail = "lankesh@ramayan.com";
	    Users updatedUser = new Users("Lankesh" , "Ravan" , "Sri Lanka" , "1111111119" , targetEmail);
	    Mockito.when(userser.updateProfile(Mockito.eq(targetEmail), Mockito.any(Users.class)))
	           .thenReturn(updatedUser);

	    mockMvc.perform(put("/api/users/{email}", targetEmail)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(updatedUser)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.phone").value("1111111119"))
	            .andExpect(jsonPath("$.email").value(targetEmail));
	}
	@Test
    void testRegisterVehicle_Success() throws Exception {
		String targetEmail = "lankesh@ramayan.com";
        Vehicles vehicleToRegister = createTestVehicle(null);
        Vehicles savedVehicle = createTestVehicle(1);

        Mockito.when(vehicleService.registerVehicle(Mockito.any(Vehicles.class), Mockito.eq(targetEmail)))
               .thenReturn(savedVehicle);

        mockMvc.perform(post("/api/users/{email}/vehicle", targetEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleToRegister)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(1))
                .andExpect(jsonPath("$.make").value("Maruti"))
                .andExpect(jsonPath("$.registrationNumber").value("KA01XX1234"));
    }

    @Test
    void testUpdateVehicle_Success() throws Exception {
    	String targetEmail = "lankesh@ramayan.com";
        Integer vehicleId = 1;
        Vehicles vehicleToUpdate = createTestVehicle(vehicleId);
        vehicleToUpdate.setRegistrationNumber("DL09YY9876");
        
        Mockito.when(vehicleService.updateVehicle(
                Mockito.eq(targetEmail), 
                Mockito.eq(vehicleId), 
                Mockito.any(Vehicles.class)))
               .thenReturn(vehicleToUpdate);

        mockMvc.perform(put("/api/users//{email}/vehicle/{id}", targetEmail, vehicleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(vehicleId))
                .andExpect(jsonPath("$.registrationNumber").value("DL09YY9876"));
    }

    @Test
    void testGetAllVehiclesByEmail_Success() throws Exception {
    	String targetEmail = "lankesh@ramayan.com";
        Vehicles vehicle1 = createTestVehicle(1);
        Vehicles vehicle2 = createTestVehicle(2);
        vehicle2.setModel("Dzire");
        List<Vehicles> vehiclesList = Arrays.asList(vehicle1, vehicle2);

        Mockito.when(vehicleService.getAllVehiclesByEmail(targetEmail))
               .thenReturn(vehiclesList);

        mockMvc.perform(get("/api/users/{email}/vehicle", targetEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].vehicleId").value(1))
                .andExpect(jsonPath("$[1].model").value("Dzire"));
    }

    @Test
    void testViewVehicleById_Success() throws Exception {
    	String targetEmail = "lankesh@ramayan.com";
        Integer vehicleId = 1;
        Vehicles vehicle = createTestVehicle(vehicleId);

        Mockito.when(vehicleService.viewVehicle(Mockito.eq(targetEmail), Mockito.eq(vehicleId)))
               .thenReturn(vehicle);

        mockMvc.perform(get("/api/users/{email}/vehicle/{id}",targetEmail, vehicleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(vehicleId))
                .andExpect(jsonPath("$.make").value("Maruti"))
                .andExpect(jsonPath("$.year").value(2021));
    }
}
