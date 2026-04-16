package site.scalarstudios.scalarpower.block.machine.wire;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "scalarpower")
public class WireInteractionHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        // Only handle on server
        if (event.getLevel().isClientSide()) {
            return;
        }

        // Check if player is holding wrench
        if (!event.getItemStack().is(site.scalarstudios.scalarpower.item.ScalarPowerItems.WRENCH.get())) {
            return;
        }

        // Get the block entity
        BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
        if (!(blockEntity instanceof BaseWireBlockEntity wireEntity)) {
            return;
        }

        Player player = event.getEntity();

        // Get the direction that was clicked
        net.minecraft.core.Direction clickedSide = event.getFace();
        if (clickedSide == null) {
            return;
        }

        // Check if shift is held
        if (player.isShiftKeyDown()) {
            // Shift-click: Cycle to next behavior
            WireBehavior newBehavior = wireEntity.cycleBehavior(clickedSide);
            player.sendSystemMessage(
                Component.translatable("chat.scalarpower.behavior_set", clickedSide.getName(), newBehavior.getDisplayName())
            );
        } else {
            // Normal click: Show current behavior
            WireBehavior current = wireEntity.getBehavior(clickedSide);
            player.sendSystemMessage(
                Component.translatable("chat.scalarpower.current_behavior_display", clickedSide.getName(), current.getDisplayName())
            );
        }

        // Consume the interaction
        event.setCanceled(true);
    }
}






