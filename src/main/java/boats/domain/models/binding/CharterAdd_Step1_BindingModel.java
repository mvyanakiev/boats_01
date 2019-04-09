package boats.domain.models.binding;

import boats.domain.models.view.DirectionListViewModel;

import java.util.List;

public class CharterAdd_Step1_BindingModel {

    private String startDate;
    private List<DirectionListViewModel> directions;

    public CharterAdd_Step1_BindingModel() {
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<DirectionListViewModel> getDirections() {
        return this.directions;
    }

    public void setDirections(List<DirectionListViewModel> directions) {
        this.directions = directions;
    }
}
