package boats.service;

import boats.domain.entities.Boat;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.repository.BoatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        try {
            boat = this.boatRepository.saveAndFlush(boat);
            return this.modelMapper.map(boat, BoatServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<BoatServiceModel> findAllBoats() {
        return this.boatRepository.findAll()
                .stream()
                .map(b -> this.modelMapper.map(b, BoatServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BoatServiceModel findBoatById(String id) {
        Boat boat = this.boatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Boat not found!"));

        return this.modelMapper.map(boat, BoatServiceModel.class);
    }

    @Override
    public BoatServiceModel saveEditedBoat(BoatServiceModel boatServiceModel) {

//        Boat boat = this.boatRepository.findById(boatServiceModel.getId()).orElseThrow(
//                () -> new IllegalArgumentException("Boat not found!")
//        );


        //todo add validation with validation util


        Boat boat = this.modelMapper.map(boatServiceModel, Boat.class);

//        boat.setName(boatServiceModel.getName());
//        boat.setModel(boatServiceModel.getModel());
//        boat.setProducer(boatServiceModel.getProducer());
//        boat.setPrice(boatServiceModel.getPrice());
        //todo all other fields

        try {
            this.boatRepository.saveAndFlush(boat);
            return this.modelMapper.map(boat, BoatServiceModel.class);
        } catch (Exception e) {
            return null;
        }

    }
}