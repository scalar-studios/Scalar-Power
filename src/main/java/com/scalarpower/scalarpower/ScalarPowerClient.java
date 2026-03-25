package com.scalarpower.scalarpower;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@Mod(value = ScalarPower.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ScalarPower.MODID, value = Dist.CLIENT)
public class ScalarPowerClient {
}
