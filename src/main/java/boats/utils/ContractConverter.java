package boats.utils;

import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.view.ContractViewModel;

public interface ContractConverter {

    ContractViewModel convertCharterToContract(CharterServiceModel charter);
}
