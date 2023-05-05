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
                            {"description": "Hello","status": "Open"}
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            [{"description": "Hello", "status": "Open"}]
                        """))
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void whenGetToDo_returnToDoById_and_200ok() throws Exception {
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
                .andExpect(content().json("""
                                    {"description":"Aenderung","status":"OPEN"}
                                """));
    }

    @Test
    void whenDeleteToDos_returnToDoWithoutItemById_and_200ok() throws Exception {
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                   {"description":"12", "status":"OPEN"}
                                """))
                .andExpect(status().isOk())
                .andReturn();

        String content1 = result1.getResponse().getContentAsString();

        ObjectMapper objectMapper1 = new ObjectMapper();
        ToDo toDo1 = objectMapper1.readValue(content1, ToDo.class);

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                   {"description":"34", "status":"OPEN"}
                                """))
                .andExpect(status().isOk())
                .andReturn();

        String content2 = result2.getResponse().getContentAsString();

        ObjectMapper objectMapper2 = new ObjectMapper();
        ToDo toDo2 = objectMapper2.readValue(content2, ToDo.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/"+ toDo1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                   {"description":"12", "status":"OPEN"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    [{"description":"34", "status":"OPEN"}]
                                """))
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

}
