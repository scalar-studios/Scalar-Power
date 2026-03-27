package site.scalarstudios.scalarpower.content.wire;

import site.scalarstudios.scalarpower.power.PowerNode;
import site.scalarstudios.scalarpower.power.PowerUtil;
import site.scalarstudios.scalarpower.block.ScalarPowerBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CopperWireBlockEntity extends BlockEntity implements PowerNode {
    private static final int ENERGY_CAPACITY = 2000;
    private static final int PUSH_PER_SIDE = 90;

    private final net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler energyHandler = new net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler(ENERGY_CAPACITY, ENERGY_CAPACITY, ENERGY_CAPACITY, 0) {
        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
        }
    };

    public CopperWireBlockEntity(BlockPos pos, BlockState blockState) {
        super(ScalarPowerBlockEntities.COPPER_WIRE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CopperWireBlockEntity blockEntity) {
        if (level == null || level.isClientSide() || blockEntity.energyHandler.getAmountAsLong() <= 0) {
            return;
        }

        int moved = PowerUtil.pushEnergy(level, pos, blockEntity, PUSH_PER_SIDE);
        if (moved > 0) {
            blockEntity.setChanged();
        }
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
    public int getEnergyStored() { return (int)energyHandler.getAmountAsLong(); }
    @Override
    public int getEnergyCapacity() { return (int)energyHandler.getCapacityAsLong(); }
    @Override
    public int receiveEnergy(int amount, boolean simulate) {
        if (simulate) {
            return Math.min(amount, (int)(energyHandler.getCapacityAsLong() - energyHandler.getAmountAsLong()));
        }
        int inserted;
        try (var tx = net.neoforged.neoforge.transfer.transaction.Transaction.openRoot()) {
            inserted = energyHandler.insert(amount, tx);
        }
        return inserted;
    }
    @Override
    public int extractEnergy(int amount, boolean simulate) {
        int extracted = Math.min(amount, (int)energyHandler.getAmountAsLong());
        if (!simulate && extracted > 0) {
            energyHandler.set((int)(energyHandler.getAmountAsLong() - extracted));
        }
        return extracted;
    }
    @Override
    public boolean canConnectPower(Direction side) { return true; }
}
