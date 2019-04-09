package boats.domain.models.binding;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CharterAdd_Step2_BindingModel {

    private String startDate;
    private String id;

    public CharterAdd_Step2_BindingModel() {
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
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
