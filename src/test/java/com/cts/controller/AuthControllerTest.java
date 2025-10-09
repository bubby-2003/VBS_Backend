package com.cts.controller;

import com.cts.entity.Auth;
import com.cts.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    AuthService authService;
    private Auth createAuth(String email, String password, String role) {
        return new Auth(email, password, role);
    }


    @Test
    void testGetAll_Success() throws Exception {
        List<Auth> authList = Arrays.asList(
                createAuth("user1@test.com", "pass1", "USER"),
                createAuth("admin@test.com", "pass2", "ADMIN")
        );
        Mockito.when(authService.getAll()).thenReturn(authList);

        mockMvc.perform(get("/api/auth")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetByEmail_Success() throws Exception {
        String testEmail = "test@example.com";
        Auth auth = createAuth(testEmail, "secure_pass", "USER");

        Mockito.when(authService.getByEmail(testEmail)).thenReturn(auth);

        mockMvc.perform(get("/api/auth/{email}", testEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(testEmail));
    }

    @Test
    void testRegister_Success() throws Exception {
        Auth newAuth = createAuth("newuser@reg.com", "regpass", "MECHANIC");
        String successMessage = "User registered successfully";

        Mockito.when(authService.create(Mockito.any(Auth.class))).thenReturn(successMessage);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAuth)))
                .andExpect(status().isCreated())
                .andExpect(content().string(successMessage));
    }

    @Test
    void testUpdate_Success() throws Exception {
        String testEmail = "update@test.com";
        Auth requestBody = createAuth(testEmail, "new_password", "ADMIN");
        Auth updatedAuth = createAuth(testEmail, "new_password", "ADMIN");

        Mockito.when(authService.update(Mockito.eq(testEmail), Mockito.any(Auth.class)))
                .thenReturn(updatedAuth);

        mockMvc.perform(put("/api/auth/{email}", testEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void testDelete_Success() throws Exception {
        String testEmail = "delete@test.com";
        Mockito.doNothing().when(authService).delete(testEmail);

        mockMvc.perform(delete("/api/auth/{email}", testEmail))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully"));
    }

    @Test
    void testLogin_Success() throws Exception {
        Auth loginAuth = createAuth("login@test.com", "loginpass", "MECHANIC");
        String successMessage = "Login successful for MECHANIC";

        Mockito.when(authService.login(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(successMessage);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginAuth)))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    void testGetByEmail_NotFound() throws Exception {
        String testEmail = "nonexistent@example.com";
        Mockito.when(authService.getByEmail(testEmail)).thenThrow(new NoSuchElementException("Auth user not found"));

        mockMvc.perform(get("/api/auth/{email}", testEmail))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRegister_Failure_BadRequest() throws Exception {
        Auth newAuth = createAuth("existing@reg.com", "regpass", "MECHANIC");
        String failureMessage = "User already exists";

        Mockito.when(authService.create(Mockito.any(Auth.class))).thenReturn(failureMessage);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAuth)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(failureMessage));
    }
    
    @Test
    void testUpdate_NotFound() throws Exception {
        String testEmail = "notfound@update.com";
        Auth requestBody = createAuth(testEmail, "new_password", "ADMIN");
        Mockito.when(authService.update(Mockito.eq(testEmail), Mockito.any(Auth.class)))
                .thenThrow(new NoSuchElementException("Auth user not found for update"));

        mockMvc.perform(put("/api/auth/{email}", testEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        String testEmail = "notfound@delete.com";
        Mockito.doThrow(new NoSuchElementException("Auth user not found for deletion"))
               .when(authService).delete(testEmail);
        mockMvc.perform(delete("/api/auth/{email}", testEmail))
                .andExpect(status().isNotFound());
    }

    @Test
    void testLogin_Failure_Unauthorized() throws Exception {
        Auth loginAuth = createAuth("wrong@test.com", "badpass", "USER");
        String failureMessage = "Invalid credentials";

        Mockito.when(authService.login(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(failureMessage);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginAuth)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(failureMessage));
    }
}
