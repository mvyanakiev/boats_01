package boats.service;

import boats.domain.models.serviceModels.DirectionServiceModel;
import boats.domain.models.serviceModels.PeopleServiceModel;
import boats.repository.DirectionRepository;
import boats.repository.PeopleRepository;
import boats.service.interfaces.DirectionsService;
import boats.service.interfaces.PeopleService;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PeopleServiceTests {

    private PeopleService peopleService;
    private PeopleServiceModel testPeople;
    private ValidationUtil validationUtil;

    @Autowired
    private PeopleRepository peopleRepository;
    private ModelMapper modelMapper;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.validationUtil = new ValidationUtilImpl();
        this.peopleService = new PeopleServiceImpl(this.peopleRepository, this.modelMapper, this.validationUtil);
        this.testPeople = createTestPeople();
    }

    @Test
    public void T01_peopleService_addPeople_With_Correct_Values_ReturnCorrect(){
        PeopleServiceModel actual = peopleService.addPeople(testPeople);
        PeopleServiceModel expected = this.modelMapper
                .map(this.peopleRepository.findAll().get(0), PeopleServiceModel.class);

        compareResult(actual, expected);
    }

    @Test(expected = Exception.class)
    public void T02_peopleService_addPeople_With_Incorrect_Email_ThrowException(){
        testPeople.setEmail(null);
        this.peopleService.addPeople(testPeople);
    }

    @Test(expected = Exception.class)
    public void T03_peopleService_addPeople_With_Incorrect_FirstName_ThrowException(){
        testPeople.setFirstName(null);
        this.peopleService.addPeople(testPeople);
    }

    @Test(expected = Exception.class)
    public void T04_peopleService_addPeople_With_Incorrect__Phone_ThrowException(){
        testPeople.setPhone("");
        this.peopleService.addPeople(testPeople);
    }

    @Test
    public void T05_peopleService_findByID_With_Correct_Values_ReturnCorrect(){
        PeopleServiceModel actual = peopleService.addPeople(testPeople);

        PeopleServiceModel expected = this.peopleService.findPeopleById(actual.getId());

        compareResult(actual, expected);
    }

    @Test(expected = Exception.class)
    public void T06_peopleService_findByID_With_Incorrect_ThrowException(){
        PeopleServiceModel actual = peopleService.addPeople(testPeople);
        PeopleServiceModel expected = this.peopleService.findPeopleById("Invalid id");
    }

    //fixme
    @Test
    public void T07_peopleService_editPeople_With_Correct_Values_ReturnCorrect(){
        PeopleServiceModel actual = peopleService.addPeople(testPeople);
        testPeople.setFirstName("Valid");

        this.peopleService.editPeople(testPeople);

        PeopleServiceModel expected = this.modelMapper
                .map(this.peopleRepository.findAll().get(0), PeopleServiceModel.class);

        compareResult(actual, expected);
    }

//    @Test(expected = Exception.class)
//    public void T08_directionService_editDirection_With_incorrect_ThrowException(){
//        DirectionServiceModel actual = directionsService.addDirection(testDirection);
//        testDirection.setDestination(null);
//
//        this.directionsService.editDirection(testDirection);
//    }

//    @Test
//    public void T09_directionService_findAllDirections_With_Correct_Values_ReturnCorrect(){
//        DirectionServiceModel actual1 = directionsService.addDirection(testDirection);
//
//        DirectionServiceModel actual2 = new DirectionServiceModel();
//        actual2.setDestination("Valid destination");
//        actual2.setPeriod(10);
//        actual2.setDistance(100);
//        actual2.setPrice(BigDecimal.ONE);
//
//        actual2 = directionsService.addDirection(actual2);
//
//        List<DirectionServiceModel> expectedDirections = this.directionsService.findAllDirections();
//
//        Assert.assertEquals(2, expectedDirections.size());
//
//        compareResult(actual1, expectedDirections.get(0));
//        compareResult(actual2, expectedDirections.get(1));
//    }

//    @Test
//    public void T10_directionService_findAllDirections_With_zero_ReturnCorrect(){
//        List<DirectionServiceModel> expectedDirections = this.directionsService.findAllDirections();
//        Assert.assertEquals(0, expectedDirections.size());
//    }

    private PeopleServiceModel createTestPeople() {
        PeopleServiceModel testPeople = new PeopleServiceModel();

        testPeople.setFirstName("John");
        testPeople.setLastName("Smith");
        testPeople.setEmail("jhon.smith@test.com");
        testPeople.setPhone("0123456789");
        testPeople.setAddress("Test address");
        testPeople.setCustomer(true);
        testPeople.setEmployee(false);
        testPeople.setSupplier(false);

        return testPeople;
    }

    private void compareResult(PeopleServiceModel actual, PeopleServiceModel expected){
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPhone(), actual.getPhone());
        Assert.assertEquals(expected.getAddress(), actual.getAddress());
        Assert.assertEquals(expected.isCustomer(), actual.isCustomer());
        Assert.assertEquals(expected.isSupplier(), actual.isSupplier());
        Assert.assertEquals(expected.isEmployee(), actual.isEmployee());
    }
}
