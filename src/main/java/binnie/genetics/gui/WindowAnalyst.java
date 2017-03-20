package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.core.AbstractMod;
import binnie.core.genetics.BreedingSystem;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.scroll.ControlScrollBar;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.Widget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.events.EventKey;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlide;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.window.Panel;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.machine.analyser.Analyser;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.IButterfly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
	IArea analystPageSize;
	boolean isDatabase;
	boolean isMaster;
	boolean lockedSearch;
	private Control analystNone;
	private ControlSlide slideUpInv;
	@Nullable
	IIndividual current;
	@Nullable
	BreedingSystem currentSystem;

	public static GeneticsGUI.WindowFactory create(final boolean database, final boolean master) {
		return (player, inventory, side) -> new WindowAnalyst(player, inventory, side, database, master);
	}

	public WindowAnalyst(final EntityPlayer player, @Nullable final IInventory inventory, final Side side, final boolean database, final boolean master) {
		super(312, 230, player, inventory, side);
		this.baseWidget = null;
		this.tabBar = null;
		this.analystPages = new ArrayList<>();
		this.analystPageSize = null;
		this.isDatabase = false;
		this.isMaster = false;
		this.lockedSearch = false;
		this.current = null;
		this.currentSystem = null;
		this.isDatabase = database;
		this.isMaster = master;
		this.lockedSearch = this.isDatabase;
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
		if (!this.isDatabase) {
			this.getWindowInventory().setValidator(0, new SlotValidator.Individual() {
				@Override
				public boolean isValid(final ItemStack itemStack) {
					return Analyser.isAnalysed(itemStack) || (Analyser.isAnalysable(itemStack) && WindowAnalyst.this.getWindowInventory().getStackInSlot(1) != null);
				}
			});
			this.getWindowInventory().setValidator(1, new SlotValidator.Item(GeneticsItems.DNADye.get(1), ModuleMachine.spriteDye));
		}
	}

	@Override
	public void initialiseServer() {
		for (final BreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
			ISpeciesRoot root = system.getSpeciesRoot();
			if (root != null) {
				final IBreedingTracker tracker = root.getBreedingTracker(this.getWorld(), this.getUsername());
				if (tracker != null) {
					tracker.synchToPlayer(this.getPlayer());
				}
			}
		}
		this.setupValidators();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle(this.isDatabase ? (this.isMaster ? "Master Registry" : "Registry") : "Analyst");
		final BreedingSystem system = Binnie.GENETICS.beeBreedingSystem;
		final IIndividual ind = system.getDefaultIndividual();
		final ItemStack stack = system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType());
		this.getWindowInventory().createSlot(0);
		this.baseWidget = new Widget(this);
		int x = 16;
		final int y = 28;
		if (this.isDatabase) {
			for (final BreedingSystem syst : Binnie.GENETICS.getActiveSystems()) {
				new Control(this, x, y, 20, 20) {
					@Override
					public void initialise() {
						this.addAttribute(Attribute.MouseOver);
						this.addSelfEventHandler(new EventMouse.Down.Handler() {
							@Override
							@SideOnly(Side.CLIENT)
							public void onEvent(final EventMouse.Down event) {
								WindowAnalyst.this.setSystem(syst);
							}
						});
					}

					@Override
					public void getTooltip(final Tooltip tooltip) {
						tooltip.add(syst.getName());
					}

					@Override
					@SideOnly(Side.CLIENT)
					public void onRenderBackground(int guiWidth, int guiHeight) {
						RenderUtil.setColour(syst.getColour());
						final int outset = (WindowAnalyst.this.getSystem() == syst) ? 1 : 0;
						CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea().outset(outset));
						if (WindowAnalyst.this.getSystem() == syst) {
							RenderUtil.setColour(1140850688 + syst.getColour());
							CraftGUI.render.texture(CraftGUITexture.TabSolid, this.getArea().outset(outset));
						}
						RenderUtil.drawItem(new IPoint(2, 2), syst.getItemStackRepresentitive());
					}
				};
				x += 22;
			}
		} else {
			new ControlSlot.Builder(this, x, y + 1).assign(InventoryType.Window, 0);
			x += 22;
			new ControlSlot.Builder(this, x, y + 1).assign(InventoryType.Window, 1);
			x += 26;
			this.setupValidators();
		}
		this.tabBar = new Control(this, x, 28, this.width() - 16 - x, 20);
		this.analystPanel = new Panel(this, 16, 54, 280, 164, MinecraftGUI.PanelType.Outline) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				RenderUtil.drawGradientRect(this.getArea(), 1157627903, 1728053247);
				super.onRenderBackground(guiWidth, guiHeight);
			}

			@Override
			public void initialise() {
				this.setColour(4473924);
				final int sectionWidth = (this.width() - 8 - 4) / 2;
				WindowAnalyst.this.leftPage = new ControlScrollableContent<IWidget>(this, 3, 3, sectionWidth + 2, this.height() - 8 + 2, 0) {
					@Override
					@SideOnly(Side.CLIENT)
					public void onRenderBackground(int guiWidth, int guiHeight) {
						if (this.getContent() == null) {
							return;
						}
						RenderUtil.setColour(this.getContent().getColour());
						CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea());
					}
				};
				new ControlScrollBar(this, sectionWidth + 2 - 3, 6, 3, this.height() - 8 + 2 - 6, WindowAnalyst.this.leftPage) {
					@Override
					@SideOnly(Side.CLIENT)
					public void onRenderBackground(int guiWidth, int guiHeight) {
						if (!this.isEnabled()) {
							return;
						}
						if (WindowAnalyst.this.leftPage.getContent() == null) {
							return;
						}
						RenderUtil.drawGradientRect(this.getArea(), 1140850688 + WindowAnalyst.this.leftPage.getContent().getColour(), 1140850688 + WindowAnalyst.this.leftPage.getContent().getColour());
						RenderUtil.drawSolidRect(this.getRenderArea(), WindowAnalyst.this.leftPage.getContent().getColour());
					}
				};
				WindowAnalyst.this.rightPage = new ControlScrollableContent<IWidget>(this, 3 + sectionWidth + 4, 3, sectionWidth + 2, this.height() - 8 + 2, 0) {
					@Override
					@SideOnly(Side.CLIENT)
					public void onRenderBackground(int guiWidth, int guiHeight) {
						if (this.getContent() == null) {
							return;
						}
						RenderUtil.setColour(this.getContent().getColour());
						CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea());
					}
				};
				new ControlScrollBar(this, sectionWidth + 2 - 3 + sectionWidth + 4, 6, 3, this.height() - 8 + 2 - 6, WindowAnalyst.this.rightPage) {
					@Override
					@SideOnly(Side.CLIENT)
					public void onRenderBackground(int guiWidth, int guiHeight) {
						if (!this.isEnabled()) {
							return;
						}
						if (WindowAnalyst.this.rightPage.getContent() == null) {
							return;
						}
						RenderUtil.drawGradientRect(this.getArea(), 1140850688 + WindowAnalyst.this.rightPage.getContent().getColour(), 1140850688 + WindowAnalyst.this.rightPage.getContent().getColour());
						RenderUtil.drawSolidRect(this.getRenderArea(), WindowAnalyst.this.rightPage.getContent().getColour());
					}
				};
				WindowAnalyst.this.analystPageSize = new IArea(1, 1, sectionWidth, this.height() - 8);
			}
		};
		if (!this.isDatabase) {
			this.slideUpInv = new ControlSlide(this, (this.getSize().x() - 244) / 2, this.getSize().y() - 80 + 1, 244, 80, Position.BOTTOM);
			new ControlPlayerInventory(this.slideUpInv, true);
			this.slideUpInv.setSlide(false);
		}
		this.addEventHandler(new EventKey.Down.Handler() {
			@Override
			public void onEvent(final EventKey.Down event) {
				if (event.getKey() == 205) {
					WindowAnalyst.this.shiftPages(true);
				}
				if (event.getKey() == 203) {
					WindowAnalyst.this.shiftPages(false);
				}
			}
		});
		if (!this.isDatabase) {
			this.analystNone = new Control(this.analystPanel, 0, 0, this.analystPanel.width(), this.analystPanel.height()) {
				@Override
				public void initialise() {
					new ControlTextCentered(this, 20, "Add a bee, tree, flower or butterfly to the top left slot. DNA Dye is required if it has not been analysed yet. This dye can also convert vanilla items to breedable individuals.").setColour(4473924);
					new ControlPlayerInventory(this);
				}
			};
		}
		this.setIndividual(null);
		this.setSystem(Binnie.GENETICS.beeBreedingSystem);
	}

	@SideOnly(Side.CLIENT)
	public void setIndividual(final IIndividual ind) {
		if (!this.isDatabase) {
			if (ind == null) {
				this.analystNone.show();
				this.slideUpInv.hide();
			} else {
				this.analystNone.hide();
				this.slideUpInv.show();
			}
		}
		if (ind == this.current || (ind != null && this.current != null && ind.isGeneticEqual(this.current))) {
			return;
		}
		final boolean systemChange = (this.current = ind) != null && ind.getGenome().getSpeciesRoot() != this.getSystem().getSpeciesRoot();
		if (systemChange) {
			this.currentSystem = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
		}
		this.updatePages(systemChange);
	}

	@SideOnly(Side.CLIENT)
	public void setSystem(final BreedingSystem system) {
		if (system == this.currentSystem) {
			return;
		}
		this.currentSystem = system;
		this.current = null;
		this.updatePages(true);
	}

	@SideOnly(Side.CLIENT)
	public void updatePages(final boolean systemChange) {
		int oldLeft = -1;
		int oldRight = -1;
		if (!systemChange) {
			oldLeft = this.analystPages.indexOf(this.leftPage.getContent());
			oldRight = this.analystPages.indexOf(this.rightPage.getContent());
		}
		ControlAnalystPage databasePage = null;
		if (this.isDatabase && !systemChange) {
			databasePage = ((this.analystPages.size() > 0) ? this.analystPages.get(0) : null);
		}
		this.analystPages.clear();
		this.setPage(this.leftPage, null);
		this.setPage(this.rightPage, null);
		if (this.isDatabase) {
			this.analystPages.add((databasePage != null) ? databasePage : new AnalystPageDatabase(this.analystPanel, this.analystPageSize, this.currentSystem, this.isMaster));
		}
		if (this.current != null) {
			this.analystPages.add(new AnalystPageDescription(this.analystPanel, this.analystPageSize, this.current));
			this.analystPages.add(new AnalystPageGenome(this.analystPanel, this.analystPageSize, true, this.current));
			if (!this.isDatabase) {
				this.analystPages.add(new AnalystPageGenome(this.analystPanel, this.analystPageSize, false, this.current));
				this.analystPages.add(new AnalystPageKaryogram(this.analystPanel, this.analystPageSize, this.current));
			}
			if (!(this.current instanceof ITree)) {
				this.analystPages.add(new AnalystPageClimate(this.analystPanel, this.analystPageSize, this.current));
			}
			if (this.current instanceof IBee) {
				this.analystPages.add(new AnalystPageProducts(this.analystPanel, this.analystPageSize, (IBee) this.current));
			} else if (this.current instanceof ITree) {
				this.analystPages.add(new AnalystPageFruit(this.analystPanel, this.analystPageSize, (ITree) this.current));
				this.analystPages.add(new AnalystPageWood(this.analystPanel, this.analystPageSize, (ITree) this.current));
			} else if (this.current instanceof IFlower) {
				this.analystPages.add(new AnalystPageSoil(this.analystPanel, this.analystPageSize, (IFlower) this.current));
			} else if (this.current instanceof IButterfly) {
				this.analystPages.add(new AnalystPageSpecimen(this.analystPanel, this.analystPageSize, (IButterfly) this.current));
			}
			this.analystPages.add(new AnalystPageBiology(this.analystPanel, this.analystPageSize, this.current));
			if (this.current instanceof IBee || this.current instanceof IButterfly) {
				this.analystPages.add(new AnalystPageBehaviour(this.analystPanel, this.analystPageSize, this.current));
			} else if (this.current instanceof ITree) {
				this.analystPages.add(new AnalystPageGrowth(this.analystPanel, this.analystPageSize, this.current));
			} else if (this.current instanceof IFlower) {
				this.analystPages.add(new AnalystPageAppearance(this.analystPanel, this.analystPageSize, (IFlower) this.current));
			}
			this.analystPages.add(new AnalystPageMutations(this.analystPanel, this.analystPageSize, this.current, this.isMaster));
		}
		this.tabBar.deleteAllChildren();
		final int width = this.tabBar.width() / this.analystPages.size();
		int x = 0;
		for (final ControlAnalystPage page : this.analystPages) {
			new ControlTooltip(this.tabBar, x, 0, width, this.tabBar.height()) {
				ControlAnalystPage value;

				@Override
				public void getTooltip(final Tooltip tooltip) {
					tooltip.add(this.value.getTitle());
				}

				@Override
				protected void initialise() {
					super.initialise();
					this.addAttribute(Attribute.MouseOver);
					this.value = page;
					this.addSelfEventHandler(new EventMouse.Down.Handler() {
						@Override
						public void onEvent(final EventMouse.Down event) {
							final int currentIndex = WindowAnalyst.this.analystPages.indexOf(WindowAnalyst.this.rightPage.getContent());
							int clickedIndex = WindowAnalyst.this.analystPages.indexOf(value);
							if (WindowAnalyst.this.isDatabase) {
								if (clickedIndex != 0 && clickedIndex != currentIndex) {
									WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, value);
								}
							} else {
								if (clickedIndex < 0) {
									clickedIndex = 0;
								}
								if (clickedIndex < currentIndex) {
									++clickedIndex;
								}
								WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, null);
								WindowAnalyst.this.setPage(WindowAnalyst.this.leftPage, null);
								WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, WindowAnalyst.this.analystPages.get(clickedIndex));
								WindowAnalyst.this.setPage(WindowAnalyst.this.leftPage, WindowAnalyst.this.analystPages.get(clickedIndex - 1));
							}
						}
					});
				}

				@Override
				@SideOnly(Side.CLIENT)
				public void onRenderBackground(int guiWidth, int guiHeight) {
					final boolean active = this.value == WindowAnalyst.this.leftPage.getContent() || this.value == WindowAnalyst.this.rightPage.getContent();
					RenderUtil.setColour((active ? -16777216 : 1140850688) + this.value.getColour());
					CraftGUI.render.texture(CraftGUITexture.TabSolid, this.getArea().inset(1));
					RenderUtil.setColour(this.value.getColour());
					CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea().inset(1));
					super.onRenderBackground(guiWidth, guiHeight);
				}
			};
			x += width;
		}
		if (this.analystPages.size() > 0) {
			this.setPage(this.leftPage, this.analystPages.get((oldLeft >= 0) ? oldLeft : 0));
		}
		if (this.analystPages.size() > 1) {
			this.setPage(this.rightPage, this.analystPages.get((oldRight >= 0) ? oldRight : 1));
		}
	}

	public void shiftPages(final boolean right) {
		if (this.analystPages.size() < 2) {
			return;
		}
		final int leftIndex = this.analystPages.indexOf(this.leftPage.getContent());
		final int rightIndex = this.analystPages.indexOf(this.rightPage.getContent());
		if (right && rightIndex + 1 >= this.analystPages.size()) {
			return;
		}
		if (!this.lockedSearch && !right && leftIndex <= 0) {
			return;
		}
		if (!this.lockedSearch && !right && rightIndex <= 1) {
			return;
		}
		final int newRightIndex = rightIndex + (right ? 1 : -1);
		final int newLeftIndex = this.lockedSearch ? 0 : (newRightIndex - 1);
		float oldRightPercent = 0;
		float oldLeftPercent = 0;
		if (newLeftIndex == rightIndex) {
			oldRightPercent = this.rightPage.getPercentageIndex();
		}
		if (newRightIndex == leftIndex) {
			oldLeftPercent = this.leftPage.getPercentageIndex();
		}
		this.setPage(this.leftPage, null);
		this.setPage(this.rightPage, null);
		this.setPage(this.leftPage, this.analystPages.get(newLeftIndex));
		this.setPage(this.rightPage, this.analystPages.get(newRightIndex));
		this.analystPages.get(newLeftIndex).show();
		if (oldRightPercent != 0) {
			this.leftPage.setPercentageIndex(oldRightPercent);
		}
		if (oldLeftPercent != 0) {
			this.rightPage.setPercentageIndex(oldLeftPercent);
		}
	}

	public void setPage(final ControlScrollableContent side, @Nullable final ControlAnalystPage page) {
		final ControlAnalystPage existingPage = (ControlAnalystPage) side.getContent();
		if (existingPage != null) {
			existingPage.hide();
			side.setScrollableContent(null);
		}
		if (page != null) {
			page.show();
			side.setScrollableContent(page);
			side.setPercentageIndex(0);
			page.setPosition(side.pos().add(1, 1));
		}
	}

	@Override
	public void onWindowInventoryChanged() {
		super.onWindowInventoryChanged();
		if (this.getWindowInventory().getStackInSlot(0) != null && !Analyser.isAnalysed(this.getWindowInventory().getStackInSlot(0))) {
			this.getWindowInventory().setInventorySlotContents(0, Analyser.analyse(this.getWindowInventory().getStackInSlot(0)));
			this.getWindowInventory().decrStackSize(1, 1);
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(this.getWindowInventory().getStackInSlot(0));
		if (ind != null) {
			ind.getGenome().getSpeciesRoot().getBreedingTracker(this.getWorld(), this.getUsername()).registerBirth(ind);
		}

		if (this.isClient()) {
			//noinspection MethodCallSideOnly
			this.setStack(this.getWindowInventory().getStackInSlot(0));
		}
	}

	@SideOnly(Side.CLIENT)
	public void setStack(final ItemStack stack) {
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		this.setIndividual(ind);
	}

	public IIndividual getIndividual() {
		return this.current;
	}

	public BreedingSystem getSystem() {
		return this.currentSystem;
	}
}
