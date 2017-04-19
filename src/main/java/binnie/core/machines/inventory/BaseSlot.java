// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import binnie.core.util.IValidator;
import forestry.api.core.INBTTagable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.EnumSet;

public abstract class BaseSlot<T> implements INBTTagable, IValidator<T>
{
	protected Validator<T> validator;
	protected String unlocName;

	private SidedAccess access;
	private boolean readOnly;
	private int index;

	public BaseSlot(final int index, final String unlocalizedName) {
		access = new SidedAccess();
		validator = null;
		readOnly = false;
		setIndex(index);
		setUnlocalizedName(unlocalizedName);
	}

	public void setReadOnly() {
		readOnly = true;
		forbidInsertion();
	}

	@Override
	public boolean isValid(final T item) {
		return item == null
			|| validator == null
			|| validator.isValid(item);
	}

	public abstract T getContent();

	public abstract void setContent(final T p0);

	public void setValidator(final Validator<T> val) {
		this.validator = val;
	}

	public boolean isEmpty() {
		return getContent() == null;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public int getIndex() {
		return index;
	}

	private void setIndex(final int index) {
		this.index = index;
	}

	public boolean canInsert() {
		return !access.getInsertionSides().isEmpty();
	}

	public boolean canExtract() {
		return !access.getExtractionSides().isEmpty();
	}

	public void forbidInteraction() {
		forbidInsertion();
		forbidExtraction();
	}

	// TODO unusing method?
	public void setInputSides(final EnumSet<ForgeDirection> sides) {
		for (final ForgeDirection side : EnumSet.complementOf(sides)) {
			if (side != ForgeDirection.UNKNOWN) {
				access.setInsert(side, false);
			}
		}
	}

	public void setOutputSides(final EnumSet<ForgeDirection> sides) {
		for (final ForgeDirection side : EnumSet.complementOf(sides)) {
			if (side != ForgeDirection.UNKNOWN) {
				access.setExtract(side, false);
			}
		}
	}

	public void forbidExtraction() {
		access.setExtract(false);
		access.forbidExtractChange();
	}

	public void forbidInsertion() {
		access.setInsert(false);
		access.forbidInsertChange();
	}

	public boolean canInsert(final ForgeDirection dir) {
		return access.canInsert(dir);
	}

	public boolean canExtract(final ForgeDirection dir) {
		return access.canExtract(dir);
	}

	public Collection<ForgeDirection> getInputSides() {
		return this.access.getInsertionSides();
	}

	public Collection<ForgeDirection> getOutputSides() {
		return this.access.getExtractionSides();
	}

	public void setUnlocalizedName(final String name) {
		this.unlocName = name;
	}

	public abstract String getName();

	public Validator<T> getValidator() {
		return this.validator;
	}
}
