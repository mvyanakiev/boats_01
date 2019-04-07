package boats.domain.models.view;

public class BoatListViewModel {

    private String id;
    private String boatName;

    public BoatListViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoatName() {
        return this.boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }
}
