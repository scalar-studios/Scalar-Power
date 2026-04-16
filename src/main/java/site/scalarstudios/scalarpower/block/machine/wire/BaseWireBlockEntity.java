package site.scalarstudios.scalarpower.block.machine.wire;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BaseWireBlockEntity extends BlockEntity {
    // Store behavior for each direction
    private final WireBehavior[] sideBehaviors = new WireBehavior[6];

    protected BaseWireBlockEntity(net.minecraft.world.level.block.entity.BlockEntityType<?> type, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState blockState) {
        super(type, pos, blockState);
        // Initialize all sides with INPUT_OUTPUT behavior
        for (int i = 0; i < 6; i++) {
            sideBehaviors[i] = WireBehavior.INPUT_OUTPUT;
        }
    }

    /**
     * Get the behavior for a specific side
     */
    public WireBehavior getBehavior(Direction side) {
        return sideBehaviors[side.ordinal()];
    }

    /**
     * Set the behavior for a specific side
     */
    public void setBehavior(Direction side, WireBehavior behavior) {
        int index = side.ordinal();
        if (sideBehaviors[index] != behavior) {
            sideBehaviors[index] = behavior;
            setChanged();
        }
    }

    /**
     * Cycle to the next behavior for a specific side
     */
    public WireBehavior cycleBehavior(Direction side) {
        int index = side.ordinal();
        WireBehavior nextBehavior = sideBehaviors[index].next();
        sideBehaviors[index] = nextBehavior;
        setChanged();
        return nextBehavior;
    }

    @Override
    protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput output) {
        super.saveAdditional(output);
        for (Direction direction : Direction.values()) {
            output.putInt("behavior_" + direction.name(), getBehavior(direction).ordinal());
        }
    }

    @Override
    protected void loadAdditional(net.minecraft.world.level.storage.ValueInput input) {
        super.loadAdditional(input);
        for (Direction direction : Direction.values()) {
            String key = "behavior_" + direction.name();
            int ordinal = input.getIntOr(key, WireBehavior.INPUT_OUTPUT.ordinal());
            sideBehaviors[direction.ordinal()] = WireBehavior.fromOrdinal(ordinal);
        }
    }
}



