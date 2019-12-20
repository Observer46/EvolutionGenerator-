package agh.cs.project.lab8.Classes;

import static agh.cs.project.lab8.Classes.OptionParser.plantEnergy;

public class Plant extends AbstractWorldMapElement {
    public static int energyBoost=plantEnergy;
    public Plant (Vector2d position){
        super.position=position;
    }

    @Override
    public boolean isPlant() {
        return true;
    }

    public void gotEaten(){
        super.map.remove(this);
    }
}
