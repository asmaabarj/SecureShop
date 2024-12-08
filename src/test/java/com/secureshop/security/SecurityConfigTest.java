package com.secureshop.security;

import com.secureshop.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;



    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

//    @Test
//    void publicEndpoints_ShouldBeAccessible() throws Exception {
//        mockMvc.perform(post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"login\":\"test\",\"password\":\"test\"}"))
//                .andExpect(status().isOk());
//    }



    @Test
    @WithMockUser(roles = "user")
    void userEndpoints_WithUserRole_ShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/api/user/categories"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "admin")
    void adminEndpoints_WithAdminRole_ShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/api/admin/categories"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void adminEndpoints_WithUserRole_ShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/categories"))
                .andExpect(status().isForbidden());
    }

    @Test
    void protectedEndpoints_WithoutAuth_ShouldBeUnauthorized() throws Exception {
        mockMvc.perform(get("/api/user/categories"))
                .andExpect(status().isUnauthorized());
    }
}