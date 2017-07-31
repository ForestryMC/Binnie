package binnie.core.gui.fieldkit;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.Texture;
import binnie.core.gui.resource.minecraft.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType> {
	Texture BeeTexture;
	Texture TreeTexture;
	Texture MothTexture;
	Texture FlowerTexture;
	@Nullable
	IChromosomeType chromo;
	@Nullable
	ISpeciesRoot species;

	public ControlChromosome(IWidget parent, int x, int y) {
		super(parent, x, y, 96, 96);
		BeeTexture = new StandardTexture(0, 0, 96, 96, BinnieCoreTexture.GUI_BREEDING);
		TreeTexture = new StandardTexture(96, 0, 96, 96, BinnieCoreTexture.GUI_BREEDING);
		MothTexture = new StandardTexture(96, 96, 96, 96, BinnieCoreTexture.GUI_BREEDING);
		FlowerTexture = new StandardTexture(0, 96, 96, 96, BinnieCoreTexture.GUI_BREEDING);
		chromo = null;
		species = null;
	}

	public ISpeciesRoot getRoot() {
		Preconditions.checkState(species != null, "root has not been set");
		return species;
	}

	public void setRoot(@Nullable ISpeciesRoot root) {
		if (root != species) {
			species = root;
			deleteAllChildren();
			if (root == Binnie.GENETICS.getBeeRoot()) {
				new ControlChromoPicker(this, 28, 47, EnumBeeChromosome.SPECIES);
				new ControlChromoPicker(this, 28, 72, EnumBeeChromosome.SPEED);
				new ControlChromoPicker(this, 19, 20, EnumBeeChromosome.LIFESPAN);
				new ControlChromoPicker(this, 55, 65, EnumBeeChromosome.FERTILITY);
				new ControlChromoPicker(this, 28, 1, EnumBeeChromosome.TEMPERATURE_TOLERANCE);
				new ControlChromoPicker(this, 61, 37, EnumBeeChromosome.NEVER_SLEEPS);
				new ControlChromoPicker(this, 81, 76, EnumBeeChromosome.HUMIDITY_TOLERANCE);
				new ControlChromoPicker(this, 44, 21, EnumBeeChromosome.TOLERATES_RAIN);
				new ControlChromoPicker(this, 3, 37, EnumBeeChromosome.CAVE_DWELLING);
				new ControlChromoPicker(this, 4, 65, EnumBeeChromosome.FLOWER_PROVIDER);
				new ControlChromoPicker(this, 83, 27, EnumBeeChromosome.FLOWERING);
				new ControlChromoPicker(this, 71, 10, EnumBeeChromosome.TERRITORY);
				new ControlChromoPicker(this, 84, 54, EnumBeeChromosome.EFFECT);
			} else if (root == Binnie.GENETICS.getTreeRoot()) {
				new ControlChromoPicker(this, 48, 48, EnumTreeChromosome.SPECIES);
				new ControlChromoPicker(this, 43, 84, EnumTreeChromosome.HEIGHT);
				new ControlChromoPicker(this, 25, 63, EnumTreeChromosome.FERTILITY);
				new ControlChromoPicker(this, 72, 57, EnumTreeChromosome.FRUITS);
				new ControlChromoPicker(this, 21, 43, EnumTreeChromosome.YIELD);
				new ControlChromoPicker(this, 15, 17, EnumTreeChromosome.SAPPINESS);
				new ControlChromoPicker(this, 67, 15, EnumTreeChromosome.EFFECT);
				new ControlChromoPicker(this, 70, 34, EnumTreeChromosome.MATURATION);
				new ControlChromoPicker(this, 45, 67, EnumTreeChromosome.GIRTH);
				new ControlChromoPicker(this, 5, 70, EnumTreeChromosome.FIREPROOF);
			} else if (root == Binnie.GENETICS.getFlowerRoot()) {
				new ControlChromoPicker(this, 35, 81, EnumFlowerChromosome.SPECIES);
				new ControlChromoPicker(this, 36, 28, EnumFlowerChromosome.PRIMARY);
				new ControlChromoPicker(this, 27, 13, EnumFlowerChromosome.SECONDARY);
				new ControlChromoPicker(this, 47, 15, EnumFlowerChromosome.FERTILITY);
				new ControlChromoPicker(this, 54, 31, EnumFlowerChromosome.TERRITORY);
				new ControlChromoPicker(this, 15, 55, EnumFlowerChromosome.EFFECT);
				new ControlChromoPicker(this, 23, 38, EnumFlowerChromosome.LIFESPAN);
				new ControlChromoPicker(this, 17, 77, EnumFlowerChromosome.TEMPERATURE_TOLERANCE);
				new ControlChromoPicker(this, 52, 51, EnumFlowerChromosome.HUMIDITY_TOLERANCE);
				new ControlChromoPicker(this, 54, 80, EnumFlowerChromosome.PH_TOLERANCE);
				new ControlChromoPicker(this, 41, 42, EnumFlowerChromosome.SAPPINESS);
				new ControlChromoPicker(this, 37, 63, EnumFlowerChromosome.STEM);
			} else if (root == Binnie.GENETICS.getButterflyRoot()) {
				new ControlChromoPicker(this, 40, 40, EnumButterflyChromosome.SPECIES);
				new ControlChromoPicker(this, 63, 32, EnumButterflyChromosome.SIZE);
				new ControlChromoPicker(this, 32, 63, EnumButterflyChromosome.SPEED);
				new ControlChromoPicker(this, 11, 27, EnumButterflyChromosome.LIFESPAN);
				new ControlChromoPicker(this, 16, 12, EnumButterflyChromosome.METABOLISM);
				new ControlChromoPicker(this, 17, 63, EnumButterflyChromosome.FERTILITY);
				new ControlChromoPicker(this, 34, 12, EnumButterflyChromosome.TEMPERATURE_TOLERANCE);
				new ControlChromoPicker(this, 22, 46, EnumButterflyChromosome.HUMIDITY_TOLERANCE);
				new ControlChromoPicker(this, 53, 26, EnumButterflyChromosome.NOCTURNAL);
				new ControlChromoPicker(this, 71, 53, EnumButterflyChromosome.TOLERANT_FLYER);
				new ControlChromoPicker(this, 78, 12, EnumButterflyChromosome.FIRE_RESIST);
				new ControlChromoPicker(this, 55, 55, EnumButterflyChromosome.FLOWER_PROVIDER);
				new ControlChromoPicker(this, 27, 31, EnumButterflyChromosome.EFFECT);
				new ControlChromoPicker(this, 87, 45, EnumButterflyChromosome.COCOON);
			}
		}
	}

	@Override
	public IChromosomeType getValue() {
		return chromo;
	}

	@Override
	public void setValue(IChromosomeType value) {
		chromo = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (species == null) {
			return;
		}
		super.onRenderBackground(guiWidth, guiHeight);
		Texture text = getTypeTexture();
		CraftGUI.RENDER.texture(text, Point.ZERO);
	}

	@Nullable
	private Texture getTypeTexture() {
		if (species == Binnie.GENETICS.getBeeRoot()) {
			return BeeTexture;
		}
		if (species == Binnie.GENETICS.getTreeRoot()) {
			return TreeTexture;
		}
		if (species == Binnie.GENETICS.getButterflyRoot()) {
			return MothTexture;
		}
		if (species == Binnie.GENETICS.getFlowerRoot()) {
			return FlowerTexture;
		}
		return null;
	}
}
