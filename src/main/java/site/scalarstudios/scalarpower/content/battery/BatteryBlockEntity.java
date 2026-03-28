package site.scalarstudios.scalarpower.content.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import site.scalarstudios.scalarpower.block.ScalarPowerBlockEntities;

public class BatteryBlockEntity extends BlockEntity implements MenuProvider {
    private static final int ENERGY_CAPACITY = 1_000_000;

    private final net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler energyHandler =
            new net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler(
                    ENERGY_CAPACITY,
                    ENERGY_CAPACITY,
                    ENERGY_CAPACITY,
                    0) {
                @Override
                protected void onEnergyChanged(int previousAmount) {
                    setChanged();
                }
            };

    public BatteryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ScalarPowerBlockEntities.BATTERY.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energyHandler.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energyHandler.deserialize(input);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.scalarpower.battery");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new BatteryMenu(id, inv, this, new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> (int) energyHandler.getAmountAsLong();
                    case 1 -> (int) energyHandler.getCapacityAsLong();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    energyHandler.set(value);
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    public EnergyHandler getEnergyHandler(Direction side) {
        return energyHandler;
    }
}

