package boats.service;

import boats.domain.entities.Boat;
import boats.domain.entities.Charter;
import boats.domain.entities.Direction;
import boats.domain.entities.People;
import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.serviceModels.DirectionServiceModel;
import boats.repository.BoatRepository;
import boats.repository.CharterRepository;
import boats.repository.DirectionRepository;
import boats.repository.PeopleRepository;
import boats.service.interfaces.CharterService;
import boats.utils.ValidationUtil;
import boats.utils.ValidationUtilImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CharterServiceTests {

    private CharterService charterService;
    private CharterServiceModel testCharter;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;

    @Autowired
    private CharterRepository charterRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private DirectionRepository directionRepository;


    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.validationUtil = new ValidationUtilImpl();
        this.charterService = new CharterServiceImpl(this.charterRepository, this.modelMapper, this.validationUtil);
        this.testCharter = createTestCharter();
    }

    @Test
    public void T01_charterService_addCharter_With_Correct_Values_ReturnCorrect(){
        CharterServiceModel actual = charterService.addCharter(testCharter);

        CharterServiceModel expected = this.modelMapper
                .map(this.charterRepository.findAll().get(0), CharterServiceModel.class);

        compareResult(actual, expected);
    }

    @Test(expected = Exception.class)
    public void T02_charterService_addCharter_With_Incorrect_Direction_ThrowException(){
        testCharter.setDirection(null);
        this.charterService.addCharter(testCharter);
    }

    @Test(expected = Exception.class)
    public void T03_charterService_addCharter_With_Incorrect_Boat_ThrowException(){
        testCharter.setBoat(null);
        this.charterService.addCharter(testCharter);
    }

    @Test(expected = Exception.class)
    public void T04_charterService_addCharter_With_Incorrect_Customer_ThrowException(){
        testCharter.setCustomer(null);
        this.charterService.addCharter(testCharter);
    }

    @Test(expected = Exception.class)
    public void T05_charterService_addCharter_With_Incorrect_Price_ThrowException(){
        testCharter.setPrice(null);
        this.charterService.addCharter(testCharter);
    }

    @Test(expected = Exception.class)
    public void T06_charterService_addCharter_With_Incorrect_StartDate_ThrowException(){
        testCharter.setStartDate(null);
        this.charterService.addCharter(testCharter);
    }

    @Test
    public void T07_charterService_findAllCharters_With_Correct_Values_ReturnCorrect(){
        CharterServiceModel actual1 = charterService.addCharter(testCharter);
        CharterServiceModel actual2 = charterService.addCharter(testCharter);

        List<CharterServiceModel> expected = this.charterService.findAllCharters();

        Assert.assertEquals(2, expected.size());
    }

    @Test
    public void T08_charterService_deleteCharter_With_Correct_Values_ReturnCorrect(){
        CharterServiceModel actual1 = charterService.addCharter(testCharter);
        CharterServiceModel actual2 = charterService.addCharter(testCharter);

        this.charterService.deleteCharter(actual1.getId());

        List<CharterServiceModel> expected = this.charterService.findAllCharters();

        Assert.assertEquals(1, expected.size());
        Assert.assertEquals(actual2.getId(), this.charterRepository.findAll().get(0).getId());
    }

    @Test
    public void T09_charterService_findActiveCharters_With_Correct_Values_ReturnCorrect(){
        CharterServiceModel actual1 = charterService.addCharter(testCharter);

        CharterServiceModel actual2 = this.modelMapper.map(this.charterRepository.findAll().get(0), CharterServiceModel.class);
        actual2.setStartDate(LocalDate.now().minusDays(2));
        this.charterRepository.saveAndFlush(this.modelMapper.map(actual2, Charter.class));


        List<CharterServiceModel> expected = this.charterService.findActiveCharters();

        Assert.assertEquals(1, expected.size());
        Assert.assertEquals(actual2.getId(), this.charterRepository.findAll().get(0).getId());
    }


    private CharterServiceModel createTestCharter() {
        CharterServiceModel testCharter = new CharterServiceModel();

        Boat testBoat = new Boat();
        testBoat.setName("Test Boat name");
        testBoat.setProducer("Test Boat producer");
        testBoat.setModel("Test Boat Model");
        testBoat.setLastCheckedDate(LocalDate.parse("2018-08-08"));
        testBoat.setPrice(BigDecimal.valueOf(1200));
        testBoat = this.boatRepository.saveAndFlush(testBoat);

        People testCustomer = new People();
        testCustomer.setFirstName("Test Customer mame");
        testCustomer.setEmail("Test Customer e-mail");
        testCustomer.setPhone("12345678");
        testCustomer.setCustomer(true);
        testCustomer = this.peopleRepository.saveAndFlush(testCustomer);

        Direction testDirection = new Direction();
        testDirection.setDestination("Test destination");
        testDirection.setPeriod(29);
        testDirection.setPrice(BigDecimal.valueOf(2300));
        testDirection = this.directionRepository.saveAndFlush(testDirection);

        testCharter.setDirection(testDirection);
        testCharter.setBoat(testBoat);
        testCharter.setCustomer(testCustomer);
        testCharter.setPrice(BigDecimal.valueOf(120));
        testCharter.setStartDate(LocalDate.now().plusMonths(6));

        return testCharter;
    }

    private void compareResult(CharterServiceModel actual, CharterServiceModel expected) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getBoat().getId(), actual.getBoat().getId());
        Assert.assertEquals(expected.getCustomer().getId(), actual.getCustomer().getId());
        Assert.assertEquals(expected.getDirection().getId(), actual.getDirection().getId());
    }
}
