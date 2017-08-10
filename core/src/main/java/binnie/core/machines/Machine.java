package binnie.core.machines;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import binnie.core.BinnieCore;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageBase;
import binnie.core.network.packet.MessageSyncTile;
import binnie.core.network.packet.PacketPayload;

public class Machine implements INetworkedEntity, INbtReadable, INbtWritable, INetwork.TilePacketSync, IMachine, INetwork.GuiNBT {
	private MachinePackage machinePackage;
	private Map<Class<?>, List<MachineComponent>> componentInterfaceMap;
	private Map<Class<?>, MachineComponent> componentMap;
	private TileEntity tile;
	private boolean queuedInventoryUpdate;
	@Nullable
	private GameProfile owner;

	public Machine(final MachinePackage pack, final TileEntity tile) {
		this.componentInterfaceMap = new LinkedHashMap<>();
		this.componentMap = new LinkedHashMap<>();
		this.queuedInventoryUpdate = false;
		this.owner = null;
		this.tile = tile;
		pack.createMachine(this);
		this.machinePackage = pack;
	}

	@Nullable
	public static IMachine getMachine(@Nullable final Object inventory) {
		if (inventory instanceof IMachine) {
			return (IMachine) inventory;
		}
		if (inventory instanceof TileEntityMachine) {
			return ((TileEntityMachine) inventory).getMachine();
		}
		if (inventory instanceof MachineComponent) {
			return ((MachineComponent) inventory).getMachine();
		}
		return null;
	}

	@Nullable
	public static <T> T getInterface(final Class<T> interfac, @Nullable final Object inventory) {
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
	public void addComponent(final MachineComponent component) {
		Preconditions.checkNotNull(component, "Can't have a null machine component!");
		component.setMachine(this);
		this.componentMap.put(component.getClass(), component);
		for (final Class<?> inter : component.getComponentInterfaces()) {
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
		if (this.hasComponent(componentClass)) {
			return componentClass.cast(this.componentMap.get(componentClass));
		}
		throw new IllegalArgumentException("No component found for " + componentClass);
	}

	@Override
	@Nullable
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
		final List<T> interfaces = new ArrayList<>();
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

	public boolean hasComponent(final Class<?> componentClass) {
		return this.componentMap.containsKey(componentClass);
	}

	@Override
	public TileEntity getTileEntity() {
		return this.tile;
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
		List<IInteraction.RightClick> interfaces = this.getInterfaces(IInteraction.RightClick.class);
		for (final IInteraction.RightClick component : interfaces) {
			component.onRightClick(world, player, pos);
		}
	}

	@Override
	public void markDirty() {
		this.queuedInventoryUpdate = true;
	}

	public void onUpdate() {
		if (BinnieCore.getBinnieProxy().isSimulating(this.getWorld())) {
			for (final MachineComponent component : this.getComponents()) {
				component.onUpdate();
			}
		} else {
			//noinspection MethodCallSideOnly
			updateClient();
		}
		if (this.queuedInventoryUpdate) {
			for (final MachineComponent component : this.getComponents()) {
				component.onInventoryUpdate();
			}
			this.queuedInventoryUpdate = false;
		}
	}

	@SideOnly(Side.CLIENT)
	private void updateClient() {
		for (IRender.DisplayTick renders : this.getInterfaces(IRender.DisplayTick.class)) {
			renders.onDisplayTick(this.getWorld(), this.getTileEntity().getPos(), this.getWorld().rand);
		}
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

	@Override
	@Nullable
	public GameProfile getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(final GameProfile owner) {
		this.owner = owner;
	}

	@Nullable
	public MessageBase getRefreshPacket() {
		final NBTTagCompound nbt = new NBTTagCompound();
		this.syncToNBT(nbt);
		if (nbt.hasNoTags()) {
			return null;
		}
		return new MessageSyncTile(BinnieCorePacketID.TILE_DESCRIPTION_SYNC.ordinal(), this.getTileEntity(), nbt);
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
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		for (final INetwork.ReceiveGuiNBT receive : this.getInterfaces(INetwork.ReceiveGuiNBT.class)) {
			receive.receiveGuiNBTOnServer(player, name, nbt);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		for (final INetwork.ReceiveGuiNBT receive : this.getInterfaces(INetwork.ReceiveGuiNBT.class)) {
			receive.receiveGuiNBTOnClient(player, name, nbt);
		}
	}

	@Override
	public void sendGuiNBTToClient(final Map<String, NBTTagCompound> nbt) {
		for (final INetwork.SendGuiNBT send : this.getInterfaces(INetwork.SendGuiNBT.class)) {
			send.sendGuiNBTToClient(nbt);
		}
	}
}
