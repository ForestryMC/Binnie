package binnie.core.machines;


import binnie.core.BinnieCore;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ITankMachine;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageTileNBT;
import binnie.core.network.packet.PacketPayload;
import com.mojang.authlib.GameProfile;
import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;

public class Machine implements INetworkedEntity, INbtReadable, INbtWritable, INetwork.TilePacketSync, IMachine, INetwork.GuiNBT {
    private MachinePackage machinePackage;
    private Map<Class, List<MachineComponent>> componentInterfaceMap;
    private Map<Class<? extends MachineComponent>, MachineComponent> componentMap;
    private TileEntity tile;
    private boolean queuedInventoryUpdate;
    private int nextProgressBarID;
    private GameProfile owner;

    public Machine(final MachinePackage pack, final TileEntity tile) {
        this.componentInterfaceMap = new LinkedHashMap<>();
        this.componentMap = new LinkedHashMap<>();
        this.queuedInventoryUpdate = false;
        this.nextProgressBarID = 0;
        this.owner = null;
        this.tile = tile;
        pack.createMachine(this);
        this.machinePackage = pack;
    }

    @Override
    public void addComponent(final MachineComponent component) {
        if (component == null) {
            throw new NullPointerException("Can't have a null machine component!");
        }
        component.setMachine(this);
        this.componentMap.put(component.getClass(), component);
        for (final Class inter : component.getComponentInterfaces()) {
            if (!this.componentInterfaceMap.containsKey(inter)) {
                this.componentInterfaceMap.put(inter, new ArrayList<>());
            }
            this.componentInterfaceMap.get(inter).add(component);
        }
    }

    public Collection<MachineComponent> getComponents() {
        return this.componentMap.values();
    }

    public <T extends MachineComponent> T getComponent(final Class<T> componentClass) {
        return this.hasComponent(componentClass) ? ((T) componentClass.cast(this.componentMap.get(componentClass))) : null;
    }

    @Override
    public <T> T getInterface(final Class<T> interfaceClass) {
        if (this.hasInterface(interfaceClass)) {
            return this.getInterfaces(interfaceClass).get(0);
        }
        if (interfaceClass.isInstance(this.getPackage())) {
            return interfaceClass.cast(this.getPackage());
        }
        for (final MachineComponent component : this.getComponents()) {
            if (interfaceClass.isInstance(component)) {
                return interfaceClass.cast(component);
            }
        }
        return null;
    }

    @Override
    public <T> List<T> getInterfaces(final Class<T> interfaceClass) {
        final ArrayList<T> interfaces = new ArrayList<>();
        if (!this.hasInterface(interfaceClass)) {
            return interfaces;
        }
        for (final MachineComponent component : this.componentInterfaceMap.get(interfaceClass)) {
            interfaces.add(interfaceClass.cast(component));
        }
        return interfaces;
    }

    public boolean hasInterface(final Class<?> interfaceClass) {
        return this.componentInterfaceMap.containsKey(interfaceClass);
    }

    public boolean hasComponent(final Class<? extends MachineComponent> componentClass) {
        return this.componentMap.containsKey(componentClass);
    }

    @Override
    public TileEntity getTileEntity() {
        return this.tile;
    }

    public void sendPacket() {
        if (!BinnieCore.proxy.isSimulating(this.getTileEntity().getWorld())) {
            return;
        }
        BinnieCore.proxy.sendNetworkEntityPacket((INetworkedEntity) this.getTileEntity());
    }

    public Side getSide() {
        return BinnieCore.proxy.isSimulating(this.getTileEntity().getWorld()) ? Side.SERVER : Side.CLIENT;
    }

    @Override
    public void writeToPacket(final PacketPayload payload) {
        for (final MachineComponent component : this.getComponents()) {
            if (component instanceof INetworkedEntity) {
                ((INetworkedEntity) component).writeToPacket(payload);
            }
        }
    }

    @Override
    public void readFromPacket(final PacketPayload payload) {
        for (final MachineComponent component : this.getComponents()) {
            if (component instanceof INetworkedEntity) {
                ((INetworkedEntity) component).readFromPacket(payload);
            }
        }
    }

    public void onRightClick(final World world, final EntityPlayer player, final BlockPos pos) {
        for (final IInteraction.RightClick component : this.getInterfaces(IInteraction.RightClick.class)) {
            component.onRightClick(world, player, pos);
        }
    }

