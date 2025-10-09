package com.cts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.entity.Mechanic;
import com.cts.service.MechanicService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = MechanicController.class)
public class MechanicControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockitoBean
	MechanicService mechSer;
	
	@Test
	void testCreateMechanic_Success() throws Exception {
		Mechanic mechanic = new Mechanic("Sidharth" , "4 year experience" , "oil change" , "coimbatore" , "8256789234" , "sidharth@gmail.com");
		
		Mockito.when(mechSer.createMechanic(Mockito.any(Mechanic.class))).thenReturn(mechanic);
		
		mockMvc.perform(post("/api/mechanic")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mechanic)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Sidharth"));
	}
	
	@Test
	void testGetMechanicByEmail_Success() throws Exception {
		String targetEmail = "sidharth@gmail.com";
		Mechanic mechanic = new Mechanic("Sidharth" , "4 year experience" , "oil change , carbeurator" , "coimbatore" , "8256789234" , targetEmail);
		
		Mockito.when(mechSer.getMechanicByEmail(targetEmail)).thenReturn(mechanic);
		
		mockMvc.perform(get("/api/mechanic/{email}", targetEmail))
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.auth.email").value(targetEmail));
	}
	
	@Test
	void testUpdateMechanic_Success() throws Exception {
		String targetEmail = "sidharth@gmail.com";
		Mechanic updatedMechanic = new Mechanic("Sidharth V" , "6 years experience" , "oil change, tire rotation" , "chennai" , "9999999999" , targetEmail);
		
		Mockito.when(mechSer.updateMechanic(Mockito.eq(targetEmail), Mockito.any(Mechanic.class)))
		       .thenReturn(updatedMechanic);
		
		mockMvc.perform(put("/api/mechanic/{email}", targetEmail)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedMechanic))) 
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Sidharth V"))
				.andExpect(jsonPath("$.expertise").value("6 years experience"))
		        .andExpect(jsonPath("$.auth.email").value(targetEmail));
	}
}
