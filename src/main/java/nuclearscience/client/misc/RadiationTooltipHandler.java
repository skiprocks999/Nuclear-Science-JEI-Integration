package nuclearscience.client.misc;

import com.mojang.datafixers.util.Either;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import nuclearscience.References;
import nuclearscience.api.radiation.util.RadiationShielding;
import nuclearscience.common.reloadlistener.RadiationShieldingRegister;
import nuclearscience.common.reloadlistener.RadioactiveItemRegister;
import nuclearscience.prefab.utils.NuclearDisplayUnits;
import nuclearscience.prefab.utils.NuclearTextUtils;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.GAME)
public class RadiationTooltipHandler {

    @SubscribeEvent
    public static void renderTooltip(RenderTooltipEvent.GatherComponents event) {

        if(Screen.hasShiftDown()) {
            ItemStack stack = event.getItemStack();
            if(stack.getItem() instanceof BlockItem blockItem) {
                RadiationShielding shielding = RadiationShieldingRegister.getValue(blockItem.getBlock());
                if(shielding.amount() <= 0) {
                    return;
                }
                event.getTooltipElements().add(Either.left(NuclearTextUtils.tooltip("radiationshieldingamount", ChatFormatter.getChatDisplayShort(shielding.amount(), NuclearDisplayUnits.RAD).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY)));
                //event.getTooltipElements().add(Either.left(NuclearTextUtils.tooltip("radiationshieldinglevel", Component.literal(shielding.level() + "").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY)));
            }
        } else if(Screen.hasControlDown()) {
            event.getTooltipElements().add(Either.left(ChatFormatter.getChatDisplayShort(RadioactiveItemRegister.getValue(event.getItemStack().getItem()).amount(), NuclearDisplayUnits.RAD).withStyle(ChatFormatting.YELLOW)));
        }




    }

}
