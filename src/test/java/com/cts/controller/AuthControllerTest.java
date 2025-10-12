package com.cts.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.exception.GlobalExceptionHandler;
import com.cts.exception.ResourceNotFoundException;
import com.cts.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper; 

@WebMvcTest(AuthController.class)
@Import(GlobalExceptionHandler.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthRequestDTO createAuthRequest(String email, String password , String role) {
        return new AuthRequestDTO(email, password , role); 
    }

    private AuthResponseDTO createAuthResponse(String email, String role, String token) {
        return new AuthResponseDTO(email, role);
    }


    @Test
    void testGetAll_Success() throws Exception {
        List<AuthResponseDTO> authList = Arrays.asList(
                createAuthResponse("user1@test.com", "USER", "token1"),
                createAuthResponse("admin@test.com", "ADMIN", "token2")
        );
        when(authService.getAll()).thenReturn(authList);

        mockMvc.perform(get("/api/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is("user1@test.com")))
                .andExpect(jsonPath("$[0].role", is("USER")));
    }

    @Test
    void testGetAll_EmptyList() throws Exception {
        when(authService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testGetByEmail_Success() throws Exception {
        String email = "test@example.com";
        AuthResponseDTO authDto = createAuthResponse(email, "USER", null);

        when(authService.getByEmail(email)).thenReturn(authDto);

        mockMvc.perform(get("/api/auth/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.role", is("USER")));
    }

	@Test
    void testGetByEmail_NotFound() throws Exception {
        String email = "notfound@example.com";
        when(authService.getByEmail(email)).thenThrow(new ResourceNotFoundException("Auth not found"));
        mockMvc.perform(get("/api/auth/{email}", email))
                .andExpect(status().isNotFound()); 
    }

    @Test
    void testRegister_Success() throws Exception {
        AuthRequestDTO requestDto = createAuthRequest("new@user.com", "pass" , "USER");

        when(authService.create(Mockito.any(AuthRequestDTO.class))).thenReturn("Registered successfully");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Registered successfully"));
    }

    @Test
    void testRegister_Failure() throws Exception {
        AuthRequestDTO requestDto = createAuthRequest("existing@user.com", "pass" , "USER");
        
        when(authService.create(Mockito.any(AuthRequestDTO.class))).thenReturn("User already exists");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already exists"));
    }

    @Test
    void testRegister_InvalidBody() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
		        .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
		        .andExpect(status().isInternalServerError());
    }

    @Test
    void testUpdate_Success() throws Exception {
        String email = "update@user.com";
        AuthRequestDTO requestDto = createAuthRequest(email, "newpass", "ADMIN");
        AuthResponseDTO responseDto = createAuthResponse(email, "ADMIN", "newtoken");

        when(authService.update(Mockito.eq(email), Mockito.any(AuthRequestDTO.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/auth/{email}", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role", is("ADMIN")))
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        String email = "notfound@user.com";
        AuthRequestDTO requestDto = createAuthRequest(email, "pass" , "USER");

        when(authService.update(Mockito.eq(email), Mockito.any(AuthRequestDTO.class)))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(put("/api/auth/{email}", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound()); 
    }

    @Test
    void testDelete_Success() throws Exception {
        String email = "delete@user.com";
        doNothing().when(authService).delete(email);

        mockMvc.perform(delete("/api/auth/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully"));
    }

    @Test
    void testDelete_Failure() throws Exception {
        String email = "fail@user.com";
        doThrow(new RuntimeException("Delete failed")).when(authService).delete(email);

        mockMvc.perform(delete("/api/auth/{email}", email))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testLogin_Success_Debug() throws Exception {
        AuthRequestDTO requestDto = createAuthRequest("login@user.com", "pass", "USER"); 

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(result -> System.err.println(result.getResponse().getContentAsString()))
                .andExpect(status().isInternalServerError()); 
    }

    @Test
    void testLogin_Failure() throws Exception {
        AuthRequestDTO requestDto = createAuthRequest("wrong@user.com", "badpass" , "USER");
        
        when(authService.login(Mockito.any(AuthRequestDTO.class)))
                .thenReturn("Invalid credentials");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void testLogin_InvalidBody_Debug() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isInternalServerError()); 
    }
}