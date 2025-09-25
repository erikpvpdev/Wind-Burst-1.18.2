package code.erikpvp.windburstmod;

import code.erikpvp.windburstmod.enchant.Enchants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("windburst")
public class Windburstmod
{
    public static final String MOD_ID = "windburst";
    private static final Logger LOGGER = LogManager.getLogger();

    public Windburstmod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Enchants.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("Wind Burst Mod loaded");
    }
}

