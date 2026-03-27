package site.scalarstudios.scalarpower.block;

import site.scalarstudios.scalarpower.ScalarPower;
import site.scalarstudios.scalarpower.content.generator.CoalGeneratorBlockEntity;
import site.scalarstudios.scalarpower.content.grinder.GrinderBlockEntity;
import site.scalarstudios.scalarpower.content.poweredfurnace.PoweredFurnaceBlockEntity;
import site.scalarstudios.scalarpower.content.wire.CopperWireBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

<<<<<<<< HEAD:src/main/java/com/scalarpower/scalarpower/registry/ScalarPowerEntities.java
public final class ScalarPowerEntities {
========
public final class ScalarPowerBlockEntities {
>>>>>>>> fc9adea (26.1 Update):src/main/java/site/scalarstudios/scalarpower/block/ScalarPowerBlockEntities.java
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
            .create(net.minecraft.core.registries.Registries.BLOCK_ENTITY_TYPE, ScalarPower.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = BLOCK_ENTITY_TYPES
            .register("coal_generator",
                    () -> new BlockEntityType<>(CoalGeneratorBlockEntity::new, ScalarPowerBlocks.COAL_GENERATOR.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrinderBlockEntity>> GRINDER = BLOCK_ENTITY_TYPES
            .register("grinder",
                    () -> new BlockEntityType<>(GrinderBlockEntity::new, ScalarPowerBlocks.GRINDER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PoweredFurnaceBlockEntity>> POWERED_FURNACE = BLOCK_ENTITY_TYPES
            .register("powered_furnace",
                    () -> new BlockEntityType<>(PoweredFurnaceBlockEntity::new, ScalarPowerBlocks.POWERED_FURNACE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CopperWireBlockEntity>> COPPER_WIRE = BLOCK_ENTITY_TYPES
            .register("copper_wire", () -> new BlockEntityType<>(CopperWireBlockEntity::new, ScalarPowerBlocks.COPPER_WIRE.get()));
<<<<<<<< HEAD:src/main/java/com/scalarpower/scalarpower/registry/ScalarPowerEntities.java

    private ScalarPowerEntities() {
    }
========
>>>>>>>> fc9adea (26.1 Update):src/main/java/site/scalarstudios/scalarpower/block/ScalarPowerBlockEntities.java

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}


