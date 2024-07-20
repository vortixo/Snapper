package ca.worldwidepixel.screenshotutils.screen.screenshot;

import ca.worldwidepixel.screenshotutils.ScreenshotUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.io.File;
import java.io.IOException;

public class ScreenshotScreen extends Screen {
    public ScreenshotScreen() {
        super(Text.translatable("menu.screenshotutils.screenshotmenu"));
    }
    ScreenshotListWidget screenshotList;

    @Override
    protected void init() {
        if (client == null) return;

        try {
            screenshotList = this.addDrawableChild(new ScreenshotListWidget(client, width, height - 48 - 48, 48, 36, screenshotList));
        } catch (IOException e) {
            ScreenshotUtils.LOGGER.error("Failed to load screenshots", e);
            client.setScreen(new TitleScreen());
            return;
        }

        addDrawableChild(
                ButtonWidget.builder(Text.translatable("button.screenshotutils.folder"), button ->
                                Util.getOperatingSystem().open(new File(client.runDirectory, "screenshots")))
                .dimensions(width / 2 - 152, height - 32, 150, 20)
                .build()
        );

        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close())
                .dimensions(width / 2 + 2, height - 32, 150, 20)
                .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xffffff);
    }
}
