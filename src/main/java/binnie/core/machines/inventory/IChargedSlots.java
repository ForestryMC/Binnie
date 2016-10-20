package binnie.core.machines.inventory;

public interface IChargedSlots {
    float getCharge(final int p0);

    void setCharge(final int p0, final float p1);

    void alterCharge(final int p0, final float p1);
}
