package site.scalarstudios.scalarpower.power;

import net.minecraft.core.Direction;

public interface PowerNode {
    int getEnergyStored();

    int getEnergyCapacity();

    int receiveEnergy(int amount, boolean simulate);

    int extractEnergy(int amount, boolean simulate);

    default boolean canConnectPower(Direction side) {
        return true;
    }
}

