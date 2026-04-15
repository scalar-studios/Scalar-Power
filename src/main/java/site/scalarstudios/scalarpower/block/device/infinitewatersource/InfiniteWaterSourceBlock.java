package site.scalarstudios.scalarpower.block.device.infinitewatersource;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;

public class InfiniteWaterSourceBlock extends BaseEntityBlock {

    public static final MapCodec<InfiniteWaterSourceBlock> CODEC = simpleCodec(InfiniteWaterSourceBlock::new);

    public InfiniteWaterSourceBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {

        // Buckets, fluid containers, etc. – handled automatically via the fluid capability
        if (FluidUtil.interactWithFluidHandler(player, hand, level, pos, hitResult.getDirection())) {
            return InteractionResult.SUCCESS;
        }

        // Glass bottle → water bottle
        if (stack.is(Items.GLASS_BOTTLE)) {
            if (!level.isClientSide()) {
                ItemStack waterBottle = new ItemStack(Items.POTION);
                waterBottle.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));

                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, waterBottle);
                } else if (!player.getInventory().add(waterBottle)) {
                    player.drop(waterBottle, false);
                }

                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfiniteWaterSourceBlockEntity(pos, state);
    }
}



