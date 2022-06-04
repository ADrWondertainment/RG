package ruangong.root.bean.dataflow;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component
public abstract class SpacePort {
    protected static List<Integer> registeredSpaceStations;
    protected static List<Integer> registeredAstronauts;

    protected static List<SpaceStation> stations;
    protected static List<Astronaut> astronauts;

    protected static Queue<AIMDiffusionField> finished;
    protected static Queue<AIMDiffusionField> powerless;
    protected static Queue<AIMDiffusionField> disoriented;
    protected static Queue<AIMDiffusionField> damaged;
    protected static Queue<AIMDiffusionField> deprecated;

    static  {
        registeredSpaceStations = new ArrayList<>();
        registeredAstronauts = new ArrayList<>();

        stations = new ArrayList<>();
        astronauts = new ArrayList<>();

        finished = new LinkedList<>();
        powerless = new LinkedList<>();
        disoriented = new LinkedList<>();
        damaged = new LinkedList<>();
        deprecated = new LinkedList<>();
    }

    protected void load(List<? extends AIMDiffusionField> fields) {
        powerless.addAll(fields);
    }

    protected void assign() {
        for (AIMDiffusionField field : powerless) {
            field.shoot();
        }
    }

    protected void registerStation(SpaceStation spaceStation) {
        stations.add(spaceStation);
        int index = stations.indexOf(spaceStation);
        registeredSpaceStations.add(index);
        spaceStation.setRegisterId(index);
    }

    protected void registerAstronaut(Astronaut astronaut) {
        astronauts.add(astronaut);
        int index = astronauts.indexOf(astronaut);
        registeredAstronauts.add(index);
        astronaut.setRegisterId(index);
    }

    protected static boolean checkSpaceStation(Integer id) {
        return registeredSpaceStations.contains(id);
    }

    protected static boolean checkAstronauts(Integer id) {
        return registeredAstronauts.contains(id);
    }

    public void show() {
        System.out.println(registeredSpaceStations);
        System.out.println(registeredAstronauts);
        System.out.println(powerless);
    }

}
