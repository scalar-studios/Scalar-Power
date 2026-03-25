package com.scalarpower.scalarpower;

import com.scalarpower.scalarpower.registry.ModBlockEntities;
import com.scalarpower.scalarpower.registry.ModBlocks;
import com.scalarpower.scalarpower.registry.ModItems;
import com.scalarpower.scalarpower.registry.ModMenus;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ScalarPower.MODID)
public class ScalarPower {
    public static final String MODID = "scalarpower";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ScalarPower(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);

        LOGGER.info("Scalar Power content registered.");
    }
}
