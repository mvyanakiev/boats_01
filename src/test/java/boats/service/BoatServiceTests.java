package boats.service;


import boats.domain.entities.Boat;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.repository.BoatRepository;
import boats.repository.CharterRepository;
import boats.repository.DirectionRepository;
import boats.service.interfaces.BoatService;
import boats.service.interfaces.CharterService;
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
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BoatServiceTests {

    private BoatService boatService;
    private BoatServiceModel testBoat;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private CharterService charterService;
    private DirectionsService directionsService;

    @Autowired
    private CharterRepository charterRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.validationUtil = new ValidationUtilImpl();
        this.directionsService = new DirectionsServiceImpl(this.directionRepository, this.modelMapper, this.validationUtil);
        this.charterService = new CharterServiceImpl(this.charterRepository, this.modelMapper, this.validationUtil);
        this.boatService = new BoatServiceImpl(
                this.boatRepository,
                this.modelMapper,
                this.validationUtil,
                this.directionsService,
                this.charterService);
        this.testBoat = createTestBoat();
    }

    @Test
    public void T01_boatService_addBoat_With_Correct_Data_ReturnCorrect(){

        BoatServiceModel actual = this.boatService.addBoat(testBoat);
        BoatServiceModel expected = this.modelMapper.map(this.boatRepository.findAll().get(0), BoatServiceModel.class);

        compareResult(expected, actual);
    }

    @Test(expected = Exception.class)
    public void T01_boatService_addBoat_With_Incorrect_Data_Throw_Exception(){

        testBoat.setModel(null);
        this.boatService.addBoat(testBoat);
    }

    @Test
    public void T03_boatService_saveEditedBoat_With_Correct_Data_ReturnCorrect(){

        BoatServiceModel actual = this.boatService.addBoat(testBoat);
        actual = this.modelMapper.map(this.boatRepository.findAll().get(0), BoatServiceModel.class);
        actual.setName("New valid name");
        this.boatService.saveEditedBoat(actual);
        BoatServiceModel expected = this.modelMapper.map(this.boatRepository.findAll().get(0), BoatServiceModel.class);

        compareResult(expected, actual);
    }

    @Test(expected = Exception.class)
    public void T04_boatService_saveEditedBoat_With_Incorrect_Data_Throw_Exception(){

        BoatServiceModel actual = this.boatService.addBoat(testBoat);
        actual = this.modelMapper.map(this.boatRepository.findAll().get(0), BoatServiceModel.class);
        actual.setName(null);
        this.boatService.saveEditedBoat(actual);
    }

    @Test
    public void T05_boatService_findAllBoats_With_Correct_Data_ReturnCorrect(){

        Boat tb1 = this.boatRepository.saveAndFlush(this.modelMapper.map(testBoat, Boat.class));
        Boat tb2 = new Boat();

        tb2.setName("New valid name");
        tb2.setProducer("Test Boat producer");
        tb2.setModel("Test Boat Model");
        tb2.setLastCheckedDate(LocalDate.parse("2018-08-08"));
        tb2.setPrice(BigDecimal.valueOf(1200));

        this.boatRepository.saveAndFlush(tb2);
        List<BoatServiceModel> excepted = this.boatService.findAllBoats();

        Assert.assertEquals(2, excepted.size());
    }

    @Test
    public void T06_boatService_findAllBoats_With_Correct_Data_ReturnCorrect(){

        List<BoatServiceModel> excepted = this.boatService.findAllBoats();
        Assert.assertEquals(0, excepted.size());
    }

    @Test
    public void T07_boatService_findBoatById_With_Correct_Data_ReturnCorrect(){

        BoatServiceModel actual = this.boatService.addBoat(testBoat);
        BoatServiceModel expected = this.boatService.findBoatById(actual.getId());

        compareResult(expected, actual);
    }

    @Test(expected = Exception.class)
    public void T08_boatService_findBoatById_With_Incorrect_Data_Throw_Exception(){

        this.boatService.findBoatById("Invalid id");
    }

    @Test
    public void T09_boatService_findBoatsNeedToCheckWith_Correct_Data_ReturnCorrect(){

        testBoat.setLastCheckedDate(LocalDate.now().minusDays(360));
        this.boatService.addBoat(testBoat);

        List<BoatServiceModel> excepted = this.boatService.findBoatsNeedToCheck();

        Assert.assertEquals(1, excepted.size());

    }










    private BoatServiceModel createTestBoat() {
        BoatServiceModel testBoat = new BoatServiceModel();

        testBoat.setName("Test Boat name");
        testBoat.setProducer("Test Boat producer");
        testBoat.setModel("Test Boat Model");
        testBoat.setLastCheckedDate(LocalDate.parse("2018-08-08"));
        testBoat.setPrice(BigDecimal.valueOf(1200));

        return testBoat;
    }

    private void compareResult(BoatServiceModel excepted, BoatServiceModel actual){

        Assert.assertEquals(excepted.getId(), actual.getId());
        Assert.assertEquals(excepted.getName(), actual.getName());
        Assert.assertEquals(excepted.getModel(), actual.getModel());
        Assert.assertEquals(excepted.getProducer(), actual.getProducer());
        Assert.assertEquals(excepted.getPrice(), actual.getPrice());
        Assert.assertEquals(excepted.getLastCheckedDate(), actual.getLastCheckedDate());
    }


}
