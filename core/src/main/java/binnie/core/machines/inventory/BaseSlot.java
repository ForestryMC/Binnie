package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import binnie.core.util.IValidator;

public abstract class BaseSlot<V> implements INbtWritable, INbtReadable, IValidator<V> {
	@Nullable
	protected final ResourceLocation unlocLocation;
	@Nullable
	private Validator<V> validator;
	private final SidedAccess access;
	private boolean readOnly;
	private int index;

	public BaseSlot(int index, ResourceLocation unlocLocation) {
		this.access = new SidedAccess();
		this.setIndex(index);
		this.unlocLocation = unlocLocation;
	}

	public void setReadOnly() {
		this.readOnly = true;
		this.forbidInsertion();
	}

	@Override
	public boolean isValid(@Nullable V item) {
		return item == null || this.validator == null || this.validator.isValid(item);
	}

	@Nullable
	public abstract V getContent();

	public boolean isReadOnly() {
		return this.readOnly;
	}

	public int getIndex() {
		return this.index;
	}

	private void setIndex(int index) {
		this.index = index;
	}

	public boolean canInsert() {
		return !this.access.getInsertionSides().isEmpty();
	}

	public boolean canExtract() {
		return !this.access.getExtractionSides().isEmpty();
	}

	public void forbidInteraction() {
		this.readOnly = true;
		this.forbidInsertion();
		this.forbidExtraction();
	}

	public void forbidExtraction() {
		this.access.setExtract(false);
		this.access.forbidExtractChange();
	}

	public void forbidInsertion() {
		this.access.setInsert(false);
		this.access.forbidInsertChange();
	}

	public boolean canInsert(@Nullable EnumFacing dir) {
		return this.access.canInsert(dir);
	}

	public boolean canExtract(@Nullable EnumFacing dir) {
		return this.access.canExtract(dir);
	}

	public Collection<EnumFacing> getInputSides() {
		return this.access.getInsertionSides();
	}

	public Collection<EnumFacing> getOutputSides() {
		return this.access.getExtractionSides();
	}

	public void setOutputSides(EnumSet<EnumFacing> sides) {
		for (EnumFacing side : EnumSet.complementOf(sides)) {
			this.access.setExtract(side, false);
		}
	}

	public abstract String getName();

	@Nullable
	public Validator<V> getValidator() {
		return this.validator;
	}

	public BaseSlot<V> setValidator(Validator<V> val) {
		this.validator = val;
		return this;
	}
}
