package binnie.extratrees.genetics;

import javax.annotation.Nullable;

import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IFruitProvider;
import forestry.core.genetics.alleles.AlleleCategorized;

public class AlleleETFruit extends AlleleCategorized implements IAlleleFruit {
	private final IFruitProvider provider;

	public AlleleETFruit(String name, IFruitProvider provider) {
		super(binnie.core.Constants.EXTRA_TREES_MOD_ID, "fruit", name, true);
		this.provider = provider;
	}

	@Override
	public IFruitProvider getProvider() {
		return this.provider;
	}

	@Override
	public String getName() {
		return getProvider().getDescription();
	}

	@Override
	public String getAlleleName() {
		return getProvider().getDescription();
	}

	@Nullable
	@Override
	public String getModelName() {
		return getProvider().getModelName();
	}


	@Override
	public String getModID() {
		return getProvider().getModID();
	}

	@Override
	public int compareTo(IAlleleFruit o) {
		return 0;
	}
}