    @Override
    public void markDirty() {
        this.queuedInventoryUpdate = true;
    }

    public void onUpdate() {
        if (BinnieCore.proxy.isSimulating(this.getWorld())) {
            for (final MachineComponent component : this.getComponents()) {
                component.onUpdate();
            }
        } else {
            for (final IRender.DisplayTick renders : this.getInterfaces(IRender.DisplayTick.class)) {
                renders.onDisplayTick(this.getWorld(), this.getTileEntity().getPos().getX(), this.getTileEntity().getPos().getY(), this.getTileEntity().getPos().getZ(), this.getWorld().rand);
            }
        }
        if (this.queuedInventoryUpdate) {
            for (final MachineComponent component : this.getComponents()) {
                component.onInventoryUpdate();
            }
            this.queuedInventoryUpdate = false;
        }
    }

    public IInventory getInventory() {
        return this.getInterface(IInventory.class);
    }

    public ITankMachine getTankContainer() {
        return this.getInterface(ITankMachine.class);
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        for (final MachineComponent component : this.getComponents()) {
            component.readFromNBT(nbttagcompound);
        }
        this.owner = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("owner"));
        this.markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
        for (final MachineComponent component : this.getComponents()) {
            component.writeToNBT(nbttagcompound);
        }
        if (this.owner != null) {
            final NBTTagCompound nbt = new NBTTagCompound();
            NBTUtil.writeGameProfile(nbt, this.owner);
            nbttagcompound.setTag("owner", nbt);
        }
        return nbttagcompound;
    }

    @Override
    public MachinePackage getPackage() {
        return this.machinePackage;
    }

    public static IMachine getMachine(final Object inventory) {
        if (inventory != null && inventory instanceof IMachine) {
            return (IMachine) inventory;
        }
        if (inventory != null && inventory instanceof TileEntityMachine) {
            return ((TileEntityMachine) inventory).getMachine();
        }
        if (inventory != null && inventory instanceof MachineComponent) {
            return ((MachineComponent) inventory).getMachine();
        }
        return null;
    }

    public static <T> T getInterface(final Class<T> interfac, final Object inventory) {
        final IMachine machine = getMachine(inventory);
        if (machine != null) {
            return machine.getInterface(interfac);
        }
        if (interfac.isInstance(inventory)) {
            return interfac.cast(inventory);
        }
        return null;
    }

    @Override
    public MachineUtil getMachineUtil() {
        return new MachineUtil(this);
    }

    @Override
    public World getWorld() {
        return this.getTileEntity().getWorld();
    }

    public void onBlockDestroy() {
        for (final MachineComponent component : this.getComponents()) {
            component.onDestruction();
        }
    }

    public int getUniqueProgressBarID() {
        return this.nextProgressBarID++;
    }

    @Override
    public GameProfile getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(final GameProfile owner) {
        this.owner = owner;
    }

    public Packet getDescriptionPacket() {
        final NBTTagCompound nbt = new NBTTagCompound();
        this.syncToNBT(nbt);
        if (nbt.hasNoTags()) {
            return null;
        }
        return BinnieCore.instance.getNetworkWrapper().getPacketFrom(new MessageTileNBT(BinnieCorePacketID.TileDescriptionSync.ordinal(), this.getTileEntity(), nbt).GetMessage());
    }

    @Override
    public void syncToNBT(final NBTTagCompound nbt) {
        for (final INetwork.TilePacketSync comp : this.getInterfaces(INetwork.TilePacketSync.class)) {
            comp.syncToNBT(nbt);
        }
    }

    @Override
    public void syncFromNBT(final NBTTagCompound nbt) {
        for (final INetwork.TilePacketSync comp : this.getInterfaces(INetwork.TilePacketSync.class)) {
            comp.syncFromNBT(nbt);
        }
    }

    @Override
    public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
        for (final INetwork.RecieveGuiNBT recieve : this.getInterfaces(INetwork.RecieveGuiNBT.class)) {
            recieve.recieveGuiNBT(side, player, name, nbt);
        }
    }

    @Override
    public void sendGuiNBT(final Map<String, NBTTagCompound> nbt) {
        for (final INetwork.SendGuiNBT recieve : this.getInterfaces(INetwork.SendGuiNBT.class)) {
            recieve.sendGuiNBT(nbt);
        }
    }
}
