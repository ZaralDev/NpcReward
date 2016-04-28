package fr.zaral.npcreward.npc;


import fr.zaral.npcreward.NpcReward;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
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

    private NpcManager npcManager;


    public Npc(NpcManager npcManager, String name, Location spawnLocation, Villager.Profession profession) {
        this.name = name;
        this.location = spawnLocation;
        this.profession = profession;
        this.npcManager = npcManager;
        npcManager.getNpcList().add(this);
    }

    public Npc(NpcManager npcManager, String name, Location spawnLocation) {
        this.name = name;
        this.location = spawnLocation;
        this.npcManager = npcManager;
        npcManager.getNpcList().add(this);


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


    public void spawn() {
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
            npcManager.getNpcList().remove(this);
        }
    }

    public void update() {
        if (!isSpawn)
            spawn();
        else {
            entity.teleport(location);
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        }
    }

}
