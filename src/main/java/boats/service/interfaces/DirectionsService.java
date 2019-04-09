package boats.service.interfaces;

import boats.domain.models.serviceModels.DirectionServiceModel;

import java.util.List;

public interface DirectionsService {

    List<DirectionServiceModel> findAllDirections();

    DirectionServiceModel findDirectionById(String id);
}
