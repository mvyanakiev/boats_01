package boats.service;

import boats.domain.entities.Boat;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.repository.BoatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;
    private final ModelMapper modelMapper;

    public BoatServiceImpl(BoatRepository boatRepository, ModelMapper modelMapper) {
        this.boatRepository = boatRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BoatServiceModel addBoat(BoatServiceModel boatServiceModel) {
        Boat boat = this.modelMapper.map(boatServiceModel, Boat.class);
        boat = this.boatRepository.saveAndFlush(boat);

        return this.modelMapper.map(boat, BoatServiceModel.class);
    }
}
