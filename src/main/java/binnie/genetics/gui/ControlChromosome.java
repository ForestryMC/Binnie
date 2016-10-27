package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.core.texture.BinnieCoreTexture;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType> {
	Texture BeeTexture;
	Texture TreeTexture;
	Texture MothTexture;
	Texture FlowerTexture;
	IChromosomeType chromo;
	ISpeciesRoot species;

	public ControlChromosome(final IWidget parent, final float x, final float y) {
		super(parent, x, y, 96.0f, 96.0f);
		this.BeeTexture = new StandardTexture(0, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.TreeTexture = new StandardTexture(96, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.MothTexture = new StandardTexture(96, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.FlowerTexture = new StandardTexture(0, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
		this.chromo = null;
		this.species = null;
	}

	public ISpeciesRoot getRoot() {
		return this.species;
	}

	public void setRoot(final ISpeciesRoot root) {
		if (root != this.species) {
			this.species = root;
			this.deleteAllChildren();
			if (root == Binnie.Genetics.getBeeRoot()) {
				new ControlChromoPicker(this, 28.0f, 47.0f, EnumBeeChromosome.SPECIES);
				new ControlChromoPicker(this, 28.0f, 72.0f, EnumBeeChromosome.SPEED);
				new ControlChromoPicker(this, 19.0f, 20.0f, EnumBeeChromosome.LIFESPAN);
				new ControlChromoPicker(this, 55.0f, 65.0f, EnumBeeChromosome.FERTILITY);
				new ControlChromoPicker(this, 28.0f, 1.0f, EnumBeeChromosome.TEMPERATURE_TOLERANCE);
				new ControlChromoPicker(this, 61.0f, 37.0f, EnumBeeChromosome.NEVER_SLEEPS);
				new ControlChromoPicker(this, 81.0f, 76.0f, EnumBeeChromosome.HUMIDITY_TOLERANCE);
				new ControlChromoPicker(this, 44.0f, 21.0f, EnumBeeChromosome.TOLERATES_RAIN);
				new ControlChromoPicker(this, 3.0f, 37.0f, EnumBeeChromosome.CAVE_DWELLING);
				new ControlChromoPicker(this, 4.0f, 65.0f, EnumBeeChromosome.FLOWER_PROVIDER);
				new ControlChromoPicker(this, 83.0f, 27.0f, EnumBeeChromosome.FLOWERING);
				new ControlChromoPicker(this, 71.0f, 10.0f, EnumBeeChromosome.TERRITORY);
				new ControlChromoPicker(this, 84.0f, 54.0f, EnumBeeChromosome.EFFECT);
			} else if (root == Binnie.Genetics.getTreeRoot()) {
				new ControlChromoPicker(this, 48.0f, 48.0f, EnumTreeChromosome.SPECIES);
				new ControlChromoPicker(this, 43.0f, 12.0f, EnumTreeChromosome.GROWTH);
				new ControlChromoPicker(this, 43.0f, 84.0f, EnumTreeChromosome.HEIGHT);
				new ControlChromoPicker(this, 25.0f, 63.0f, EnumTreeChromosome.FERTILITY);
				new ControlChromoPicker(this, 72.0f, 57.0f, EnumTreeChromosome.FRUITS);
				new ControlChromoPicker(this, 21.0f, 43.0f, EnumTreeChromosome.YIELD);
				new ControlChromoPicker(this, 38.0f, 32.0f, EnumTreeChromosome.PLANT);
				new ControlChromoPicker(this, 15.0f, 17.0f, EnumTreeChromosome.SAPPINESS);
				new ControlChromoPicker(this, 75.0f, 78.0f, EnumTreeChromosome.TERRITORY);
				new ControlChromoPicker(this, 67.0f, 15.0f, EnumTreeChromosome.EFFECT);
				new ControlChromoPicker(this, 70.0f, 34.0f, EnumTreeChromosome.MATURATION);
				new ControlChromoPicker(this, 45.0f, 67.0f, EnumTreeChromosome.GIRTH);
				new ControlChromoPicker(this, 5.0f, 70.0f, EnumTreeChromosome.FIREPROOF);
			} else if (root == Binnie.Genetics.getFlowerRoot()) {
				new ControlChromoPicker(this, 35.0f, 81.0f, EnumFlowerChromosome.SPECIES);
				new ControlChromoPicker(this, 36.0f, 28.0f, EnumFlowerChromosome.PRIMARY);
				new ControlChromoPicker(this, 27.0f, 13.0f, EnumFlowerChromosome.SECONDARY);
				new ControlChromoPicker(this, 47.0f, 15.0f, EnumFlowerChromosome.FERTILITY);
				new ControlChromoPicker(this, 54.0f, 31.0f, EnumFlowerChromosome.TERRITORY);
				new ControlChromoPicker(this, 15.0f, 55.0f, EnumFlowerChromosome.EFFECT);
				new ControlChromoPicker(this, 23.0f, 38.0f, EnumFlowerChromosome.LIFESPAN);
				new ControlChromoPicker(this, 17.0f, 77.0f, EnumFlowerChromosome.TEMPERATURE_TOLERANCE);
				new ControlChromoPicker(this, 52.0f, 51.0f, EnumFlowerChromosome.HUMIDITY_TOLERANCE);
				new ControlChromoPicker(this, 54.0f, 80.0f, EnumFlowerChromosome.PH_TOLERANCE);
				new ControlChromoPicker(this, 41.0f, 42.0f, EnumFlowerChromosome.SAPPINESS);
				new ControlChromoPicker(this, 37.0f, 63.0f, EnumFlowerChromosome.STEM);
			} else if (root == Binnie.Genetics.getButterflyRoot()) {
				new ControlChromoPicker(this, 40.0f, 40.0f, EnumButterflyChromosome.SPECIES);
				new ControlChromoPicker(this, 63.0f, 32.0f, EnumButterflyChromosome.SIZE);
				new ControlChromoPicker(this, 32.0f, 63.0f, EnumButterflyChromosome.SPEED);
				new ControlChromoPicker(this, 11.0f, 27.0f, EnumButterflyChromosome.LIFESPAN);
				new ControlChromoPicker(this, 16.0f, 12.0f, EnumButterflyChromosome.METABOLISM);
				new ControlChromoPicker(this, 17.0f, 63.0f, EnumButterflyChromosome.FERTILITY);
				new ControlChromoPicker(this, 34.0f, 12.0f, EnumButterflyChromosome.TEMPERATURE_TOLERANCE);
				new ControlChromoPicker(this, 22.0f, 46.0f, EnumButterflyChromosome.HUMIDITY_TOLERANCE);
				new ControlChromoPicker(this, 53.0f, 26.0f, EnumButterflyChromosome.NOCTURNAL);
				new ControlChromoPicker(this, 71.0f, 53.0f, EnumButterflyChromosome.TOLERANT_FLYER);
				new ControlChromoPicker(this, 78.0f, 12.0f, EnumButterflyChromosome.FIRE_RESIST);
				new ControlChromoPicker(this, 55.0f, 55.0f, EnumButterflyChromosome.FLOWER_PROVIDER);
				new ControlChromoPicker(this, 27.0f, 31.0f, EnumButterflyChromosome.EFFECT);
				new ControlChromoPicker(this, 87.0f, 45.0f, EnumButterflyChromosome.COCOON);
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
	public void onRenderBackground() {
		if (this.species == null) {
			return;
		}
		super.onRenderBackground();
		final Texture text = this.getTypeTexture();
		CraftGUI.Render.texture(text, IPoint.ZERO);
	}

	private Texture getTypeTexture() {
		if (this.species == Binnie.Genetics.getBeeRoot()) {
			return this.BeeTexture;
		}
		if (this.species == Binnie.Genetics.getTreeRoot()) {
			return this.TreeTexture;
		}
		if (this.species == Binnie.Genetics.getButterflyRoot()) {
			return this.MothTexture;
		}
		if (this.species == Binnie.Genetics.getFlowerRoot()) {
			return this.FlowerTexture;
		}
		return null;
	}
}
