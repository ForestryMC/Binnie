package binnie.core.machines.inventory;

public interface IChargedSlots {
    float getCharge(int slot);

    void setCharge(int slot, float value);

    void alterCharge(int slot, float value);
}
