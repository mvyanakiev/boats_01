package boats.service.interfaces;

import boats.domain.models.serviceModels.BoatServiceModel;

import java.util.List;

public interface BoatService {

    BoatServiceModel addBoat(BoatServiceModel boatServiceModel);

    List<BoatServiceModel> findAllBoats();

    BoatServiceModel findBoatById(String id);

    BoatServiceModel saveEditedBoat(BoatServiceModel boatServiceModel);

    List<BoatServiceModel> findAvailableBoats(String startDate, String directionId);

    List<BoatServiceModel> findBoatsNeedToCheck();
}
