package binnie.core.machines.inventory;

import binnie.core.util.IValidator;
import forestry.api.core.INBTTagable;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BaseSlot<T> implements INBTTagable, IValidator<T> {
    protected Validator<T> validator;
    protected String unlocName;

    private SidedAccess access;
    private boolean readOnly;
    private int index;

    public BaseSlot(int index, String unlocalizedName) {
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
    public boolean isValid(T item) {
        return item == null || validator == null || validator.isValid(item);
    }

    public abstract T getContent();

    public abstract void setContent(T p0);

    public void setValidator(Validator<T> validator) {
        this.validator = validator;
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

    private void setIndex(int index) {
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

    public void setOutputSides(EnumSet<ForgeDirection> sides) {
        for (ForgeDirection side : EnumSet.complementOf(sides)) {
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

    public boolean canInsert(ForgeDirection dir) {
        return access.canInsert(dir);
    }

    public boolean canExtract(ForgeDirection dir) {
        return access.canExtract(dir);
    }

    public Collection<ForgeDirection> getInputSides() {
        return access.getInsertionSides();
    }

    public Collection<ForgeDirection> getOutputSides() {
        return access.getExtractionSides();
    }

    public void setUnlocalizedName(String name) {
        unlocName = name;
    }

    public abstract String getName();

    public Validator<T> getValidator() {
        return validator;
    }
}
