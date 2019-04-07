package boats.domain.models.view;

public class DirectionListViewModel {

    private String id;
    private String destination;

    public DirectionListViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
