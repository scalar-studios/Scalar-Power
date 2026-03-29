package site.scalarstudios.scalarpower.content.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import site.scalarstudios.scalarpower.block.ScalarPowerBlockEntities;

public class BatteryBlockEntity extends BlockEntity implements MenuProvider {
    private static final int ENERGY_CAPACITY = 1_000_000;
    private static final String CONTAINER_TRANSLATION_KEY = "container.scalarpower.battery";

    private final String containerTranslationKey;
    private final net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler energyHandler;

    public BatteryBlockEntity(BlockPos pos, BlockState blockState) {
        this(ScalarPowerBlockEntities.BATTERY.get(), pos, blockState, ENERGY_CAPACITY, CONTAINER_TRANSLATION_KEY);
    }

    protected BatteryBlockEntity(BlockEntityType<? extends BatteryBlockEntity> type, BlockPos pos, BlockState blockState,
            int energyCapacity, String containerTranslationKey) {
        super(type, pos, blockState);
        this.containerTranslationKey = containerTranslationKey;
        this.energyHandler = new net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler(
                energyCapacity,
                energyCapacity,
                energyCapacity,
                0) {
            @Override
            protected void onEnergyChanged(int previousAmount) {
                setChanged();
            }
        };
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
        return Component.translatable(containerTranslationKey);
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

