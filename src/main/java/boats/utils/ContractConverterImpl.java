package boats.utils;

import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.serviceModels.EquipmentServiceModel;
import boats.domain.models.view.ContractViewModel;
import boats.service.EquipmentServiceImpl;
import boats.service.interfaces.EquipmentService;

import java.time.LocalDate;
import java.util.List;

public class ContractConverterImpl implements ContractConverter {

//    private final EquipmentService equipmentService;

//    public ContractConverterImpl() {
//        this.equipmentService = new EquipmentServiceImpl();
//    }


    @Override
    public ContractViewModel convertCharterToContract(CharterServiceModel charter) {

        ContractViewModel model = new ContractViewModel();

        model.setBoatName(charter.getBoat().getName());
        model.setBoatProducer(charter.getBoat().getProducer());
        model.setBoatModel(charter.getBoat().getModel());
        model.setCustomerName(charter.getCustomer().getFirstName() + " " + charter.getCustomer().getLastName());
        model.setDirection(charter.getDirection().getDestination());
        model.setPrice(String.valueOf(charter.getPrice()));
        model.setPeriod(String.valueOf(charter.getDirection().getPeriod()));
        model.setStartDate(charter.getStartDate());
        model.setToday(LocalDate.now());
//        model.setEquipment(this.equipmentService.findByBoatId(charter.getBoat().getId())); //fixme

        return model;
    }
}
