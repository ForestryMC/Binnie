package binnie.extratrees.api;

import java.util.List;

public interface IDesignCategory {
    String getName();

    List<IDesign> getDesigns();

    void addDesign(IDesign design);

    String getId();
}
