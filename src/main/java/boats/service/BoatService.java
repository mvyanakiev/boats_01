package boats.service;

import boats.domain.models.serviceModels.BoatServiceModel;

import java.util.List;

public interface BoatService {

    BoatServiceModel addBoat(BoatServiceModel boatServiceModel);

    List<BoatServiceModel> findAllBoats();

    BoatServiceModel findBoatById(String id);

    BoatServiceModel saveEditedBoat(BoatServiceModel boatServiceModel);
}
