package binnie.botany.genetics.allele;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IModelManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.genetics.alleles.AlleleSpecies;

import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IAlleleFlowerSpeciesBuilder;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.core.BotanyCore;

public class AlleleFlowerSpecies extends AlleleSpecies implements IAlleleFlowerSpecies, IAlleleFlowerSpeciesBuilder {
	private final IFlowerType flowerType;
	private EnumAcidity acidity = EnumAcidity.NEUTRAL;
	private EnumMoisture moisture = EnumMoisture.NORMAL;

	public AlleleFlowerSpecies(
			String uid,
			String unlocalizedName,
			String authority,
			String unlocalizedDescription,
			boolean isDominant,
			IClassification branch,
			String binomial,
			IFlowerType flowerType) {
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
	public int getFlowerColor(EnumFlowerStage type, int renderPass) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getFlowerModel(EnumFlowerStage type, boolean flowered) {
		return flowerType.getModel(type, flowered);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(Item item, IModelManager manager, EnumFlowerStage type) {
		flowerType.registerModels(item, manager, type);
	}

	@Override
	public int compareTo(IAlleleFlowerSpecies o) {
		return 0;
	}
}
