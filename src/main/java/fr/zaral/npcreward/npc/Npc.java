package fr.zaral.npcreward.npc;


import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Zaral on 09/04/2016.
 */
public class Npc {

    @Getter
    public boolean isSpawn = false;
    @Getter
    private String name = null;
    @Getter
    private Location location = null;
    @Getter
    private LivingEntity entity = null;
    @Getter
    private Villager.Profession profession = null;
    @Getter
    private Player target = null;


    public Npc(String name, Location spawnLocation, Villager.Profession profession, Player target) {
        this.name = name;
        this.location = spawnLocation;
        this.profession = profession;
        this.target = target;
    }

    public Npc(String name, Location spawnLocation, Player target) {
        this.name = name;
        this.location = spawnLocation;
        this.target = target;


    }

    public void freezeEntity(Entity en) {
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) en).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        nmsEn.f(compound);
    }

    public void silentEntity(Entity en) {
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) en).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("Silent", (byte) 1);
        nmsEn.f(compound);
    }


    public void spawn(Player player) {
        double dX = location.getX();
        double dY = location.getY();
        double dZ = location.getZ() ;
        Location pL = player.getLocation();
        double yaw = (Math.atan2((dX-pL.getX()),(dZ-pL.getZ()))*(180.0/Math.PI));
        double pitch = Math.asin((dY - pL.getY()) / location.distance(pL));
        if (yaw == 0) yaw = 180;
        else if (yaw == 180) yaw = 0;

        location.setYaw(new Double(yaw).floatValue());
        location.setPitch(new Double(pitch).floatValue());
        if (!isSpawn) {
            entity = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

            entity.setFireTicks(0);
            ((Villager) entity).setAdult();
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Short.MAX_VALUE - 1, 15, true));
            if (profession != null) {
                try {
                    if (profession != null) {
                        ((Villager) entity).setProfession(profession);
                    } else {
                        ((Villager) entity).setProfession(Villager.Profession.FARMER);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
            entity.setCustomNameVisible(true);
            entity.teleport(location);
            freezeEntity(entity);
            silentEntity(entity);
           // CraftEntity.getEntity((CraftServer)NpcReward.getInstance().getServer(), (net.minecraft.server.v1_8_R3.Entity) entity).getHandle().set;

            isSpawn = true;

        }
    }

    public void teleport() {
        entity.teleport(location);

    }
    public void setEntity(LivingEntity entity) {

        this.entity = entity;
        this.name = entity.getName();
    }

    public void delete() {
        if (isSpawn) {
            entity.remove();
            isSpawn = false;
        }
    }

    public void update(Player player) {
        if (!isSpawn)
            spawn(player);
        else {
            entity.teleport(location);
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        }
    }

}
