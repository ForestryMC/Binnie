package binnie.genetics.gui.analyst;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.IButterfly;

import binnie.Binnie;
import binnie.botany.api.genetics.IFlower;
import binnie.core.AbstractMod;
import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.IWidget;
import binnie.core.gui.Widget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.events.EventKey;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.minecraft.InventoryType;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.WindowInventory;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlide;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.window.Panel;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.gui.analyst.bee.AnalystPageProducts;
import binnie.genetics.gui.analyst.butterfly.AnalystPageSpecimen;
import binnie.genetics.gui.analyst.flower.AnalystPageAppearance;
import binnie.genetics.gui.analyst.flower.AnalystPageSoil;
import binnie.genetics.gui.analyst.tree.AnalystPageClimate;
import binnie.genetics.gui.analyst.tree.AnalystPageFruit;
import binnie.genetics.gui.analyst.tree.AnalystPageGrowth;
import binnie.genetics.gui.analyst.tree.AnalystPageWood;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.machine.analyser.Analyser;

public class WindowAnalyst extends Window {
	@Nullable
	IWidget baseWidget;
	ControlScrollableContent leftPage;
	ControlScrollableContent rightPage;
	@Nullable
	Control tabBar;
	Panel analystPanel;
	List<ControlAnalystPage> analystPages;
	@Nullable
	Area analystPageSize;
	boolean isDatabase;
	boolean isMaster;
	boolean lockedSearch;
	@Nullable
	IIndividual current;
	@Nullable
	BreedingSystem currentSystem;
	private Control analystNone;
	private ControlSlide slideUpInv;

	public WindowAnalyst(EntityPlayer player, @Nullable IInventory inventory, Side side, boolean database, boolean master) {
		super(312, 230, player, inventory, side);
		baseWidget = null;
		tabBar = null;
		analystPages = new ArrayList<>();
		analystPageSize = null;
		isDatabase = false;
		isMaster = false;
		lockedSearch = false;
		current = null;
		currentSystem = null;
		isDatabase = database;
		isMaster = master;
		lockedSearch = isDatabase;
	}

	public static GeneticsGUI.WindowFactory create(boolean database, boolean master) {
		return (player, inventory, side) -> new WindowAnalyst(player, inventory, side, database, master);
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Analyst";
	}

	private void setupValidators() {
		if (!isDatabase) {
			getWindowInventory().setValidator(0, new SlotValidator.Individual() {
				@Override
				public boolean isValid(ItemStack itemStack) {
					return Analyser.isAnalysed(itemStack) || (Analyser.isAnalysable(itemStack) && !getWindowInventory().getStackInSlot(1).isEmpty());
				}
			});
			getWindowInventory().setValidator(1, new SlotValidator.Item(GeneticsItems.DNADye.get(1), ModuleMachine.spriteDye));
		}
	}

