package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.services.UserService;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void clean() {
        userService.removeAllUsers();
    }

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Ada Lovelace\",\n" +
                                "  \"email\": \"adal@example.com\"\n" +
                                "}")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Ada Lovelace"))
                .andExpect(jsonPath("$.email").value("adal@example.com"));
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception {
        // Primer afegeix un usuari amb POST
        // Després GET /users/{id} i comprova que torni aquest usuari

        MvcResult result = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Ada Lovelace\",\n" +
                        "  \"email\": \"adalovelace@example.com\"\n" +
                        "}")).andReturn();
        String response = result.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id").toString();
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Ada Lovelace"))
                .andExpect(jsonPath("$.email").value("adalovelace@example.com"));
    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {

        mockMvc.perform(get("/users/" + UUID.randomUUID().toString())).andExpect(status().isNotFound());

    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Ada Lovelace\",\n" +
                        "  \"email\": \"ada@example.com\"\n" +
                        "}"));

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Joan Pere\",\n" +
                        "  \"email\": \"joan@example.com\"\n" +
                        "}"));

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Mojo Juan\",\n" +
                        "  \"email\": \"mojo@example.com\"\n" +
                        "}"));

        MvcResult mvcResult = mockMvc.perform(get("/users?name=jo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn();
        List<String> names = JsonPath.read((mvcResult.getResponse()), "$[*].name");
        Assertions.assertThat(names).allMatch(name -> name.toLowerCase().contains("jo"));
    }

    @Test
    void getUserById_invalidId() throws Exception {
        mockMvc.perform(get("/users/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }
}
