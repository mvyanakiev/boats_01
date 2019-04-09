package boats.service;

import boats.domain.entities.Direction;
import boats.domain.models.serviceModels.DirectionServiceModel;
import boats.repository.DirectionRepository;
import boats.service.interfaces.DirectionsService;
import boats.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectionsServiceImpl implements DirectionsService {

    private final DirectionRepository directionRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public DirectionsServiceImpl(DirectionRepository directionRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.directionRepository = directionRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public List<DirectionServiceModel> findAllDirections() {
        return this.directionRepository.findAll(Sort.by(Sort.Direction.ASC, "destination"))
                .stream()
                .map(b -> this.modelMapper.map(b, DirectionServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DirectionServiceModel findDirectionById(String id) {
        Direction direction = this.directionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Direction not found!"));

        return this.modelMapper.map(direction, DirectionServiceModel.class);
    }
}
