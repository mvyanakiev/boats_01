package boats.service;

import boats.domain.entities.Boat;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.domain.models.serviceModels.CharterServiceModel;
import boats.error.NotFoundExceptions;
import boats.repository.BoatRepository;
import boats.service.interfaces.BoatService;
import boats.service.interfaces.CharterService;
import boats.service.interfaces.DirectionsService;
import org.modelmapper.ModelMapper;
import boats.utils.ValidationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final DirectionsService directionsService;
    private final CharterService charterService;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository,
                           ModelMapper modelMapper,
                           ValidationUtil validationUtil,
                           DirectionsService directionsService,
                           CharterService charterService) {
        this.boatRepository = boatRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.directionsService = directionsService;
        this.charterService = charterService;
    }

    @Override
    public BoatServiceModel addBoat(BoatServiceModel boatServiceModel) {

        if (!this.validationUtil.isValid(boatServiceModel)) {
            throw new IllegalArgumentException("Trying to add boat with invalid data!");
        }

        return saveBoatToDb(boatServiceModel);
    }

    @Override
    public List<BoatServiceModel> findAllBoats() {
        return this.boatRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(b -> this.modelMapper.map(b, BoatServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BoatServiceModel findBoatById(String id) {
        Boat boat = this.boatRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Boat not found!"));

        return this.modelMapper.map(boat, BoatServiceModel.class);
    }

    @Override
    public BoatServiceModel saveEditedBoat(BoatServiceModel boatServiceModel) {

        if (!this.validationUtil.isValid(boatServiceModel)) {
            throw new IllegalArgumentException("Trying to edit boat with invalid data!");
        }

        return saveBoatToDb(boatServiceModel);
    }

    @Override
    public List<BoatServiceModel> findAvailableBoats(String startDate, String directionId) {
        int period = this.directionsService.findDirectionById(directionId).getPeriod();
        LocalDate requiredPeriodStart = LocalDate.parse(startDate);
        LocalDate requiredPeriodEnd = requiredPeriodStart.plusDays(period);

        List<BoatServiceModel> availableBoats = this.findAllBoats();
        List<BoatServiceModel> NotAvailableBoats = new ArrayList<>();

        List<CharterServiceModel> charters = this.charterService.findAllCharters();

        // same for repairs

        for (CharterServiceModel charter : charters) {

            LocalDate chartPeriodStart = charter.getStartDate();
            LocalDate chartPeriodEnd = chartPeriodStart.plusDays(charter.getDirection().getPeriod());

            if (!(requiredPeriodStart.isAfter(chartPeriodEnd) || requiredPeriodEnd.isBefore(chartPeriodStart))) {

                for (BoatServiceModel boat : availableBoats) {
                    if (boat.getId().equals(charter.getBoat().getId())) {
                        NotAvailableBoats.add(boat);
                    }
                }
            }

        }
        availableBoats.removeAll(NotAvailableBoats);
        return availableBoats;
    }

    private BoatServiceModel saveBoatToDb(BoatServiceModel boatServiceModel) {
        Boat boat = this.modelMapper.map(boatServiceModel, Boat.class);

        try {
            boat = this.boatRepository.saveAndFlush(boat);
            return this.modelMapper.map(boat, BoatServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }
}