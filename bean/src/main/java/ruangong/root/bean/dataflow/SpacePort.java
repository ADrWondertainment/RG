package ruangong.root.bean.dataflow;

import lombok.Data;

import java.util.List;
import java.util.Queue;

@Data
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

    protected static void registerStation(SpaceStation spaceStation) {
        stations.add(spaceStation);
        int index = stations.indexOf(spaceStation);
        registeredSpaceStations.add(index);
        spaceStation.setRegisterId(index);
    }

    protected static void registerAstronaut(Astronaut astronaut) {
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

}
