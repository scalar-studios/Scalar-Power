package com.scalarpower.scalarpower.power;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class PowerUtil {
    private PowerUtil() {
    }

    public static int pushEnergy(Level level, BlockPos sourcePos, PowerNode source, int maxTransferPerSide) {
        int totalMoved = 0;
        for (Direction direction : Direction.values()) {
            if (!source.canConnectPower(direction)) {
                continue;
            }

            BlockEntity be = level.getBlockEntity(sourcePos.relative(direction));
            if (!(be instanceof PowerNode target) || !target.canConnectPower(direction.getOpposite())) {
                continue;
            }

            int offer = Math.min(maxTransferPerSide, source.getEnergyStored());
            if (offer <= 0) {
                break;
            }

            int accepted = target.receiveEnergy(offer, false);
            if (accepted > 0) {
                source.extractEnergy(accepted, false);
                totalMoved += accepted;
            }
        }
        return totalMoved;
    }

    public static int pullEnergy(Level level, BlockPos receiverPos, PowerNode receiver, int maxPerSide) {
        int pulled = 0;
        for (Direction direction : Direction.values()) {
            if (!receiver.canConnectPower(direction)) {
                continue;
            }

            BlockEntity be = level.getBlockEntity(receiverPos.relative(direction));
            if (!(be instanceof PowerNode source) || !source.canConnectPower(direction.getOpposite())) {
                continue;
            }

            int wanted = Math.min(maxPerSide, receiver.getEnergyCapacity() - receiver.getEnergyStored());
            if (wanted <= 0) {
                break;
            }

            int extracted = source.extractEnergy(wanted, false);
            if (extracted > 0) {
                int accepted = receiver.receiveEnergy(extracted, false);
                if (accepted < extracted) {
                    source.receiveEnergy(extracted - accepted, false);
                }
                pulled += accepted;
            }
        }
        return pulled;
    }
}

