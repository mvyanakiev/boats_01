package boats.web.controller;


import boats.config.ConfigValues;
import boats.domain.entities.Direction;
import boats.repository.DirectionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DirectionControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectionRepository directionRepository;

    @Before
    public void init() {
        this.directionRepository.deleteAll();
    }


    @Test
    @WithMockUser("spring")
    public void T01_show_AllDirection_loggedUser() throws Exception {
        this.mockMvc
                .perform(get("/directions/show"))
                .andExpect(view().name("/directions/directions-all"));
    }


    @Test
    public void T02_show_AllDirection_without_logged_user() throws Exception {
        this.mockMvc
                .perform(get("/directions/show"))
                .andExpect(redirectedUrl(ConfigValues.LOCAL_DOMAIN + "/users/login"));
    }


    @Test
    @WithMockUser("spring")
    public void T03_show_AddDirection_loggedUser() throws Exception {
        this.mockMvc
                .perform(get("/directions/add"))
                .andExpect(view().name("/directions/direction-add"));
    }


    @Test
    public void T04_show_AddDirection_without_logged_user() throws Exception {
        this.mockMvc
                .perform(get("/directions/add"))
                .andExpect(redirectedUrl(ConfigValues.LOCAL_DOMAIN + "/users/login"));
    }


    @Test
    @WithMockUser("spring")
    public void T05_AddDirection_with_logged_user() throws Exception {

        Direction direction = new Direction();
        direction.setDestination("Valid");
        direction.setDistance(100);
        direction.setPeriod(4);
        direction.setPrice(BigDecimal.valueOf(1200));

        this.mockMvc
                .perform(post("/directions/add")
                        .param("destination", "Valid")
                        .param("distance", "100")
                        .param("period", "4")
                        .param("price", "1200")
                );

        Assert.assertEquals(1, this.directionRepository.findAll().size());
    }


    @Test
    public void T06_AddDirection_without_logged_user() throws Exception {

        this.mockMvc
                .perform(post("/directions/add")
                        .param("destination", "Valid")
                        .param("distance", "100")
                        .param("period", "4")
                        .param("price", "1200")
                )
                .andExpect(redirectedUrl(ConfigValues.LOCAL_DOMAIN + "/users/login"));
    }


    @Test
    @WithMockUser(roles={"ADMIN"})
    public void T07_EditDirection_with_logged_user() throws Exception {

        Direction direction = new Direction();
        direction.setDestination("Valid");
        direction.setDistance(100);
        direction.setPeriod(4);
        direction.setPrice(BigDecimal.valueOf(1200));

        this.directionRepository.saveAndFlush(direction);

        String id = this.directionRepository.findAll().get(0).getId();

        this.mockMvc
                .perform(post("/directions/edit/" + id)
                        .param("destination", "Edited Valid")
                        .param("distance", "100")
                        .param("period", "4")
                        .param("price", "1200")
                );
        Direction actual = this.directionRepository.findAll().get(0);

        Assert.assertEquals("Edited Valid", actual.getDestination());
    }
}
