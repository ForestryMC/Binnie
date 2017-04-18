// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines;

import binnie.core.network.packet.MessageTileNBT;
import binnie.core.network.BinnieCorePacketID;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import binnie.core.machines.power.ITankMachine;
import net.minecraft.inventory.IInventory;
import binnie.core.machines.component.IRender;
import binnie.core.machines.component.IInteraction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import binnie.core.network.packet.PacketPayload;
import cpw.mods.fml.relauncher.Side;
import binnie.core.BinnieCore;
import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.mojang.authlib.GameProfile;
import net.minecraft.tileentity.TileEntity;
import java.util.List;
import java.util.Map;
import binnie.core.machines.network.INetwork;
import forestry.api.core.INBTTagable;
import binnie.core.network.INetworkedEntity;

public class Machine implements INetworkedEntity, INBTTagable, INetwork.TilePacketSync, IMachine, INetwork.GuiNBT
{
	private MachinePackage machinePackage;
	private Map<Class, List<MachineComponent>> componentInterfaceMap;
	private Map<Class<? extends MachineComponent>, MachineComponent> componentMap;
	private TileEntity tile;
	private boolean queuedInventoryUpdate;
	private int nextProgressBarID;
	private GameProfile owner;

	public Machine(final MachinePackage pack, final TileEntity tile) {
		this.componentInterfaceMap = new LinkedHashMap<Class, List<MachineComponent>>();
		this.componentMap = new LinkedHashMap<Class<? extends MachineComponent>, MachineComponent>();
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
				this.componentInterfaceMap.put(inter, new ArrayList<MachineComponent>());
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
		final ArrayList<T> interfaces = new ArrayList<T>();
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
		if (!BinnieCore.proxy.isSimulating(this.getTileEntity().getWorldObj())) {
			return;
		}
		BinnieCore.proxy.sendNetworkEntityPacket((INetworkedEntity) this.getTileEntity());
	}

	public Side getSide() {
		return BinnieCore.proxy.isSimulating(this.getTileEntity().getWorldObj()) ? Side.SERVER : Side.CLIENT;
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

	public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
		for (final IInteraction.RightClick component : this.getInterfaces(IInteraction.RightClick.class)) {
			component.onRightClick(world, player, x, y, z);
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
		}
		else {
			for (final IRender.DisplayTick renders : this.getInterfaces(IRender.DisplayTick.class)) {
				renders.onDisplayTick(this.getWorld(), this.getTileEntity().xCoord, this.getTileEntity().yCoord, this.getTileEntity().zCoord, this.getWorld().rand);
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
		this.owner = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("owner"));
		this.markDirty();
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbttagcompound) {
		for (final MachineComponent component : this.getComponents()) {
			component.writeToNBT(nbttagcompound);
		}
		if (this.owner != null) {
			final NBTTagCompound nbt = new NBTTagCompound();
			NBTUtil.func_152460_a(nbt, this.owner);
			nbttagcompound.setTag("owner", nbt);
		}
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
		return this.getTileEntity().getWorldObj();
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
		return BinnieCore.instance.getNetworkWrapper().getPacketFrom(new MessageTileNBT(BinnieCorePacketID.TileDescriptionSync.ordinal(), this.getTileEntity(), nbt).getMessage());
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
