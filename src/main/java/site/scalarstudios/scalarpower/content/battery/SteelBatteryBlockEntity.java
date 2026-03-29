package site.scalarstudios.scalarpower.content.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import site.scalarstudios.scalarpower.block.ScalarPowerBlockEntities;

public class SteelBatteryBlockEntity extends BatteryBlockEntity {
    private static final int ENERGY_CAPACITY = 10_000_000;
    private static final String CONTAINER_TRANSLATION_KEY = "container.scalarpower.steel_battery";

    public SteelBatteryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ScalarPowerBlockEntities.STEEL_BATTERY.get(), pos, blockState, ENERGY_CAPACITY, CONTAINER_TRANSLATION_KEY);
    }
}

