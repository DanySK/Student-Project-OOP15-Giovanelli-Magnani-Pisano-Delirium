package model;

import java.util.Optional;

public class Hero extends EntitiesImpl {

    //TODO cambiare costruttore levando optional
    public Hero(int code, LifeManager lifeManager, MovementManager movementManager,
            Optional<ShootManager> shootManager, Optional<Integer> contactDamage) {
        super(code, lifeManager, movementManager, shootManager, contactDamage);
    }

    @Override
    public void setAction(Actions action) {
        if (action != Actions.SHOOT) {
            super.getMovementManager().get().setAction(action);
        } else {
            HeroShootManager heroShootManager = (HeroShootManager) getShootManager().get();
            heroShootManager.wannaShoot();
        }
    }
    
    public void setOnPlatform(boolean bool) {
        HeroMovementManager move = (HeroMovementManager) this.getMovementManager().get();
        move.setOnPlatform(bool);
    }

}
