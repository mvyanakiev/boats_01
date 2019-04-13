package boats.service;

import boats.domain.entities.Boat;
import boats.domain.entities.Equipment;
import boats.domain.models.serviceModels.EquipmentServiceModel;
import boats.domain.models.serviceModels.PeopleServiceModel;
import boats.repository.BoatRepository;
import boats.repository.EquipmentRepository;
import boats.repository.PeopleRepository;
import boats.service.interfaces.EquipmentService;
import boats.service.interfaces.PeopleService;
import boats.utils.ValidationUtil;
import boats.utils.ValidationUtilImpl;
import org.junit.After;
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

    @After
    public void reset(){
        this.testEquipment = null;
    }

    @Test
    public void T00_verify_boat_added_correct() {

        Assert.assertEquals(
                testEquipment.getBoat().getName(),
                this.boatRepository.findAll().get(0).getName()
        );
    }

    @Test
    public void T01_equipmentService_addEquipment_With_Correct_Values_ReturnCorrect() {
        EquipmentServiceModel actual = equipmentService.addEquipment(testEquipment);
        EquipmentServiceModel expected = this.modelMapper
                .map(this.equipmentRepository.findAll().get(0), EquipmentServiceModel.class);

        compareResult(actual, expected);
    }

    @Test(expected = Exception.class)
    public void T02_equipmentService_addEquipment_With_Incorrect_Item_ThrowException() {
        testEquipment.setItem(null);
        this.equipmentService.addEquipment(testEquipment);
    }

    @Test
    public void T03_equipmentService_editEquipment_With_Correct_Values_ReturnCorrect() {

        EquipmentServiceModel actual = this.equipmentService.addEquipment(testEquipment);

        actual = this.modelMapper.map(this.equipmentRepository.findAll().get(0), EquipmentServiceModel.class);
        actual.setItem("New valid item");
        this.equipmentService.editEquipment(actual);

        EquipmentServiceModel expected = this.modelMapper.map(this.equipmentRepository.findAll().get(0), EquipmentServiceModel.class);

        compareResult(actual, expected);
    }


    @Test(expected = Exception.class)
    public void T04_equipmentService_editEquipment_With_Incorrect_Item_ThrowException() {
        EquipmentServiceModel actual = this.equipmentService.addEquipment(testEquipment);

        actual = this.modelMapper.map(this.equipmentRepository.findAll().get(0), EquipmentServiceModel.class);
        actual.setItem(null);
        this.equipmentService.editEquipment(actual);
    }

    @Test
    public void T05_equipmentService_findByBoatId_With_Correct_Values_ReturnCorrect(){
        String boatId = testEquipment.getBoat().getId();

        //todo
//        List<EquipmentServiceModel> expected = this.equipmentService.findByBoatId(boatId);
//        compareResult(testEquipment, this.modelMapper.map(expected.get(0), ));
    }

    @Test
    public void T06_equipmentService_findEquipmentById_With_Correct_Values_ReturnCorrect(){

        Equipment eq = this.equipmentRepository.saveAndFlush(this.modelMapper.map(testEquipment, Equipment.class));
        EquipmentServiceModel expected = this.equipmentService.findEquipmentById(eq.getId());

        compareResult(this.modelMapper.map(eq, EquipmentServiceModel.class), expected);
    }


    @Test(expected = Exception.class)
    public void T07_equipmentService_findEquipmentById_With_With_Incorrect_Id_ThrowException(){

        Equipment eq = this.equipmentRepository.saveAndFlush(this.modelMapper.map(testEquipment, Equipment.class));
        EquipmentServiceModel expected = this.equipmentService.findEquipmentById("invalid id");

        compareResult(this.modelMapper.map(eq, EquipmentServiceModel.class), expected);
    }

    @Test
    public void T08_equipmentService_findAllEquipment_ReturnCorrect(){
        this.equipmentRepository.saveAndFlush(this.modelMapper.map(testEquipment, Equipment.class));
        this.equipmentRepository.saveAndFlush(this.modelMapper.map(testEquipment, Equipment.class));

        List<EquipmentServiceModel> expected = this.equipmentService.findAllEquipment();

        Assert.assertEquals(2, expected.size());
    }

    @Test
    public void T09_equipmentService_findAllEquipment_ReturnCorrect(){
        List<EquipmentServiceModel> expected = this.equipmentService.findAllEquipment();

        Assert.assertEquals(0, expected.size());
    }

    @Test
    public void T10_equipmentService_deleteEquipment_With_Correct_Id_ReturnCorrect(){
        this.equipmentRepository.saveAndFlush(this.modelMapper.map(testEquipment, Equipment.class));
        this.equipmentRepository.saveAndFlush(this.modelMapper.map(testEquipment, Equipment.class));

        Equipment equipmentToDelete = this.equipmentRepository.findAll().get(0);

        this.equipmentService.deleteEquipment(equipmentToDelete.getId());
        Assert.assertEquals(1, this.equipmentRepository.findAll().size());
    }


    @Test(expected = Exception.class)
    public void T11_equipmentService_deleteEquipment_With_Incorrect_Id_ThrowException(){
        this.equipmentRepository.saveAndFlush(this.modelMapper.map(testEquipment, Equipment.class));

        this.equipmentService.deleteEquipment("invalid id");
    }




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
