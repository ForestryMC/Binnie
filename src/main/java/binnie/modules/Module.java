package binnie.modules;

import java.util.Collections;
import java.util.Set;

public abstract class Module{

	public void registerItemsAndBlocks(){
	}

	public String getFailMessage(){
		return "";
	}

	public Set<String> getDependencyUids(){
		return Collections.emptySet();
	}

	public boolean canBeDisabled(){
		return true;
	}

	public boolean isAvailable(){
		return true;
	}

	public void setupAPI(){
	}

	public void disabledSetupAPI(){
	}

	public void preInit() {
	}

	public void init() {
	}

	public void postInit() {
	}
}
