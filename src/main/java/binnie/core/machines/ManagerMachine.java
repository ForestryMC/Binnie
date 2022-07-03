package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.ManagerBase;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import forestry.api.core.INBTTagable;
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
        componentInterfaceMap = new HashMap<>();
        machineGroups = new HashMap<>();
        networkIDToComponent = new HashMap<>();
        componentToNetworkID = new HashMap<>();
        nextNetworkID = 0;
    }

    public void registerMachineGroup(MachineGroup group) {
        machineGroups.put(group.getUID(), group);
    }

    public MachineGroup getGroup(String name) {
        return machineGroups.get(name);
    }

    public MachinePackage getPackage(String group, String name) {
        MachineGroup machineGroup = getGroup(group);
        return (machineGroup == null) ? null : machineGroup.getPackage(name);
    }

    private void registerComponentClass(Class<? extends MachineComponent> component) {
        if (componentInterfaceMap.containsKey(component)) {
            return;
        }

        Set<Class<?>> interfaces = new HashSet<>();
        for (Class<?> currentClass = component; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Collections.addAll(interfaces, currentClass.getInterfaces());
        }

        interfaces.remove(INBTTagable.class);
        componentInterfaceMap.put(component, interfaces.toArray(new Class[0]));
        int networkID = nextNetworkID++;
        networkIDToComponent.put(networkID, component);
        componentToNetworkID.put(component, networkID);
    }

    public int getNetworkID(Class<?> component) {
        return componentToNetworkID.get(component);
    }

    public Class<?> getComponentClass(int networkID) {
        return networkIDToComponent.get(networkID);
    }

    public int getMachineRenderID() {
        return machineRenderID;
    }

    @Override
    public void init() {
        machineRenderID = BinnieCore.proxy.getUniqueRenderID();
        SlotValidator.IconBee = new ValidatorIcon(BinnieCore.instance, "validator/bee.0", "validator/bee.1");
        SlotValidator.IconFrame = new ValidatorIcon(BinnieCore.instance, "validator/frame.0", "validator/frame.1");
        SlotValidator.IconCircuit =
                new ValidatorIcon(BinnieCore.instance, "validator/circuit.0", "validator/circuit.1");
        SlotValidator.IconBlock = new ValidatorIcon(BinnieCore.instance, "validator/block.0", "validator/block.1");
    }

    @Override
    public void postInit() {
        BinnieCore.proxy.registerBlockRenderer(BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
        BinnieCore.proxy.registerTileEntity(
                TileEntityMachine.class,
                "binnie.tile.machine",
                BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
    }

    public Class<?>[] getComponentInterfaces(Class<? extends MachineComponent> clss) {
        if (!componentInterfaceMap.containsKey(clss)) {
            registerComponentClass(clss);
        }
        return componentInterfaceMap.get(clss);
    }
}
