package fr.zaral.npcreward.lib;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitlesLib {

    public static void sendTitle(Player player, PacketPlayOutTitle.EnumTitleAction titleType, String text, int fadeInTime, int showTime, int fadeOutTime, ChatColor color)
    {
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");

        if(titleType != PacketPlayOutTitle.EnumTitleAction.TITLE && titleType != PacketPlayOutTitle.EnumTitleAction.SUBTITLE) return;
        PacketPlayOutTitle title = new PacketPlayOutTitle(titleType, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeInTime, showTime ,fadeOutTime);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }
}
