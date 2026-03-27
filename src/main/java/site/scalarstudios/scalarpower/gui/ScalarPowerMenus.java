package site.scalarstudios.scalarpower.gui;

import site.scalarstudios.scalarpower.content.generator.CoalGeneratorMenu;
import site.scalarstudios.scalarpower.content.grinder.GrinderMenu;
import site.scalarstudios.scalarpower.content.poweredfurnace.PoweredFurnaceMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import site.scalarstudios.scalarpower.ScalarPower;

<<<<<<<< HEAD:src/main/java/com/scalarpower/scalarpower/registry/ScalarPowerMenus.java
public final class ScalarPowerMenus {
========
public class ScalarPowerMenus {
>>>>>>>> fc9adea (26.1 Update):src/main/java/site/scalarstudios/scalarpower/gui/ScalarPowerMenus.java
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ScalarPower.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU = MENUS
            .register("coal_generator", () -> IMenuTypeExtension.create(CoalGeneratorMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<GrinderMenu>> GRINDER_MENU = MENUS
            .register("grinder", () -> IMenuTypeExtension.create(GrinderMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<PoweredFurnaceMenu>> POWERED_FURNACE_MENU = MENUS
            .register("powered_furnace", () -> IMenuTypeExtension.create(PoweredFurnaceMenu::new));

<<<<<<<< HEAD:src/main/java/com/scalarpower/scalarpower/registry/ScalarPowerMenus.java
    private ScalarPowerMenus() {
    }

========
>>>>>>>> fc9adea (26.1 Update):src/main/java/site/scalarstudios/scalarpower/gui/ScalarPowerMenus.java
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
