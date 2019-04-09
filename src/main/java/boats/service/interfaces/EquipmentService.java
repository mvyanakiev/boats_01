package boats.service.interfaces;

import boats.domain.models.serviceModels.EquipmentServiceModel;

import java.util.List;

public interface EquipmentService {

    List<EquipmentServiceModel> findByBoatId(String boatId);


}
