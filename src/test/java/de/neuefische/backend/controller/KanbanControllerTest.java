package de.neuefische.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class KanbanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void whenGetList_returnToDosList_and_200() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
        void whenAddOrder_returnOrderWithId_andStatusCode200_version() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                            .contentType("application/json")
                            .content("""
                                        {
                                        "description": "1",
                                        "status": "Apfel"
                                        }
                                    """))
                    .andExpect(status().isOk());

            mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                                        [{
                                        "description": "1",
                                        "status": "Apfel"
                                        }]
                                    """)).andExpect(jsonPath("$[0].id").isNotEmpty());
        }
}