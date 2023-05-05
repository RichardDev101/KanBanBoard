package de.neuefische.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.model.ToDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class KanbanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DirtiesContext
    void whenGetList_returnToDosList_and_200ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void whenAddToDos_returnToDo_and_200ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType("application/json")
                        .content("""
                                    {
                                    "description": "Datenbanken einrichten",
                                    "status": "Open"
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

    @Test
    @DirtiesContext
    void whenDetails_returnToDoById_and_200ok() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                   {"description":"hello ","status":"OPEN"}
                                """))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        ToDo toDo = objectMapper.readValue(content, ToDo.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + toDo.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {"description":"hello ","status":"OPEN"}
                                """)).andExpect(jsonPath("$.id").value(toDo.getId()));
    }

    @Test
    @DirtiesContext
    void whenEditToDos_returnEditToDoById_and_200ok() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                   {"description":"1233", "status":"OPEN"}
                                """))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        ToDo toDo = objectMapper.readValue(content, ToDo.class);

       mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/"+ toDo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                   {"description":"Aenderung", "status":"OPEN"}
                                """))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + toDo.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {"description":"Aenderung","status":"OPEN"}
                                """));
    }

}
