package binnie.core.triggers;

class TriggerProvider// implements ITriggerProvider
{
	//	static TriggerProvider instance;
	//	public static List<BinnieTrigger> triggers;
	//
	//	@Override
	//	public Collection<ITriggerExternal> getExternalTriggers(final ForgeDirection side, final TileEntity tile) {
	//		final LinkedList<TriggerData> list = new LinkedList<TriggerData>();
	//		final LinkedList<ITriggerExternal> triggerData = new LinkedList<ITriggerExternal>();
	//		if (tile instanceof IBuildcraft.TriggerProvider) {
	//			((IBuildcraft.TriggerProvider) tile).getTriggers(list);
	//		}
	//		for (final TriggerData data : list) {
	//			if (data.getKey() != null && data.getKey().getUniqueTag() != null) {
	//				triggerData.add(data.getKey());
	//			}
	//		}
	//		return triggerData;
	//	}
	//
	//	public static boolean isTriggerActive(final ITriggerExternal trigger, final TileEntity tile) {
	//		final LinkedList<TriggerData> list = new LinkedList<TriggerData>();
	//		final LinkedList<ITriggerExternal> triggerData = new LinkedList<ITriggerExternal>();
	//		if (tile instanceof IBuildcraft.TriggerProvider) {
	//			((IBuildcraft.TriggerProvider) tile).getTriggers(list);
	//		}
	//		for (final TriggerData data : list) {
	//			if (data.getKey() == trigger) {
	//				return data.getValue();
	//			}
	//		}
	//		return false;
	//	}
	//
	//	@Override
	//	public Collection<ITriggerInternal> getInternalTriggers(final IStatementContainer container) {
	//		return new ArrayList<ITriggerInternal>();
	//	}
	//
	//	static {
	//		TriggerProvider.instance = new TriggerProvider();
	//		TriggerProvider.triggers = new ArrayList<BinnieTrigger>();
	//	}
}
