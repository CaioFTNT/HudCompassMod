package com.johndue.hudcompassmod.config;

import com.johndue.hudcompassmod.config.widgets.ConfigSliderWidget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

	private Screen parentScreen;

	public ConfigScreen(Screen parentScreen) {
		super(Text.of("HUD Compass config"));
		this.parentScreen = parentScreen;
	}

	@Override
	protected void init() {
		
		CyclingButtonWidget<Boolean> toggleButton = CyclingButtonWidget.onOffBuilder(Text.of("ON"), Text.of("OFF"))
			.omitKeyText()
			.initially(ModConfig.get().compassEnabled)
            .build(this.width-200, 40, 150, 20, Text.empty(), (btn, value) -> {
                ModConfig.get().compassEnabled = value;
            });
		
		CyclingButtonWidget<Boolean> deathMarkerButton = CyclingButtonWidget.onOffBuilder(Text.of("ON"), Text.of("OFF"))
			.omitKeyText()
			.initially(ModConfig.get().deathMarker)
            .build(this.width-200, 75, 150, 20, Text.empty(), (btn, value) -> {
                ModConfig.get().deathMarker = value;
            });
		ConfigSliderWidget widthSlider = new ConfigSliderWidget(this.width-200, 100, 150, 20, ModConfig.get().compassWidth, 20, 100, value -> ModConfig.get().compassWidth = (int)value);
		ConfigSliderWidget fovSlider = new ConfigSliderWidget(this.width-200, 125, 150, 20, ModConfig.get().compassFOV, 90, 270, value -> ModConfig.get().compassFOV = (int)value);
		ConfigSliderWidget opacitySlider = new ConfigSliderWidget(this.width-200, 150, 150, 20, ModConfig.get().directionsOpacity*100, 0, 100, value -> ModConfig.get().directionsOpacity = value/100d);
		ConfigSliderWidget brightnessSlider = new ConfigSliderWidget(this.width-200, 175, 150, 20, ModConfig.get().directionsBrightness*100, 0, 100, value -> ModConfig.get().directionsBrightness = value/100d);

		CyclingButtonWidget<NorthHighlightColors> northHighlight = CyclingButtonWidget.<NorthHighlightColors>builder(state -> Text.literal(state.name().replace('_', ' ')))
			.omitKeyText()
			.values(NorthHighlightColors.values())
			.initially(ModConfig.get().highlightNorth)
            .build(this.width-200, 200, 150, 20, Text.empty(), (btn, value) -> {
                ModConfig.get().highlightNorth = value;
            });
		
		this.addDrawableChild(toggleButton);

		this.addDrawableChild(deathMarkerButton);
		this.addDrawableChild(widthSlider);
		this.addDrawableChild(fovSlider);

		this.addDrawableChild(opacitySlider);
		this.addDrawableChild(brightnessSlider);
		this.addDrawableChild(northHighlight);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		context.drawText(this.textRenderer, "Hud compass settings", this.width/2-this.textRenderer.getWidth("Hud compass settings")/2, 15, 0xffffffff, true);

		context.drawText(this.textRenderer, "Enable compass", 50, 46, 0xffffffff, true);
		
		context.drawText(this.textRenderer, "Death marker", 50, 81, 0xffffffff, true);
		context.drawText(this.textRenderer, "Compass width (%)", 50, 106, 0xffffffff, true);
		context.drawText(this.textRenderer, "Compass FOV (°)", 50, 131, 0xffffffff, true);
		context.drawText(this.textRenderer, "Compass opacity (%)", 50, 156, 0xffffffff, true);
		context.drawText(this.textRenderer, "Directions brightness (%)", 50, 181, 0xffffffff, true);
		context.drawText(this.textRenderer, "North highlight", 50, 206, 0xffffffff, true);
	}

	@Override
	public void close() {
		ConfigManager.save(ModConfig.get());
		this.client.setScreen(this.parentScreen);
	}
}
