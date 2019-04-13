package boats.service.interfaces;

import boats.domain.models.serviceModels.EquipmentServiceModel;

import java.util.List;

public interface EquipmentService {

    List<EquipmentServiceModel> findByBoatId(String boatId);

    EquipmentServiceModel findEquipmentById(String id);

    List<EquipmentServiceModel> findAllEquipment();

    EquipmentServiceModel addEquipment(EquipmentServiceModel equipmentServiceModel);

    EquipmentServiceModel editEquipment(EquipmentServiceModel equipmentServiceModel);

    void deleteEquipment(String id);
}