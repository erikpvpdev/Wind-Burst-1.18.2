package code.erikpvp.windburstmod.enchant;


import code.erikpvp.windburstmod.Windburstmod;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Enchants {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Windburstmod.MOD_ID);

    public static final RegistryObject<Enchantment> WIND_BURST =
            ENCHANTMENTS.register("wind_burst", () ->
                    new WindBurstEnchantment(Enchantment.Rarity.RARE));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
