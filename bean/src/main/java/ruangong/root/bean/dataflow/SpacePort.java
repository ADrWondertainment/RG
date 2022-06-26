package ruangong.root.bean.dataflow;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@SuppressWarnings("AlibabaAbstractClassShouldStartWithAbstractNaming")
@Data
@Component
public abstract class SpacePort<MEMBER extends Astronaut<LOW>, LOW> {
    protected Map<Integer, Integer> registeredSpaceStations;
    protected Map<Integer, Integer> registeredAstronauts;

    protected List<SpaceStation<MEMBER, LOW>> stations;
    protected List<Astronaut<LOW>> astronauts;

    protected Queue<AIMDiffusionField<MEMBER, LOW>> finished;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> powerless;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> disoriented;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> damaged;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> deprecated;

    public void init() {
        registeredAstronauts = new HashMap<>();
        registeredSpaceStations = new HashMap<>();
        stations = new ArrayList<>();
        astronauts = new ArrayList<>();
        finished = new LinkedList<>();
        powerless = new LinkedList<>();
        disoriented = new LinkedList<>();
        damaged = new LinkedList<>();
        deprecated = new LinkedList<>();
    }

    protected void load(List<? extends AIMDiffusionField<MEMBER, LOW>> fields) {
        powerless.addAll(fields);
    }

    protected void assign() {
        for (AIMDiffusionField field : powerless) {
            field.shoot();
        }
    }

    protected void registerStation(SpaceStation<MEMBER, LOW> spaceStation, int id) {
        stations.add(spaceStation);
        registeredSpaceStations.put(id, stations.indexOf(spaceStation));
        spaceStation.setRegisterId(id);
        spaceStation.setCentralPort(this);
    }

    protected void registerAstronaut(Astronaut<LOW> astronaut, int id) {
        astronauts.add(astronaut);
        registeredAstronauts.put(id, astronauts.indexOf(astronaut));
        astronaut.setRegisterId(id);
        astronaut.setCentralPort(this);
    }

    public boolean checkSpaceStation(Integer id) {
        return registeredSpaceStations.containsKey(id);
    }

    public boolean checkAstronauts(Integer id) {
        return registeredAstronauts.containsKey(id);
    }

    public void show() {
        System.out.println(registeredSpaceStations);
        System.out.println(registeredAstronauts);
        System.out.println(powerless);
    }

}
