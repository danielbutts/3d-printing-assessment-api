package com.github.danielbutts.partsanalyzer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.Random;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by danielbutts on 7/5/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserRepository repository;

    @Test
    public void testGetAllUsers() throws Exception {
        RequestBuilder request = get("/users");

        Long id1 = new Random().nextLong();
        User user1 = new User();
        user1.setFirstName("Test");
        user1.setLastName("Person");
        user1.setEmail("test@example.com");
        user1.setCompany("Example");
        user1.setId(id1);

        ArrayList<User> users = new ArrayList<User>();
        users.add(user1);

        when(this.repository.findAll()).thenReturn(users);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{ firstName:\"Test\", lastName :\"Person\", email:\"test@example.com\", " +
                        "company:\"Example\"}]"));
    }

    @Test
    public void testGetOneUser() throws Exception {

        Long id = new Random().nextLong();
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Person");
        user.setEmail("test@example.com");
        user.setCompany("Example");
        user.setId(id);

        when(this.repository.findUserById(id)).thenReturn(user);

        MockHttpServletRequestBuilder request = get(String.format("/users/%d", id));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{ firstName:\"Test\", lastName :\"Person\", email:\"test@example.com\", " +
                        "company:\"Example\"}"));
    }

    @Test
    public void testPatch() throws Exception {
        Long id = new Random().nextLong();
        User user = new User();
        user.setFirstName("Dwayne");
        user.setLastName("Johnson");
        user.setId(id);

        when(this.repository.findUserById(user.getId())).thenReturn(user);
        when(this.repository.save(any(User.class))).thenReturn(user);

        MockHttpServletRequestBuilder request = patch("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":"+id+",\"firstName\": \"Timmy\",\"company\": \"Example\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Timmy") ))
                .andExpect(jsonPath("$.lastName", equalTo("Johnson") ))
                .andExpect(jsonPath("$.company", equalTo("Example") ));
    }

    @Test(expected = Exception.class)
    public void testCreateWithMissingRequiredFirstNameParam() throws Exception {
       MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"lastName\": \"Johnson\"," +
                        "\"email\": \"email@company.com\",\"company\": \"Example\"}");

        this.mvc.perform(request);
    }

    @Test
    public void testCreate() throws Exception {
        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\": \"Sam\", \"lastName\": \"Johnson\"," +
                        "\"email\": \"email@company.com\",\"company\": \"Example\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk());
    }

}
