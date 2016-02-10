package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import control.Dimension;
import control.Pair;
import control.Point;

public class ArenaImpl implements Arena {
    
    
    private final Dimension dimensions;
    private final Hero hero;
    //private List<Other> others;
    private List<Entities> entities;
    
    
    public ArenaImpl(final Heroes hero, final Dimension dimensions ) {
        this.dimensions = dimensions;
        this.hero = new HeroImpl(hero.getCode(), hero.getLife(), hero.getPosition(), hero.gestSpeed());
        //this.others = new LinkedList<>();
        this.entities = new LinkedList<>();
    }
    
    
    @Override
    public Map<Integer, Pair<Integer, ModelPosition>> getHero() {
        Map<Integer, Pair<Integer, ModelPosition>> result = new HashMap<>();
        result.put(this.hero.getCode(), new Pair<Integer, ModelPosition>(this.hero.getLife(), this.hero.getPosition()));
        return result;
    }


    @Override
    public void moveHero(final ModelDirections action) {
        //Point actualPoint = this.hero.getPosition().getPoint();
        //this.hero.setPosition(action.getFunction().apply(actualPoint, this.hero.getSpeed()), action);
        this.entities.stream().filter(t -> t.getCode() == 0).forEach(t -> {
            t.setPosition(action.getFunction().apply(t.getPoint(), Hero.INITIAL_SPEED), action);
        });
    }


    @Override
    public void putOthers(Map<Integer, StaticOthers> staticOthers, Map<Integer, DinamicOthers> dinamicOthers) {
        dinamicOthers.entrySet().stream().forEach(t -> {
            MovementManager movementManager;
            if(t.getValue().getDirection() == ModelDirections.NONE) {
                movementManager = new RandomDinamicMovementManager(t.getValue().getPosition(), t.getValue().getSpeed(), t.getValue().getBounds());
            } else {
                movementManager = new LinearDinamicMovementManager(t.getValue().getPosition(), t.getValue().getSpeed(), t.getValue().getBounds());
            }
            //this.others.add(new OtherImpl(t.getKey(), t.getValue().getLife(), t.getValue().getLifemanager(), movementManager, t.getValue().getContactDamage()));
            this.entities.add(new EntitiesImpl(t.getKey(), t.getValue().getLife(), t.getValue().getLifemanager(), movementManager, t.getValue().getContactDamage().get()));
        });
        
        staticOthers.entrySet().stream().forEach(t -> {
            this.entities.add(new EntitiesImpl(t.getKey(), t.getValue().getLife(), t.getValue().getLifemanager(), new StaticMovementManager(t.getValue().getPosition()), t.getValue().getContactDamage().get()));
            //this.others.add(new OtherImpl(t.getKey(), t.getValue().getLife(), t.getValue().getLifemanager(), new StaticMovementManager(t.getValue().getPosition()), t.getValue().getContactDamage()));
            
        });
        
    }


    @Override
    public Map<Integer, Pair<Integer, ModelPosition>> getOthers() {
        final Map<Integer, Pair<Integer, ModelPosition>> result = new HashMap<>();
        this.entities.stream().forEach(e -> result.put(e.getCode(), new Pair<Integer, ModelPosition>(e.getLife(), e.getPosition())));
        //this.others.stream().forEach(e -> result.put(e.getCode(), new Pair<Integer, ModelPosition>(e.getLife(), e.getMovementManager().getPosition())));
        
        return result;
        
    }


    @Override
    public void moveOthers() {
       /* this.others.stream().forEach(t -> {
            ModelPosition newPosition = t.getMovementManager().getNextMove();
            t.getMovementManager().setPosition(newPosition.getPoint(), newPosition.getDirection());
        });*/
        
        this.entities.stream().forEach(t -> {
            t.setPosition(t.getNextMove().getPoint(), t.getNextMove().getDirection());
        });
    }
    
}
