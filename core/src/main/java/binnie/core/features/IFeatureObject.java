package binnie.core.features;

public interface IFeatureObject<F extends Feature> {
	void init(F feature);
}
