package net.chixozhmix.chiarmor.block;

import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.item.ModsItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SoundArmor.MOD_ID);

    public static final RegistryObject<Block> SHELL = registerBlock("shell", () ->
            new Block(BlockBehaviour.Properties.of().strength(100.0F, 1200.0F)));
    public static final RegistryObject<Block> ACTIVATOR = registerBlock("activator", () ->
            new ActivatorBlock(BlockBehaviour.Properties.of().strength(1.0F, 1.0F)));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModsItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
