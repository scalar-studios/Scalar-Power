package site.scalarstudios.scalarpower.block.device.infinitewatersource;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import site.scalarstudios.scalarpower.block.ScalarPowerBlockEntities;

public class InfiniteWaterSourceBlockEntity extends BlockEntity {

    private static final FluidResource WATER_RESOURCE = FluidResource.of(Fluids.WATER);

    /**
     * Represents an effectively-infinite amount of water (1 billion mB).
     * Pipes and other fluid consumers will never drain it completely because
     * onContentsChanged immediately refills the handler.
     */
    private static final int INFINITE_AMOUNT = 1_000_000_000;

    /** Guards against recursive calls inside onContentsChanged. */
    private boolean isResetting = false;

    private final FluidStacksResourceHandler fluidHandler = new FluidStacksResourceHandler(1, INFINITE_AMOUNT) {
        {
            // Pre-fill with water on construction so the tank is never empty
            set(0, WATER_RESOURCE, INFINITE_AMOUNT);
        }

        /** Reject all insertions – this is a source, not a tank. */
        @Override
        public boolean isValid(int index, FluidResource resource) {
            return false;
        }

        /**
         * Called after any state change (including extractions by pipes).
         * Immediately refills the tank so the source stays effectively infinite.
         */
        @Override
        protected void onContentsChanged(int index, net.neoforged.neoforge.fluids.FluidStack previousContents) {
            if (!isResetting) {
                isResetting = true;
                if (getAmountAsInt(0) < INFINITE_AMOUNT) {
                    set(0, WATER_RESOURCE, INFINITE_AMOUNT);
                }
                isResetting = false;
            }
        }
    };

    public InfiniteWaterSourceBlockEntity(BlockPos pos, BlockState state) {
        super(ScalarPowerBlockEntities.INFINITE_WATER_SOURCE.get(), pos, state);
    }

    /**
     * Exposes the infinite-water handler to the fluid capability system so that
     * pipes (e.g. Pipez) can extract water from any side.
     */
    public ResourceHandler<FluidResource> getFluidHandler(Direction side) {
        return fluidHandler;
    }
}

