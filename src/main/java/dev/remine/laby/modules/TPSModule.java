package dev.remine.laby.modules;

import dev.remine.laby.ServerTPS;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class TPSModule extends SimpleModule {

    @Override
    public ModuleCategory getCategory()
    {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.CLOCK);
    }

    @Override
    public double getHeight() {
        return 100;
    }

    @Override
    public double getWidth() {
        return 20;
    }

    @Override
    public void loadSettings() {

    }

    @Override
    public String getSettingName() {
        return "Server TPS";
    }

    @Override
    public String getControlName()
    {
        return "Server TPS";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getSortingId() {
        return 18;
    }

    @Override
    public String getDisplayName() {
        return "Server TPS";
    }

    @Override
    public String getDisplayValue() {
        if (ServerTPS.calculateServerTPS() > 20)
            return String.valueOf(20);
        return String.format("%.1f", ServerTPS.calculateServerTPS());
    }

    @Override
    public String getDefaultValue() {
        return "?";
    }
}
