package net.chixozhmix.chiarmor.item;

import net.chixozhmix.chiarmor.SoundArmor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SoundArmor.MOD_ID);

    //Items
    public static final RegistryObject<CreativeModeTab> ARMOR_TAB = CREATIVE_MOD_TABS.register("armor_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModsItem.SOUND_ARMOR_HELMET.get()))
                    .title(Component.translatable("creativetab.soundarmor.armor"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModsItem.SOUND_ARMOR_HELMET.get());
                        output.accept(ModsItem.SOUND_ARMOR_CHESTPLATE.get());
                        output.accept(ModsItem.SOUND_ARMOR_LEGGINGS.get());
                        output.accept(ModsItem.SOUND_ARMOR_BOOTS.get());
                        output.accept(ModsItem.BARD_HEART.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);

    }
}
