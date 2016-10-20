package binnie.extratrees.api;

import java.util.List;

public interface IDesignCategory {
    String getName();

    List<IDesign> getDesigns();

    void addDesign(final IDesign p0);

    String getId();
}
