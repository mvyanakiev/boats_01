package boats.service;

import boats.domain.entities.Charter;
import boats.domain.models.serviceModels.CharterServiceModel;
import boats.repository.CharterRepository;
import boats.service.interfaces.CharterService;
import boats.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharterServiceImpl implements CharterService {

    private final CharterRepository charterRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CharterServiceImpl(CharterRepository charterRepository,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil) {
        this.charterRepository = charterRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public CharterServiceModel addCharter(CharterServiceModel charterServiceModel) {

        if (!this.validationUtil.isValid(charterServiceModel)) {
            throw new IllegalArgumentException("Not valid data in add service");
        }

        Charter character = this.modelMapper.map(charterServiceModel, Charter.class);

        try {
            character = this.charterRepository.saveAndFlush(character);
            return this.modelMapper.map(character, CharterServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CharterServiceModel> findAllCharters() {
        return this.charterRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"))
                .stream()
                .map(b -> this.modelMapper.map(b, CharterServiceModel.class))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteCharter(String id) {

        this.charterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charter not found!"));

        try {
        this.charterRepository.deleteById(id);
        } catch(Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Something get wrong during deletion");
        }
    }
}
