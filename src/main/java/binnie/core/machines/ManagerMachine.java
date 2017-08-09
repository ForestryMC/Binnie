package binnie.core.machines;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import binnie.core.BinnieCore;
import binnie.core.ManagerBase;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorSprite;

public class ManagerMachine extends ManagerBase {
	private Map<Class<?>, Class<?>[]> componentInterfaceMap;
	private Map<String, MachineGroup> machineGroups;
	private int nextNetworkID;

	public ManagerMachine() {
		this.componentInterfaceMap = new HashMap<>();
		this.machineGroups = new HashMap<>();
		this.nextNetworkID = 0;
	}

	public void registerMachineGroup(final MachineGroup group) {
		this.machineGroups.put(group.getUID(), group);
	}

	public MachineGroup getGroup(final String name) {
		return this.machineGroups.get(name);
	}

	public MachinePackage getPackage(final String group, final String name) {
		final MachineGroup machineGroup = this.getGroup(group);
		return (machineGroup == null) ? null : machineGroup.getPackage(name);
	}

	@SuppressWarnings("unchecked")
	private void registerComponentClass(final Class<? extends MachineComponent> component) {
		if (this.componentInterfaceMap.containsKey(component)) {
			return;
		}
		final Set<Class<?>> interfaces = new HashSet<>();
		for (Class<?> currentClass = component; currentClass != null; currentClass = currentClass.getSuperclass()) {
			Collections.addAll(interfaces, currentClass.getInterfaces());
		}
		interfaces.remove(INbtWritable.class);
		interfaces.remove(INbtReadable.class);
		this.componentInterfaceMap.put(component, interfaces.toArray(new Class[0]));
		final int networkID = this.nextNetworkID++;
	}

	@Override
	public void preInit() {
		SlotValidator.spriteBee = new ValidatorSprite(BinnieCore.getInstance(), "validator/bee.0", "validator/bee.1");
		SlotValidator.spriteFrame = new ValidatorSprite(BinnieCore.getInstance(), "validator/frame.0", "validator/frame.1");
		SlotValidator.spriteCircuit = new ValidatorSprite(BinnieCore.getInstance(), "validator/circuit.0", "validator/circuit.1");
		SlotValidator.spriteBlock = new ValidatorSprite(BinnieCore.getInstance(), "validator/block.0", "validator/block.1");
	}

	@Override
	public void postInit() {
		// TODO fix rendering
		Object rendererMachine = null; // BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine");
		BinnieCore.getBinnieProxy().registerTileEntity(TileEntityMachine.class, "binnie.tile.machine", rendererMachine);
	}

	public Class<?>[] getComponentInterfaces(final Class<? extends MachineComponent> clss) {
		if (!this.componentInterfaceMap.containsKey(clss)) {
			this.registerComponentClass(clss);
		}
		return this.componentInterfaceMap.get(clss);
	}
}
