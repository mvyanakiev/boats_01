package boats.service;

import boats.domain.models.serviceModels.DirectionServiceModel;
import boats.repository.DirectionRepository;
import boats.service.interfaces.DirectionsService;
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

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DirectionServiceTests {

    private DirectionsService directionsService;
    private DirectionServiceModel testDirection;
    private ValidationUtil validationUtil;

    @Autowired
    private DirectionRepository directionRepository;
    private ModelMapper modelMapper;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.validationUtil = new ValidationUtilImpl();
        this.directionsService = new DirectionsServiceImpl(this.directionRepository, this.modelMapper, this.validationUtil);
        this.testDirection = createTestDirection();
    }

    @Test
    public void T01_directionService_addDirectionWithCorrectValues_ReturnCorrect(){
        DirectionServiceModel actual = directionsService.addDirection(testDirection);
        DirectionServiceModel expected = this.modelMapper
                .map(this.directionRepository.findAll().get(0), DirectionServiceModel.class);

        compareResult(actual, expected);
    }

    @Test(expected = Exception.class)
    public void T02_directionService_addDirectionWithIncorrect_Destination_ThrowException(){
        testDirection.setDestination(null);
        this.directionsService.addDirection(testDirection);
    }

    @Test(expected = Exception.class)
    public void T03_directionService_addDirectionWithIncorrect_Period_ThrowException(){
        testDirection.setPeriod(-1);
        this.directionsService.addDirection(testDirection);
    }

    @Test(expected = Exception.class)
    public void T04_directionService_addDirectionWithIncorrect_Price_ThrowException(){
        testDirection.setPrice(BigDecimal.ZERO);
        this.directionsService.addDirection(testDirection);
    }

    @Test
    public void T05_directionService_findByID_correct_returnCorrect(){
        DirectionServiceModel actual = directionsService.addDirection(testDirection);

        DirectionServiceModel expected = this.directionsService.findDirectionById(actual.getId());

        compareResult(actual, expected);
    }

    @Test(expected = Exception.class)
    public void T06_directionService_findByID_incorrect_ThrowException(){
        DirectionServiceModel actual = directionsService.addDirection(testDirection);

        DirectionServiceModel expected = this.directionsService.findDirectionById("Invalid id");

        compareResult(actual, expected);
    }

    @Test
    public void T07_directionService_editDirectionWithCorrectValues_ReturnCorrect(){
        DirectionServiceModel actual = directionsService.addDirection(testDirection);
        testDirection.setDestination("New Destination");

        this.directionsService.editDirection(testDirection);

        DirectionServiceModel expected = this.modelMapper
                .map(this.directionRepository.findAll().get(0), DirectionServiceModel.class);

        compareResult(actual, expected);
    }

    @Test(expected = Exception.class)
    public void T08_directionService_editDirectionWith_incorrectValues_ThrowException(){
        DirectionServiceModel actual = directionsService.addDirection(testDirection);
        testDirection.setDestination(null);

        this.directionsService.editDirection(testDirection);
    }

    @Test
    public void T09_directionService_findAllDirections_With_Correct_Values_ReturnCorrect(){
        DirectionServiceModel actual1 = directionsService.addDirection(testDirection);

        DirectionServiceModel actual2 = new DirectionServiceModel();
        actual2.setDestination("Valid destination");
        actual2.setPeriod(10);
        actual2.setDistance(100);
        actual2.setPrice(BigDecimal.ONE);

        actual2 = directionsService.addDirection(actual2);

        List<DirectionServiceModel> expectedDirections = this.directionsService.findAllDirections();

        Assert.assertEquals(2, expectedDirections.size());

        compareResult(actual1, expectedDirections.get(0));
        compareResult(actual2, expectedDirections.get(1));
    }

    @Test
    public void T10_directionService_findAllDirections_With_zero_ReturnCorrect(){
        List<DirectionServiceModel> expectedDirections = this.directionsService.findAllDirections();
        Assert.assertEquals(0, expectedDirections.size());
    }

    private DirectionServiceModel createTestDirection() {
        DirectionServiceModel testDirection = new DirectionServiceModel();

        testDirection.setDestination("Corfu");
        testDirection.setDistance(210);
        testDirection.setPeriod(7);
        testDirection.setPrice(BigDecimal.TEN);

        return testDirection;
    }

    private void compareResult(DirectionServiceModel actual, DirectionServiceModel expected){
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getDestination(), actual.getDestination());
        Assert.assertEquals(expected.getPeriod(), actual.getPeriod());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }
}
