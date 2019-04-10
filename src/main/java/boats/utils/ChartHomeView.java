package boats.utils;

import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.view.CharterHomeViewModel;

import java.util.List;

public interface ChartHomeView {

    List<CharterHomeViewModel> activeCharters(List<CharterServiceModel> charterServiceModel);
}
