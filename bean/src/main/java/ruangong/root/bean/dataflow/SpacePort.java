package ruangong.root.bean.dataflow;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component
public abstract class SpacePort<MEMBER extends Astronaut<LOW>, LOW> {
    protected List<Integer> registeredSpaceStations;
    protected List<Integer> registeredAstronauts;

    protected List<SpaceStation<MEMBER, LOW>> stations;
    protected List<Astronaut<LOW>> astronauts;

    protected Queue<AIMDiffusionField<MEMBER, LOW>> finished;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> powerless;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> disoriented;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> damaged;
    protected Queue<AIMDiffusionField<MEMBER, LOW>> deprecated;


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
        registeredSpaceStations.add(id);
        spaceStation.setRegisterId(id);
        spaceStation.setCentralPort(this);
    }

    protected void registerAstronaut(Astronaut<LOW> astronaut, int id) {
        astronauts.add(astronaut);
        registeredAstronauts.add(id);
        astronaut.setRegisterId(id);
        astronaut.setCentralPort(this);
    }

    public boolean checkSpaceStation(Integer id) {
        return registeredSpaceStations.contains(id);
    }

    public boolean checkAstronauts(Integer id) {
        return registeredAstronauts.contains(id);
    }

    public void show() {
        System.out.println(registeredSpaceStations);
        System.out.println(registeredAstronauts);
        System.out.println(powerless);
    }

}
