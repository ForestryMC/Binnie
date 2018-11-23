package binnie.design.api;

import java.util.List;

public interface IDesignCategory {
	String getName();

	List<IDesign> getDesigns();

	void addDesign(IDesign p0);

	String getId();
}
