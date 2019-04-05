package boats.service;

import boats.domain.entities.Equipment;
import boats.domain.models.serviceModels.EquipmentServiceModel;
import boats.domain.models.view.EqupmentViewModel;
import boats.repository.EquipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, ModelMapper modelMapper) {
        this.equipmentRepository = equipmentRepository;
        this.modelMapper = modelMapper;
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
}
