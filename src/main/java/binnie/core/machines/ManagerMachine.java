package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.ManagerBase;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ManagerMachine extends ManagerBase {
	private Map<Class<?>, Class<?>[]> componentInterfaceMap;
	private Map<String, MachineGroup> machineGroups;
	private Map<Integer, Class<?>> networkIDToComponent;
	private Map<Class<?>, Integer> componentToNetworkID;
	private int nextNetworkID;
	private int machineRenderID;

	public ManagerMachine() {
		this.componentInterfaceMap = new HashMap<>();
		this.machineGroups = new HashMap<>();
		this.networkIDToComponent = new HashMap<>();
		this.componentToNetworkID = new HashMap<>();
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

	private void registerComponentClass(final Class<? extends MachineComponent> component) {
		if (this.componentInterfaceMap.containsKey(component)) {
			return;
		}
		final Set<Class<?>> interfaces = new HashSet<>();
		for (Class<?> currentClass = component; currentClass != null; currentClass = null) {
			Collections.addAll(interfaces, currentClass.getInterfaces());
			currentClass = currentClass.getSuperclass();
			if (currentClass == Object.class) {
			}
		}
		interfaces.remove(INbtWritable.class);
		interfaces.remove(INbtReadable.class);
		this.componentInterfaceMap.put(component, interfaces.toArray(new Class[0]));
		final int networkID = this.nextNetworkID++;
		this.networkIDToComponent.put(networkID, component);
		this.componentToNetworkID.put(component, networkID);
	}

	public int getNetworkID(final Class<?> component) {
		return this.componentToNetworkID.get(component);
	}

	public Class<?> getComponentClass(final int networkID) {
		return this.networkIDToComponent.get(networkID);
	}

	public int getMachineRenderID() {
		return this.machineRenderID;
	}

	@Override
	public void init() {
		//TODO renderupdate
		//this.machineRenderID = BinnieCore.proxy.getUniqueRenderID();
		SlotValidator.IconBee = new ValidatorIcon(BinnieCore.instance, "validator/bee.0", "validator/bee.1");
		SlotValidator.IconFrame = new ValidatorIcon(BinnieCore.instance, "validator/frame.0", "validator/frame.1");
		SlotValidator.IconCircuit = new ValidatorIcon(BinnieCore.instance, "validator/circuit.0", "validator/circuit.1");
		SlotValidator.IconBlock = new ValidatorIcon(BinnieCore.instance, "validator/block.0", "validator/block.1");
	}

	@Override
	public void postInit() {
		//BinnieCore.proxy.registerBlockRenderer(BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
		//BinnieCore.proxy.registerTileEntity(TileEntityMachine.class, "binnie.tile.machine", BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
	}

	public Class<?>[] getComponentInterfaces(final Class<? extends MachineComponent> clss) {
		if (!this.componentInterfaceMap.containsKey(clss)) {
			this.registerComponentClass(clss);
		}
		return this.componentInterfaceMap.get(clss);
	}
}
