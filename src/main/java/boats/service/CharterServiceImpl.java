package boats.service;

import boats.domain.entities.Charter;
import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.view.ContractViewModel;
import boats.error.NotFoundExceptions;
import boats.repository.CharterRepository;
import boats.service.interfaces.CharterService;
import boats.utils.ContractConverter;
import boats.utils.ContractConverterImpl;
import boats.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static boats.config.ErrorMessages.*;

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
                .orElseThrow(() -> new NotFoundExceptions(ITEM_CHARTER + NOT_FOUND));

        try {
            this.charterRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Something get wrong during deletion");
        }
    }


    @Override
    public CharterServiceModel findByCharterId(String id) {

        Charter charter = this.charterRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions(ITEM_CHARTER + NOT_FOUND));

        return this.modelMapper.map(charter, CharterServiceModel.class);
    }

    @Override
    public List<CharterServiceModel> findActiveCharters() {

        List<CharterServiceModel> allCharters = this.findAllCharters();
        List<CharterServiceModel> activeCharter = new ArrayList<>();

        for (CharterServiceModel charter : allCharters) {

            if ((charter.getStartDate().isBefore(LocalDate.now())
                    ||
                    charter.getStartDate().isEqual(LocalDate.now()))
                    &&
                    charter.getStartDate().plusDays(charter.getDirection().getPeriod()).isAfter(LocalDate.now())) {
                activeCharter.add(charter);
            }
        }

        return activeCharter;
    }

    @Override
    public ContractViewModel createContract(String id) {

        ContractConverter contractConverter = new ContractConverterImpl();
        return contractConverter.convertCharterToContract(this.findByCharterId(id));
    }
}
