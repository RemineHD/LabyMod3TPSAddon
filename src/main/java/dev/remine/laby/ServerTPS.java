package dev.remine.laby;

import com.google.common.collect.EvictingQueue;
import dev.remine.laby.modules.TPSModule;
import net.labymod.api.LabyModAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.network.IncomingPacketEvent;
import net.labymod.api.event.events.network.server.PostLoginServerEvent;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.network.play.server.SUpdateTimePacket;

import java.util.List;

/*
Created by elremineh#6498
Thanks to FliegendeWurst for providing the maths to format the TPS.
 */
public class ServerTPS extends LabyModAddon {
    public static EvictingQueue<Float> serverTPS = EvictingQueue.create(3);
    private static long systemTime = 0;
    private static long serverTime = 0;

    @Override
    public void onEnable() {
        getApi().getEventService().registerListener(this);
        getApi().registerModule(new TPSModule());
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
    }

    @Subscribe
    public void clientConnectToServer(PostLoginServerEvent postLoginServerEvent)
    {
        serverTPS.clear();
        systemTime = 0;
        serverTime = 0;
    }

    @Subscribe
    public void serverTickEvent(IncomingPacketEvent incomingPacketEvent)
    {
        if (incomingPacketEvent.getPacket() instanceof SUpdateTimePacket)
            if (systemTime == 0)
            {
                systemTime = System.currentTimeMillis();
                serverTime = ((SUpdateTimePacket) incomingPacketEvent.getPacket()).getTotalWorldTime();
            } else
            {

                long newSystemTime = System.currentTimeMillis();
                long newServerTime = ((SUpdateTimePacket) incomingPacketEvent.getPacket()).getTotalWorldTime();
                serverTPS.add((((float) (newServerTime - serverTime)) / (((float) (newSystemTime - systemTime)) / 50.0f)) * 20.0f);
                systemTime = newSystemTime;
                serverTime = newServerTime;
            }
    }

    public static float calculateServerTPS() {
        float sum = 0.0f;
        for (Float f : serverTPS) {
            sum += f;
        }
        return sum / (float) serverTPS.size();
    }

}
