package us.timinc.randommachines;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import us.timinc.randommachines.command.CommandRecipe;
import us.timinc.randommachines.proxy.CommonProxy;
import us.timinc.randommachines.recipes.JarRecipe;
import us.timinc.randommachines.recipes.RecipeDeserializer;
import org.apache.logging.log4j.LogManager;
import us.timinc.randommachines.recipes.Recipes;
import us.timinc.randommachines.tileentity.JarEntity;

@Mod(modid = us.timinc.randommachines.RandomMachines.MODID, name = us.timinc.randommachines.RandomMachines.NAME, version = us.timinc.randommachines.RandomMachines.VERSION)
public class RandomMachines {
  public static final String MODID = "randommachines";
  public static final String NAME = "Random Machines";
  public static final String VERSION = "1.0.3";

  @SidedProxy(clientSide = "us.timinc.randommachines.proxy.ClientProxy", serverSide = "us.timinc.randommachines.proxy.ServerProxy")
  public static CommonProxy proxy;

  @Mod.Instance
  public static RandomMachines instance;

  private static final Logger LOGGER = LogManager.getLogger(NAME);

  public Recipes recipes;

  public static void LOG(String message, Object... variables) {
    if (LOGGER != null) {
      LOGGER.info(String.format(message, variables));
    }
  }

  public RandomMachines() {
    registerDeserializers();
  }

  public void registerDeserializers() {
    LOG("--Registering deserializers--");
    RecipeDeserializer.registerDescription("jar", JarRecipe.class);
  }

  @Mod.EventHandler
  public void serverLoad(FMLServerStartingEvent event) {
    event.registerServerCommand(new CommandRecipe());
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    recipes = new Recipes();
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    proxy.postInit(e);
  }
}
