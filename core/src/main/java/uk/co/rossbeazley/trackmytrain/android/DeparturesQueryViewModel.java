package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;

public class DeparturesQueryViewModel {
    private final Station at;
    private final Direction direction;

    private final List<Station> stations;

    public DeparturesQueryViewModel(Station at, Direction direction) {
        this.at = at;
        this.direction = direction;
        stations = new Stations().list();
    }

    public Station getAt() {
        return at;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Station> stations() {
        return stations;
    }
}
