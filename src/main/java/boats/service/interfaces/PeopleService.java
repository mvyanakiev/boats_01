package boats.service.interfaces;

import boats.domain.models.serviceModels.PeopleServiceModel;

import java.util.List;

public interface PeopleService {

    List<PeopleServiceModel> findAllPeoples();

    PeopleServiceModel findPeopleById(String id);

    List<PeopleServiceModel> findAllCustomers();

    PeopleServiceModel addPeople(PeopleServiceModel peopleServiceModel);

    PeopleServiceModel editPeople(PeopleServiceModel peopleServiceModel);

}