	@Override
	public void initialiseServer() {
		for (BreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
			ISpeciesRoot root = system.getSpeciesRoot();
			if (root != null) {
				IBreedingTracker tracker = root.getBreedingTracker(getWorld(), getUsername());
				if (tracker != null) {
					tracker.synchToPlayer(getPlayer());
				}
			}
		}
		//create slots
		getWindowInventory().createSlot(0);
		getWindowInventory().createSlot(1);
		setupValidators();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		setTitle(isDatabase ? (isMaster ? I18N.localise("genetics.gui.registry.master.title") : I18N.localise("genetics.gui.registry.title")) : I18N.localise("genetics.gui.analyst.title"));
		getWindowInventory().createSlot(0);
		baseWidget = new Widget(this);
		int x = 16;
		int y = 28;
		if (isDatabase) {
			for (BreedingSystem syst : Binnie.GENETICS.getActiveSystems()) {
				new ControlSystemButton(x, y, this, syst);
				x += 22;
			}
		} else {
			new ControlSlot.Builder(this, x, y + 1).assign(InventoryType.WINDOW, 0);
			x += 22;
			new ControlSlot.Builder(this, x, y + 1).assign(InventoryType.WINDOW, 1);
			x += 26;
			setupValidators();
		}
		tabBar = new Control(this, x, 28, getWidth() - 16 - x, 20);
		analystPanel = new AnalystPanel(this);
		if (!isDatabase) {
			slideUpInv = new ControlSlide(this, (getSize().xPos() - 244) / 2, getSize().yPos() - 80 + 1, 244, 80, Position.BOTTOM);
			new ControlPlayerInventory(slideUpInv, true);
			slideUpInv.setSlide(false);
		}
		addEventHandler(new EventKey.Down.Handler() {
			@Override
			public void onEvent(EventKey.Down event) {
				if (event.getKey() == 205) {
					shiftPages(true);
				}
				if (event.getKey() == 203) {
					shiftPages(false);
				}
			}
		});
		if (!isDatabase) {
			analystNone = new Control(analystPanel, 0, 0, analystPanel.getWidth(), analystPanel.getHeight()) {
				@Override
				public void initialise() {
					new ControlTextCentered(this, 20, I18N.localise("genetics.gui.analyst.desc")).setColor(4473924);
					new ControlPlayerInventory(this);
				}
			};
		}
		setIndividual(null);
		setSystem(Binnie.GENETICS.beeBreedingSystem);
	}

