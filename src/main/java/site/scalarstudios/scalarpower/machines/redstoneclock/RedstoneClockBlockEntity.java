package site.scalarstudios.scalarpower.machines.redstoneclock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import site.scalarstudios.scalarpower.block.ScalarPowerBlockEntities;

public class RedstoneClockBlockEntity extends BlockEntity {

    /**
     * Full period in ticks. The clock fires a 1-tick pulse every PERIOD ticks
     * (19 ticks OFF → 1 tick ON → repeat).
     */
    private static final int PERIOD = 20;

    private int tickCounter = 0;

    public RedstoneClockBlockEntity(BlockPos pos, BlockState state) {
        super(ScalarPowerBlockEntities.REDSTONE_CLOCK.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RedstoneClockBlockEntity blockEntity) {
        if (level == null || level.isClientSide()) return;

        boolean powered = state.getValue(RedstoneClockBlock.POWERED);

        blockEntity.tickCounter++;

        if (blockEntity.tickCounter >= PERIOD) {
            // Fire the pulse: turn ON for this tick.
            blockEntity.tickCounter = 0;
            if (!powered) {
                level.setBlock(pos, state.setValue(RedstoneClockBlock.POWERED, true), 3);
                level.updateNeighborsAt(pos, state.getBlock());
                blockEntity.setChanged();
            }
        } else if (powered) {
            // The tick immediately after the pulse: turn OFF.
            level.setBlock(pos, state.setValue(RedstoneClockBlock.POWERED, false), 3);
            level.updateNeighborsAt(pos, state.getBlock());
            blockEntity.setChanged();
        }
    }

    // Persistence
    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("TickCounter", tickCounter);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        tickCounter = input.getIntOr("TickCounter", 0);
    }
}

