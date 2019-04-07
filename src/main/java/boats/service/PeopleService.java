package boats.service;

import boats.domain.models.serviceModels.PeopleServiceModel;

import java.util.List;

public interface PeopleService {

    List<PeopleServiceModel> findAllPeoples();

    PeopleServiceModel findPeopleById(String id);

    List<PeopleServiceModel> findAllCustomers();

}
