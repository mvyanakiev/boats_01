package boats.service;


import boats.domain.models.serviceModels.UserServiceModel;
import boats.repository.RoleRepository;
import boats.repository.UserRepository;
import boats.service.interfaces.RoleService;
import boats.service.interfaces.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTests {

    private UserService userService;
    private UserServiceModel testUser;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleService roleService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.roleService = new RoleServiceImpl(this.roleRepository, this.modelMapper);
        this.userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.bCryptPasswordEncoder);
        this.testUser = createTestUser();
    }



    @Test
    public void T01_userService_registerUser_with_correct_data_return_ok() {
        this.userService.registerUser(testUser);
        long expected = this.userRepository.findAll().size();

        Assert.assertEquals(1, expected);
    }




    private UserServiceModel createTestUser() {

        UserServiceModel testUser = new UserServiceModel();
        testUser.setUsername("john");
        testUser.setPassword("1");
        testUser.setEmail("valid@mail.com");

        return testUser;
    }
}
