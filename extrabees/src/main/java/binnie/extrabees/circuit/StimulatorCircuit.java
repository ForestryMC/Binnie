package binnie.extrabees.circuit;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitLayout;

import binnie.core.Mods;

public class StimulatorCircuit implements ICircuit, IBeeModifier {

	private final AlvearySimulatorCircuitType type;
	private final String uid;
	private final List<String> tooltip;

	public StimulatorCircuit(final AlvearySimulatorCircuitType type, final ICircuitLayout layout) {
		uid = "binnie.circuit.stimulator." + type.toString().toLowerCase();
		this.type = type;
		ItemStack stack = new ItemStack(Mods.Forestry.item("thermionic_tubes"), 1, type.getRecipe());
		ChipsetManager.circuitRegistry.registerCircuit(this);
		ChipsetManager.solderManager.addRecipe(layout, stack, this);
		tooltip = Lists.newArrayList();
	}

	public int getPowerUsage() {
		return this.type.getPower();
	}

	public void addTooltip(String tt) {
		this.tooltip.add(tt);
	}

	@Override
	public float getTerritoryModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.type.getTerritoryModifier(genome, currentModifier);
	}

	@Override
	public float getMutationModifier(@Nonnull final IBeeGenome genome, @Nonnull final IBeeGenome mate, final float currentModifier) {
		return this.type.getMutationModifier(genome, mate, currentModifier);
	}

	@Override
	public float getLifespanModifier(@Nonnull final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return this.type.getLifespanModifier(genome, mate, currentModifier);
	}

	@Override
	public float getProductionModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.type.getProductionModifier(genome, currentModifier);
	}

	@Override
	public float getFloweringModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.type.getFloweringModifier(genome, currentModifier);
	}

	@Override
	public boolean isSealed() {
		return this.type.isSealed();
	}

	@Override
	public boolean isSelfLighted() {
		return this.type.isSelfLighted();
	}

	@Override
	public boolean isSunlightSimulated() {
		return this.type.isSunlightSimulated();
	}

	@Override
	public boolean isHellish() {
		return this.type.isHellish();
	}

	@Override
	public float getGeneticDecay(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.type.getGeneticDecay(genome, currentModifier);
	}

	@Override
	@Nonnull
	public String getUID() {
		return uid;
	}

	@Override
	@Nonnull
	public String getUnlocalizedName() {
		return uid;
	}

	@Override
	public String getLocalizedName() {
		return tooltip.toString();
	}

	@Override
	public boolean isCircuitable(@Nonnull Object tile) {
		System.out.println("isCircuitable: " + tile);
		return false;
	}

	@Override
	public void onInsertion(int slot, @Nonnull Object tile) {

	}

	@Override
	public void onLoad(int slot, @Nonnull Object tile) {

	}

	@Override
	public void onRemoval(int slot, @Nonnull Object tile) {

	}

	@Override
	public void onTick(int slot, @Nonnull Object tile) {

	}

	@Override
	public void addTooltip(@Nonnull List<String> list) {
		list.addAll(tooltip);
	}
}
