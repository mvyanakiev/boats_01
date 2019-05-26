package boats.utils;

import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.view.ContractViewModel;

public class ContractConverterImpl implements ContractConverter {

    @Override
    public ContractViewModel convertCharterToContract(CharterServiceModel charter) {

        ContractViewModel model = new ContractViewModel();
        //todo

        model.setBoatName(charter.getBoat().getName());
        model.setBoatProducer(charter.getBoat().getProducer());
        model.setCustomerName(charter.getCustomer().getFirstName() + " " + charter.getCustomer().getLastName());
        model.setPrice(String.valueOf(charter.getPrice()));


        model.setStartDate(String.valueOf(charter.getStartDate()));

        return model;
    }


}


//private String boatName;
//    private String boatModel;
//    private String boatProducer;
//    private String customerName;
//    private String destination;
//    private String startDate;
//    private String today;
