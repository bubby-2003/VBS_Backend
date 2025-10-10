package com.cts.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.entity.Auth;
import com.cts.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

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

        mockMvc.perform(get("/api/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetAll_EmptyList() throws Exception {
        Mockito.when(authService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testGetByEmail_Success() throws Exception {
        String email = "test@example.com";
        Auth auth = createAuth(email, "pass", "USER");

        Mockito.when(authService.getByEmail(email)).thenReturn(auth);

        mockMvc.perform(get("/api/auth/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    void testGetByEmail_NotFound() throws Exception {
        String email = "notfound@example.com";
        Mockito.when(authService.getByEmail(email)).thenReturn(null);

        mockMvc.perform(get("/api/auth/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testRegister_Success() throws Exception {
        Auth auth = createAuth("new@user.com", "pass", "USER");
        Mockito.when(authService.create(Mockito.any(Auth.class))).thenReturn("User registered successfully");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testRegister_Failure() throws Exception {
        Auth auth = createAuth("existing@user.com", "pass", "USER");
        Mockito.when(authService.create(Mockito.any(Auth.class))).thenReturn("User already exists");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already exists"));
    }

    @Test
    void testRegister_NullBody() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_Success() throws Exception {
        String email = "update@user.com";
        Auth auth = createAuth(email, "newpass", "ADMIN");

        Mockito.when(authService.update(Mockito.eq(email), Mockito.any(Auth.class))).thenReturn(auth);

        mockMvc.perform(put("/api/auth/{email}", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        String email = "notfound@user.com";
        Auth auth = createAuth(email, "pass", "USER");

        Mockito.when(authService.update(Mockito.eq(email), Mockito.any(Auth.class))).thenReturn(null);

        mockMvc.perform(put("/api/auth/{email}", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
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
    void testLogin_Success() throws Exception {
        Auth auth = createAuth("login@user.com", "pass", "USER");
        Mockito.when(authService.login(auth.getEmail(), auth.getPassword(), auth.getRole()))
                .thenReturn("Login successful");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    void testLogin_Failure() throws Exception {
        Auth auth = createAuth("wrong@user.com", "badpass", "USER");
        Mockito.when(authService.login(auth.getEmail(), auth.getPassword(), auth.getRole()))
                .thenReturn("Invalid credentials");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void testLogin_NullBody() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnauthorized());
    }
}