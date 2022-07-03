package binnie.core.machines.power;

public interface IErrorStateSource {
    ErrorState canWork();

    ErrorState canProgress();
}
