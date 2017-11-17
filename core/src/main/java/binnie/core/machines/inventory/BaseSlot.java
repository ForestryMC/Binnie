package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;

import net.minecraft.util.EnumFacing;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import binnie.core.util.IValidator;
import net.minecraft.util.ResourceLocation;

public abstract class BaseSlot<T> implements INbtWritable, INbtReadable, IValidator<T> {
	@Nullable
	protected final ResourceLocation unlocLocation;
	@Nullable
	private Validator<T> validator;
	private final SidedAccess access;
	private boolean readOnly;
	private int index;

	public BaseSlot(final int index, final ResourceLocation unlocLocation) {
		this.access = new SidedAccess();
		this.setIndex(index);
		this.unlocLocation = unlocLocation;
	}

	public void setReadOnly() {
		this.readOnly = true;
		this.forbidInsertion();
	}

	@Override
	public boolean isValid(final T item) {
		return item == null || this.validator == null || this.validator.isValid(item);
	}

	@Nullable
	public abstract T getContent();

	public boolean isReadOnly() {
		return this.readOnly;
	}

	public int getIndex() {
		return this.index;
	}

	private void setIndex(final int index) {
		this.index = index;
	}

	public boolean canInsert() {
		return !this.access.getInsertionSides().isEmpty();
	}

	public boolean canExtract() {
		return !this.access.getExtractionSides().isEmpty();
	}

	public void forbidInteraction() {
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

	public void setOutputSides(final EnumSet<EnumFacing> sides) {
		for (final EnumFacing side : EnumSet.complementOf(sides)) {
			//if (side != ForgeDirection.UNKNOWN) {
			this.access.setExtract(side, false);
			//}
		}
	}

	public abstract String getName();

	@Nullable
	public Validator<T> getValidator() {
		return this.validator;
	}

	public BaseSlot<T> setValidator(final Validator<T> val) {
		this.validator = val;
		return this;
	}
}
