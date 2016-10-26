package binnie.botany.genetics;

import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import forestry.api.core.IModelManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.genetics.alleles.AlleleSpecies;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class AlleleFlowerSpecies extends AlleleSpecies implements IAlleleFlowerSpecies, IAlleleFlowerSpeciesBuilder {

	@Nonnull
	private final IFlowerType flowerType;
	@Nonnull
	private EnumAcidity acidity = EnumAcidity.Neutral;
	@Nonnull
	private EnumMoisture moisture = EnumMoisture.Normal;
	
	public AlleleFlowerSpecies(			
			@Nonnull String uid,
			@Nonnull String unlocalizedName,
			@Nonnull String authority,
			@Nonnull String unlocalizedDescription,
			boolean isDominant,
			@Nonnull IClassification branch,
			@Nonnull String binomial,
			@Nonnull IFlowerType flowerType) {
		super(uid, unlocalizedName, authority, unlocalizedDescription, isDominant, branch, binomial);
		this.flowerType = flowerType;
	}
	
	@Override
	public IAlleleFlowerSpeciesBuilder setPH(EnumAcidity acidity) {
		this.acidity = acidity;
		return this;
	}

	@Override
	public IAlleleFlowerSpeciesBuilder setMoisture(EnumMoisture moisture) {
		this.moisture = moisture;
		return this;
	}
	
	@Override
	public IAlleleFlowerSpecies build() {
		AlleleManager.alleleRegistry.registerAllele(this, EnumFlowerChromosome.SPECIES);
		return this;
	}

	@Nonnull
	@Override
	public ISpeciesRoot getRoot() {
		return BotanyCore.getFlowerRoot();
	}

	@Override
	public IFlowerType getType() {
		return flowerType;
	}

	@Override
	public EnumAcidity getPH() {
		return acidity;
	}

	@Override
	public EnumMoisture getMoisture() {
		return moisture;
	}
	
	@Override
	public int getSpriteColour(int renderPass) {
		return 0;
	}

	@Override
	public int getFlowerColour(EnumFlowerStage type, int renderPass) {
		return 0;
	}

	@Override
	public ModelResourceLocation getFlowerModel(EnumFlowerStage type, boolean flowered) {
		return flowerType.getModel(type, flowered);
	}

	@Override
	public void registerModels(Item item, IModelManager manager, EnumFlowerStage type) {
		flowerType.registerModels(item, manager, type);
	}

	@Override
	public int compareTo(IAlleleFlowerSpecies o) {
		return 0;
	}

}
