package boats.utils;

import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.view.CharterHomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChartHomeViewImpl implements ChartHomeView {

    @Override
    public List<CharterHomeViewModel> activeCharters(List<CharterServiceModel> charterServiceModel) {

        List<CharterHomeViewModel> activeCharters = new ArrayList<>();

        if (charterServiceModel.size() > 0 ){
            for (CharterServiceModel serviceModel : charterServiceModel) {

                CharterHomeViewModel charter = new CharterHomeViewModel();

                charter.setBoatName(serviceModel.getBoat().getName());
                charter.setDestination(serviceModel.getDirection().getDestination());
                charter.setCustomerName(serviceModel.getCustomer().getFirstName() + " " + serviceModel.getCustomer().getLastName());
                charter.setEndDate(serviceModel.getStartDate().plusDays(serviceModel.getDirection().getPeriod()));

                activeCharters.add(charter);
            }
        }
        return activeCharters;
    }
}
