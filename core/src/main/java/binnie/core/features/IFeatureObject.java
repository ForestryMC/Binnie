package binnie.core.features;

public interface IFeatureObject<F extends IModFeature<?>> {
	void init(F feature);
}
