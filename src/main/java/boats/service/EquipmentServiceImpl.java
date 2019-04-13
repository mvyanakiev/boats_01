package boats.service;

import boats.domain.entities.Equipment;
import boats.domain.models.serviceModels.EquipmentServiceModel;
import boats.error.NotFoundExceptions;
import boats.repository.EquipmentRepository;
import boats.service.interfaces.EquipmentService;
import boats.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    @Autowired
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.equipmentRepository = equipmentRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public List<EquipmentServiceModel> findByBoatId(String boatId) {
        List<Equipment> boatEquipments = this.equipmentRepository.findEquipmentByBoat_Id(boatId);

        if (boatEquipments != null) {
            List<EquipmentServiceModel> equipment = boatEquipments
                    .stream()
                    .map(e -> this.modelMapper.map(e, EquipmentServiceModel.class))
                    .collect(Collectors.toList());
            return equipment;
        }
        return null;
    }

    @Override
    public EquipmentServiceModel findEquipmentById(String id) {
        Equipment equipment = this.equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Equipment not found!"));

        return this.modelMapper.map(equipment, EquipmentServiceModel.class);
    }

    @Override
    public List<EquipmentServiceModel> findAllEquipment() {
        return this.equipmentRepository.findAll().stream()
                .map(e -> this.modelMapper.map(e, EquipmentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentServiceModel addEquipment(EquipmentServiceModel equipmentServiceModel) {
        if (!this.validationUtil.isValid(equipmentServiceModel)) {
            throw new IllegalArgumentException("Not valid data in add service");
        }

        return saveEquipmentToDb(equipmentServiceModel);
    }

    @Override
    public EquipmentServiceModel editEquipment(EquipmentServiceModel equipmentServiceModel) {
        if (!this.validationUtil.isValid(equipmentServiceModel)) {
            throw new IllegalArgumentException("Not valid data in edit service");
        }

        return saveEquipmentToDb(equipmentServiceModel);
    }

    @Override
    public void deleteEquipment(String id) {

        this.equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Equipment not found!"));

        this.equipmentRepository.deleteById(id);
    }

    private EquipmentServiceModel saveEquipmentToDb(EquipmentServiceModel equipmentServiceModel) {

        Equipment equipment = this.modelMapper.map(equipmentServiceModel, Equipment.class);

        try {
            equipment = this.equipmentRepository.saveAndFlush(equipment);
            return this.modelMapper.map(equipment, EquipmentServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }
}