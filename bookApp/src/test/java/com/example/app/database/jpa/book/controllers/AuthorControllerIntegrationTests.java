package com.example.app.database.jpa.book.controllers;

import com.example.app.database.jpa.book.TestDataUtils;
import com.example.app.database.jpa.book.domain.dto.CreateAuthorDto;
import com.example.app.database.jpa.book.domain.dto.ResponseAuthorDto;
import com.example.app.database.jpa.book.domain.entities.AuthorEntity;
import com.example.app.database.jpa.book.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSuccessfullyCreateAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthorEntityA();
        String authorJson = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testSuccessfullySaveAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthorEntityA();
        String authorJson = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        authorService.save(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].age").value(80)
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        ResponseAuthorDto responseAuthorDto = authorService.save(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + responseAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExist() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        ResponseAuthorDto responseAuthorDto = authorService.save(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + responseAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(responseAuthorDto.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus4200WhenAuthorExists() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        ResponseAuthorDto savedAuthor = authorService.save(testAuthorDtoA);

        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        ResponseAuthorDto savedAuthor = authorService.save(testAuthorDtoA);

        AuthorEntity authorDto = TestDataUtils.createTestAuthorEntityA();
        authorDto.setId(savedAuthor.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus20Ok() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        ResponseAuthorDto savedAuthor = authorService.save(testAuthorDtoA);
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        ResponseAuthorDto savedAuthor = authorService.save(testAuthorDtoA);
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDtoA.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception {
        CreateAuthorDto testAuthorDtoA = TestDataUtils.createTestCreateAuthorDtoA();
        ResponseAuthorDto savedAuthor = authorService.save(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
