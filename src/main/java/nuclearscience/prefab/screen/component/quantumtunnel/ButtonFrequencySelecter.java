package nuclearscience.prefab.screen.component.quantumtunnel;

import com.mojang.blaze3d.systems.RenderSystem;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ButtonFrequencySelecter extends ScreenComponentButton<ButtonFrequencySelecter> {

    public boolean isSelected = false;

    public ButtonFrequencySelecter(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        if(!isVisible()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        graphics.setColor(this.color.rFloat(), this.color.gFloat(), this.color.bFloat(), this.color.aFloat());
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        graphics.blitSprite(VANILLA_BUTTON_SPRITES.get(this.isActive(), isHovered() || isSelected), this.xLocation + guiWidth, this.yLocation + guiHeight, this.width, this.height);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        Font font = minecraft.font;
        Component label = this.getLabel();
        if (label != null) {
            graphics.drawCenteredString(font, label, this.xLocation + guiWidth + this.width / 2, this.yLocation + guiHeight + (this.height - 8) / 2, this.color.color());
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
