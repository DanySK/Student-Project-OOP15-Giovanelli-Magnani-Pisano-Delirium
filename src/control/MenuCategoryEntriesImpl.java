package control;

import java.util.List;
import java.util.Optional;

public class MenuCategoryEntriesImpl implements MenuCategoryEntries {
    private final List<Buttons> entries;
    private Optional<Buttons> focus;
    public MenuCategoryEntriesImpl(List<Buttons> entries, Optional<Buttons> focus) {
        super();
        this.entries = entries;
        this.focus = focus;
    }
    
    @Override
    public Optional<Buttons> getFocus() {
        return focus;
    }
    
    public void setFocus(Optional<Buttons> focus) {
        this.focus = focus;
    }
    
    @Override
    public List<Buttons> getEntries() {
        return entries;
    }
}
