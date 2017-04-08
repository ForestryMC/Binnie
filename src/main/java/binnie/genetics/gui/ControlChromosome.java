package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;

import com.google.common.base.Preconditions;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType> {
	Texture BeeTexture;
	Texture TreeTexture;
	Texture MothTexture;
	Texture FlowerTexture;
	@Nullable
	IChromosomeType chromo;
	@Nullable
	ISpeciesRoot species;

	public ControlChromosome(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 96, 96);
		this.BeeTexture = new StandardTexture(0, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.TreeTexture = new StandardTexture(96, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.MothTexture = new StandardTexture(96, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.FlowerTexture = new StandardTexture(0, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.chromo = null;
		this.species = null;
	}

	public ISpeciesRoot getRoot() {
		Preconditions.checkState(this.species != null, "root has not been set");
		return this.species;
	}

	public void setRoot(@Nullable final ISpeciesRoot root) {
		if (root != this.species) {
			this.species = root;
			this.deleteAllChildren();
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
		return this.chromo;
	}

	@Override
	public void setValue(final IChromosomeType value) {
		this.chromo = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (this.species == null) {
			return;
		}
		super.onRenderBackground(guiWidth, guiHeight);
		final Texture text = this.getTypeTexture();
		CraftGUI.render.texture(text, Point.ZERO);
	}

	@Nullable
	private Texture getTypeTexture() {
		if (this.species == Binnie.GENETICS.getBeeRoot()) {
			return this.BeeTexture;
		}
		if (this.species == Binnie.GENETICS.getTreeRoot()) {
			return this.TreeTexture;
		}
		if (this.species == Binnie.GENETICS.getButterflyRoot()) {
			return this.MothTexture;
		}
		if (this.species == Binnie.GENETICS.getFlowerRoot()) {
			return this.FlowerTexture;
		}
		return null;
	}
}
