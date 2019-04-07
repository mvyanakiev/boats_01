package boats.service;

import boats.domain.entities.People;
import boats.domain.models.serviceModels.PeopleServiceModel;
import boats.repository.PeopleRepository;
import boats.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService{

    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public PeopleServiceImpl(PeopleRepository peopleRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public List<PeopleServiceModel> findAllPeoples() {
        return this.peopleRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"))
                .stream()
                .map(b -> this.modelMapper.map(b, PeopleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PeopleServiceModel findPeopleById(String id) {
        People people = this.peopleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("People not found!"));

        return this.modelMapper.map(people, PeopleServiceModel.class);
    }

    @Override
    public List<PeopleServiceModel> findAllCustomers() {
        return this.peopleRepository.findPeopleByCustomerIsTrue()
                .stream()
                .map(p -> this.modelMapper.map(p, PeopleServiceModel.class))
                .collect(Collectors.toList());
    }


}
