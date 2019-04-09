package boats.domain.models.binding;

import boats.domain.models.view.DirectionListViewModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CharterAdd_Step1_BindingModel {

    private String startDate;
    private List<DirectionListViewModel> directions;

    public CharterAdd_Step1_BindingModel() {
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @NotNull
    @NotEmpty
    public List<DirectionListViewModel> getDirections() {
        return this.directions;
    }

    public void setDirections(List<DirectionListViewModel> directions) {
        this.directions = directions;
    }
}
