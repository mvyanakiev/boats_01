package boats.service;

import boats.domain.entities.Boat;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.repository.BoatRepository;
import org.modelmapper.ModelMapper;
import boats.utils.ValidationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.boatRepository = boatRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public BoatServiceModel addBoat(BoatServiceModel boatServiceModel) {

        if (!this.validationUtil.isValid(boatServiceModel)) {
            throw new IllegalArgumentException("Not valid data in add service");
        }

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

        if (!this.validationUtil.isValid(boatServiceModel)) {
            throw new IllegalArgumentException("Not valid edit in add service");

        }


        Boat boat = this.modelMapper.map(boatServiceModel, Boat.class);

        try {
            boat = this.boatRepository.saveAndFlush(boat);
            return this.modelMapper.map(boat, BoatServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }




}