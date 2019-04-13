package boats.service;

import boats.domain.entities.Boat;
import boats.domain.models.serviceModels.EquipmentServiceModel;
import boats.domain.models.serviceModels.PeopleServiceModel;
import boats.repository.BoatRepository;
import boats.repository.EquipmentRepository;
import boats.repository.PeopleRepository;
import boats.service.interfaces.EquipmentService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EquipmentServiceTests {

    private EquipmentService equipmentService;
    private EquipmentServiceModel testEquipment;
    private ValidationUtil validationUtil;

    @Autowired
    private EquipmentRepository equipmentRepository;
    private ModelMapper modelMapper;

    @Autowired
    private BoatRepository boatRepository;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.validationUtil = new ValidationUtilImpl();
        this.equipmentService = new EquipmentServiceImpl(this.equipmentRepository, this.modelMapper, this.validationUtil);
        this.testEquipment = createTestEquipment();
    }

//    @Test
//    public void T00_peopleService_addPeople_With_Correct_Values_ReturnCorrect() {
//
//        Assert.assertEquals(testEquipment.getBoat().getName(), this.boatRepository.findAll().get(0).getName());
//    }


//    @Test
//    public void T01_peopleService_addPeople_With_Correct_Values_ReturnCorrect() {
//        PeopleServiceModel actual = peopleService.addPeople(testPeople);
//        PeopleServiceModel expected = this.modelMapper
//                .map(this.peopleRepository.findAll().get(0), PeopleServiceModel.class);
//
//        compareResult(actual, expected);
//    }
//
//    @Test(expected = Exception.class)
//    public void T02_peopleService_addPeople_With_Incorrect_Email_ThrowException() {
//        testPeople.setEmail(null);
//        this.peopleService.addPeople(testPeople);
//    }
//
//    @Test(expected = Exception.class)
//    public void T03_peopleService_addPeople_With_Incorrect_FirstName_ThrowException() {
//        testPeople.setFirstName(null);
//        this.peopleService.addPeople(testPeople);
//    }
//
//    @Test(expected = Exception.class)
//    public void T04_peopleService_addPeople_With_Incorrect__Phone_ThrowException() {
//        testPeople.setPhone("");
//        this.peopleService.addPeople(testPeople);
//    }
//
//    @Test
//    public void T05_peopleService_findByID_With_Correct_Values_ReturnCorrect() {
//        PeopleServiceModel actual = peopleService.addPeople(testPeople);
//
//        PeopleServiceModel expected = this.peopleService.findPeopleById(actual.getId());
//
//        compareResult(actual, expected);
//    }
//
//    @Test(expected = Exception.class)
//    public void T06_peopleService_findByID_With_Incorrect_ThrowException() {
//        PeopleServiceModel actual = peopleService.addPeople(testPeople);
//        PeopleServiceModel expected = this.peopleService.findPeopleById("Invalid id");
//    }
//
//    @Test
//    public void T07_peopleService_editPeople_With_Correct_Values_ReturnCorrect() {
//        PeopleServiceModel actual = peopleService.addPeople(testPeople);
//
//        actual.setId(this.peopleRepository.findAll().get(0).getId());
//        actual.setFirstName("Valid");
//
//        this.peopleService.editPeople(actual);
//
//        PeopleServiceModel expected = this.modelMapper
//                .map(this.peopleRepository.findAll().get(0), PeopleServiceModel.class);
//
//        compareResult(actual, expected);
//    }
//
//    @Test(expected = Exception.class)
//    public void T08_peopleService_editPeople_With_incorrect_ThrowException(){
//        PeopleServiceModel actual = peopleService.addPeople(testPeople);
//        actual.setFirstName(null);
//
//        this.peopleService.editPeople(actual);
//    }
//
//    @Test
//    public void T09_peopleService_findAllPeoples_With_Correct_Values_ReturnCorrect(){
//        PeopleServiceModel actual1 = peopleService.addPeople(testPeople);
//
//        PeopleServiceModel actual2 = new PeopleServiceModel();
//        actual2.setFirstName("Valid fName");
//        actual2.setLastName("Valid lName");
//        actual2.setPhone("Valid phone");
//        actual2.setEmail("Valid e-mail");
//        actual2.setAddress("Valid e-address");
//        actual2.setCustomer(true);
//        actual2.setEmployee(false);
//        actual2.setSupplier(false);
//
//        actual2 = peopleService.addPeople(actual2);
//
//        List<PeopleServiceModel> expectedPeoples = this.peopleService.findAllPeoples();
//
//        Assert.assertEquals(2, expectedPeoples.size());
//
//        compareResult(actual1, expectedPeoples.get(0));
//        compareResult(actual2, expectedPeoples.get(1));
//    }
//
//    @Test
//    public void T10_peopleService_findAllPeoples_With_zero_ReturnCorrect(){
//        List<PeopleServiceModel> expectedPeoples = this.peopleService.findAllPeoples();
//        Assert.assertEquals(0, expectedPeoples.size());
//    }
//
//    @Test
//    public void T11_peopleService_findAllCustomers_With_Correct_Values_ReturnCorrect(){
//        PeopleServiceModel actual1 = peopleService.addPeople(testPeople);
//
//        PeopleServiceModel actual2 = new PeopleServiceModel();
//        actual2.setFirstName("Valid fName");
//        actual2.setLastName("Valid lName");
//        actual2.setPhone("Valid phone");
//        actual2.setEmail("Valid e-mail");
//        actual2.setAddress("Valid e-address");
//        actual2.setCustomer(false);
//        actual2.setEmployee(true);
//        actual2.setSupplier(true);
//
//        actual2 = peopleService.addPeople(actual2);
//
//        List<PeopleServiceModel> expectedPeoples = this.peopleService.findAllCustomers();
//
//        Assert.assertEquals(1, expectedPeoples.size());
//        Assert.assertTrue(this.peopleRepository.findAll().get(0).isCustomer());
//    }

    private EquipmentServiceModel createTestEquipment() {
        EquipmentServiceModel testEquipment = new EquipmentServiceModel();

        Boat testBoat = new Boat();
        testBoat.setName("Test Boat name");
        testBoat.setProducer("Test Boat producer");
        testBoat.setModel("Test Boat Model");
        testBoat.setLastCheckedDate(LocalDate.parse("2018-08-08"));
        testBoat.setPrice(BigDecimal.valueOf(1200));
        this.boatRepository.saveAndFlush(testBoat);


        testEquipment.setItem("Valid equipment");
        testEquipment.setSerialNumber("123");
        testEquipment.setLastCheckedDate(LocalDate.parse("2018-02-02"));
        testEquipment.setBoat(this.boatRepository.findAll().get(0));

        return testEquipment;
    }

    private void compareResult(EquipmentServiceModel actual, EquipmentServiceModel expected) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getItem(), actual.getItem());
        Assert.assertEquals(expected.getBoat().getId(), actual.getBoat().getId());
        Assert.assertEquals(expected.getSerialNumber(), actual.getSerialNumber());
        Assert.assertEquals(expected.getLastCheckedDate(), actual.getLastCheckedDate());

    }
}
