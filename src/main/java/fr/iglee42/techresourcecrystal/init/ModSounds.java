package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TechResourcesCrystal.MODID);

    public static final RegistryObject<SoundEvent> ITEM_GOAT_HORN_BASE = build("item.goat_horn.base");
    public static final RegistryObject<SoundEvent> ITEM_GOAT_HORN_EASTER_0 = build("item.goat_horn.drakonic");
    public static final RegistryObject<SoundEvent> ITEM_GOAT_HORN_EASTER_1 = build("item.goat_horn.drakonic_1");

    private static RegistryObject<SoundEvent> build(String id)
    {
        return REGISTER.register(id, () -> new SoundEvent(new ResourceLocation(TechResourcesCrystal.MODID, id)));
    }
}
