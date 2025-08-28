package net.chixozhmix.chiarmor.sounds;

import net.chixozhmix.chiarmor.SoundArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SoundArmor.MOD_ID);

    public static final RegistryObject<SoundEvent> HEATR_MUSIC = registrySoundEvents("bard_heart_sound");


    private static RegistryObject<SoundEvent> registrySoundEvents(String name) {
        return SOUND_EVENT.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SoundArmor.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENT.register(eventBus);
    }

}
