package site.scalarstudios.scalarpower.machines.battery;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SteelBatteryBlock extends BatteryBlock {
    public static final MapCodec<SteelBatteryBlock> CODEC = simpleCodec(SteelBatteryBlock::new);

    public SteelBatteryBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SteelBatteryBlockEntity(pos, state);
    }
}

