package view;

public enum Actions {
    
    JUMP("jump"),
    FALL("fall"),
    MOVE("move"),
    IDLE("idle");
    //SHOOT("shoot"),
    //DEATH("death");
    
    private final String string;

    private Actions(final String string) {
        this.string = string;
    }
    
    public String getString() {
        return this.string;
    }

}