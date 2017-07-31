package binnie.extratrees.machines.designer;

public abstract class Designer {
	public static int BEESWAX_SLOT = 0;
	public static int DESIGN_SLOT_1 = 1;
	public static int DESIGN_SLOT_2 = 2;

	public static class PackageWoodworker extends PackageDesigner {
		public PackageWoodworker() {
			super(DesignerType.Woodworker);
		}
	}

	public static class PackagePanelworker extends PackageDesigner {
		public PackagePanelworker() {
			super(DesignerType.Panelworker);
		}
	}

	public static class PackageGlassworker extends PackageDesigner {
		public PackageGlassworker() {
			super(DesignerType.GlassWorker);
		}
	}

	public static class PackageTileworker extends PackageDesigner {
		public PackageTileworker() {
			super(DesignerType.Tileworker);
		}
	}

}