	@SideOnly(Side.CLIENT)
	public void updatePages(boolean systemChange) {
		int oldLeft = -1;
		int oldRight = -1;
		if (!systemChange) {
			oldLeft = analystPages.indexOf(leftPage.getContent());
			oldRight = analystPages.indexOf(rightPage.getContent());
		}
		ControlAnalystPage databasePage = null;
		if (isDatabase && !systemChange) {
			databasePage = ((analystPages.size() > 0) ? analystPages.get(0) : null);
		}
		analystPages.clear();
		setPage(leftPage, null);
		setPage(rightPage, null);
		createPages(databasePage);
		tabBar.deleteAllChildren();
		if (analystPages.size() > 0) {
			int width = tabBar.getWidth() / analystPages.size();
			int x = 0;
			for (ControlAnalystPage page : analystPages) {
				new ControlAnalystButton(tabBar, x, 0, width, tabBar.getHeight(), this, page);
				x += width;
			}
			setPage(leftPage, analystPages.get((oldLeft >= 0) ? oldLeft : 0));
			if (analystPages.size() > 1) {
				setPage(rightPage, analystPages.get((oldRight >= 0) ? oldRight : 1));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void createPages(ControlAnalystPage databasePage){
		if (isDatabase) {
			analystPages.add((databasePage != null) ? databasePage : new AnalystPageDatabase(analystPanel, analystPageSize, currentSystem, isMaster));
		}
		if (current != null) {
			analystPages.add(new AnalystPageDescription(analystPanel, analystPageSize, current));
			analystPages.add(new AnalystPageGenome(analystPanel, analystPageSize, true, current));
			if (!isDatabase) {
				analystPages.add(new AnalystPageGenome(analystPanel, analystPageSize, false, current));
				analystPages.add(new AnalystPageKaryogram(analystPanel, analystPageSize, current));
			}
			if (!(current instanceof ITree)) {
				analystPages.add(new AnalystPageClimate(analystPanel, analystPageSize, current));
			}
			if (current instanceof IBee) {
				analystPages.add(new AnalystPageProducts(analystPanel, analystPageSize, (IBee) current));
			} else if (current instanceof ITree) {
				analystPages.add(new AnalystPageFruit(analystPanel, analystPageSize, (ITree) current));
				analystPages.add(new AnalystPageWood(analystPanel, analystPageSize, (ITree) current));
			} else if (current instanceof IFlower) {
				analystPages.add(new AnalystPageSoil(analystPanel, analystPageSize, (IFlower) current));
			} else if (current instanceof IButterfly) {
				analystPages.add(new AnalystPageSpecimen(analystPanel, analystPageSize, (IButterfly) current));
			}
			analystPages.add(new AnalystPageBiology(analystPanel, analystPageSize, current));
			if (current instanceof IBee || current instanceof IButterfly) {
				analystPages.add(new AnalystPageBehaviour(analystPanel, analystPageSize, current));
			} else if (current instanceof ITree) {
				analystPages.add(new AnalystPageGrowth(analystPanel, analystPageSize, current));
			} else if (current instanceof IFlower) {
				analystPages.add(new AnalystPageAppearance(analystPanel, analystPageSize, (IFlower) current));
			}
			analystPages.add(new AnalystPageMutations(analystPanel, analystPageSize, current, isMaster));
		}
	}

	public void shiftPages(boolean right) {
		if (analystPages.size() < 2) {
			return;
		}
		int leftIndex = analystPages.indexOf(leftPage.getContent());
		int rightIndex = analystPages.indexOf(rightPage.getContent());
		if (right && rightIndex + 1 >= analystPages.size()) {
			return;
		}
		if (!lockedSearch && !right && leftIndex <= 0) {
			return;
		}
		if (!lockedSearch && !right && rightIndex <= 1) {
			return;
		}
		int newRightIndex = rightIndex + (right ? 1 : -1);
		int newLeftIndex = lockedSearch ? 0 : (newRightIndex - 1);
		float oldRightPercent = 0;
		float oldLeftPercent = 0;
		if (newLeftIndex == rightIndex) {
			oldRightPercent = rightPage.getPercentageIndex();
		}
		if (newRightIndex == leftIndex) {
			oldLeftPercent = leftPage.getPercentageIndex();
		}
		setPage(leftPage, null);
		setPage(rightPage, null);
		setPage(leftPage, analystPages.get(newLeftIndex));
		setPage(rightPage, analystPages.get(newRightIndex));
		analystPages.get(newLeftIndex).show();
		if (oldRightPercent != 0) {
			leftPage.setPercentageIndex(oldRightPercent);
		}
		if (oldLeftPercent != 0) {
			rightPage.setPercentageIndex(oldLeftPercent);
		}
	}

	public void setPage(ControlScrollableContent side, @Nullable ControlAnalystPage page) {
		ControlAnalystPage existingPage = (ControlAnalystPage) side.getContent();
		if (existingPage != null) {
			existingPage.hide();
			side.setScrollableContent(null);
		}
		if (page != null) {
			page.show();
			side.setScrollableContent(page);
			side.setPercentageIndex(0);
			page.setPosition(side.getPosition().add(1, 1));
		}
	}

	@Override
	public void onWindowInventoryChanged() {
		super.onWindowInventoryChanged();
		WindowInventory inv = getWindowInventory();
		ItemStack stack = inv.getStackInSlot(0);
		if (!stack.isEmpty() && !Analyser.isAnalysed(stack)) {
			inv.setInventorySlotContents(0, Analyser.analyse(stack, getWorld(), getUsername()));
			inv.decrStackSize(1, 1);
		}

		if (isClient()) {
			//noinspection MethodCallSideOnly
			setStack(stack);
		}
	}

	@SideOnly(Side.CLIENT)
	public void setStack(ItemStack stack) {
		IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		setIndividual(ind);
	}

	public IIndividual getIndividual() {
		return current;
	}

	@SideOnly(Side.CLIENT)
	public void setIndividual(IIndividual ind) {
		if (!isDatabase) {
			if (ind == null) {
				analystNone.show();
				slideUpInv.hide();
			} else {
				analystNone.hide();
				slideUpInv.show();
			}
		}
		if (ind == current || (ind != null && current != null && ind.isGeneticEqual(current))) {
			return;
		}
		boolean systemChange = (current = ind) != null && ind.getGenome().getSpeciesRoot() != getSystem().getSpeciesRoot();
		if (systemChange) {
			currentSystem = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
		}
		updatePages(systemChange);
	}

	public BreedingSystem getSystem() {
		return currentSystem;
	}

	@SideOnly(Side.CLIENT)
	public void setSystem(BreedingSystem system) {
		if (system == currentSystem) {
			return;
		}
		currentSystem = system;
		current = null;
		updatePages(true);
	}
}
