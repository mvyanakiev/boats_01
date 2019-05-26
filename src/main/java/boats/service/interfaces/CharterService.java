package boats.service.interfaces;

import boats.domain.models.serviceModels.CharterServiceModel;

import java.util.List;

public interface CharterService {

    CharterServiceModel addCharter(CharterServiceModel charterServiceModel);

    List<CharterServiceModel> findAllCharters();

    List<CharterServiceModel> findActiveCharters();

    void deleteCharter(String id);

    CharterServiceModel findByCharterId(String id);
}
