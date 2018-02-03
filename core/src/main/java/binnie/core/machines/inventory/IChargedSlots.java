package binnie.core.machines.inventory;

public interface IChargedSlots {
	float getCharge(int index);

	void setCharge(int index, float value);

	void alterCharge(int index, float value);
}
