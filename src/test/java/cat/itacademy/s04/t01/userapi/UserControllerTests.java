package cat.itacademy.s04.t01.userapi;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;
import cat.itacademy.s04.t01.userapi.controllers.UserController;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
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
	private ObjectMapper objectMapper;

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
				"  \"email\": \"ada@example.com\"\n" +
				"}")).andExpect(jsonPath("$.id").exists());
	}

	@Test
	void getUserById_returnsCorrectUser() {
		// Primer afegeix un usuari amb POST
		// Després GET /users/{id} i comprova que torni aquest usuari
		try {
			MvcResult result = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
					.content("{\n" +
					"  \"name\": \"Ada Lovelace\",\n" +
					"  \"email\": \"ada@example.com\"\n" +
					"}")).andReturn();
			String response = result.getResponse().getContentAsString();
			String id = JsonPath.parse(response).read("$.id").toString();
			mockMvc.perform(get("/users/{id}",id))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").value(id))
					.andExpect(jsonPath("$.name").value("Ada Lovelace"))
					.andExpect(jsonPath("$.email").value("ada@example.com"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void getUserById_returnsNotFoundIfMissing() {
        try {
            mockMvc.perform(get("/users/" + UUID.randomUUID().toString())).andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	@Test
	void getUsers_withNameParam_returnsFilteredUsers() {
        try {
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
					.andReturn();
			List<String> names = JsonPath.read((mvcResult.getResponse()),"$[*].name");
			Assertions.assertThat(names).allMatch(name -> name.toLowerCase().contains("jo"));
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
