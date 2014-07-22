package me.firebreath15.dpets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class SpawnListener
  implements Listener
{
  DPets plugin;

  SpawnListener(DPets c)
  {
    this.plugin = c;
  }

  @EventHandler
  public void onSpawnPet(PlayerInteractEvent e) {
    if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
      (e.getPlayer().getItemInHand().getType() == Material.NAME_TAG)) {
      ItemStack tag = e.getPlayer().getItemInHand();
      if ((tag.hasItemMeta()) && 
        (tag.getItemMeta().getDisplayName().equalsIgnoreCase("§aSpawn Pet")))
        if (this.plugin.getWorldGuard() != null) {
          RegionManager rm = this.plugin.getWorldGuard().getRegionManager(e.getPlayer().getWorld());
          if (rm != null) {
            ApplicableRegionSet set = rm.getApplicableRegions(e.getPlayer().getLocation());
            if (set.size() > 0)
              e.getPlayer().sendMessage("§cYou cannot spawn your pet here!");
            else
              spawnPet(e.getPlayer());
          }
          else {
            spawnPet(e.getPlayer());
          }
        }
        else {
          spawnPet(e.getPlayer());
        }
    }
  }

  private void spawnPet(Player p)
  {
    List<String> lore = p.getItemInHand().getItemMeta().getLore();

    for (String lor : lore) {
      if (lor.equalsIgnoreCase("creeper")) {
        if (p.getWorld().getAllowMonsters()) {
          Entity ent = p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
          Creeper c = (Creeper)ent;
          c.setCustomName("§a" + p.getName() + "'s Creeper");
          c.setCustomNameVisible(true);
          c.setRemoveWhenFarAway(false);

          if (this.plugin.getConfig().contains(p.getName().toLowerCase() + ".pets")) {
            List<String> pets = this.plugin.getConfig().getStringList(p.getName().toLowerCase() + ".pets");
            pets.add(c.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(c.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          } else {
            List<String> pets = new ArrayList<String>();
            pets.add(c.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(c.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          }

          p.sendMessage(ChatColor.GREEN + "Pet Spawned!");
          p.getInventory().remove(p.getItemInHand());
        } else {
          p.sendMessage("§cThis world blocks mob spawning!");
        }
      }

      if (lor.equalsIgnoreCase("wolf")) {
        if (p.getWorld().getAllowAnimals()) {
          Entity ent = p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
          Wolf w = (Wolf)ent;
          w.setTamed(true);
          w.setCustomName("§a" + p.getName() + "'s Wolf");
          w.setCustomNameVisible(true);
          w.setRemoveWhenFarAway(false);
          w.setMaxHealth(500.0D);
          w.setAdult();
          w.setOwner(p);

          if (this.plugin.getConfig().contains(p.getName().toLowerCase() + ".pets")) {
            List<String> pets = this.plugin.getConfig().getStringList(p.getName().toLowerCase() + ".pets");
            pets.add(w.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(w.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          } else {
            List<String> pets = new ArrayList<String>();
            pets.add(w.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(w.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          }

          p.sendMessage(ChatColor.GREEN + "Pet Spawned!");
          p.getInventory().remove(p.getItemInHand());
        } else {
          p.sendMessage("§cThis world blocks mob spawning!");
        }
      }

      if (lor.equalsIgnoreCase("cat")) {
        if (p.getWorld().getAllowAnimals()) {
          Entity ent = p.getWorld().spawnEntity(p.getLocation(), EntityType.OCELOT);
          Ocelot o = (Ocelot)ent;
          o.setTamed(true);
          o.setCustomName("§a" + p.getName() + "'s Cat");
          o.setCustomNameVisible(true);
          o.setRemoveWhenFarAway(false);
          o.setMaxHealth(500.0D);
          o.setAdult();
          o.setOwner(p);
          o.setCatType(Ocelot.Type.RED_CAT);

          if (this.plugin.getConfig().contains(p.getName().toLowerCase() + ".pets")) {
            List<String> pets = this.plugin.getConfig().getStringList(p.getName().toLowerCase() + ".pets");
            pets.add(o.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(o.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          } else {
            List<String> pets = new ArrayList<String>();
            pets.add(o.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(o.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          }

          p.sendMessage(ChatColor.GREEN + "Pet Spawned!");
          p.getInventory().remove(p.getItemInHand());
        } else {
          p.sendMessage("§cThis world blocks mob spawning!");
        }
      }

      if (lor.equalsIgnoreCase("iron_golem"))
        if (p.getWorld().getAllowAnimals()) {
          Entity ent = p.getWorld().spawnEntity(p.getLocation(), EntityType.IRON_GOLEM);
          IronGolem ig = (IronGolem)ent;
          ig.setPlayerCreated(true);
          ig.setCustomName("§a" + p.getName() + "'s Iron Golem");
          ig.setCustomNameVisible(true);
          ig.setRemoveWhenFarAway(false);
          ig.setMaxHealth(500.0D);

          if (this.plugin.getConfig().contains(p.getName().toLowerCase() + ".pets")) {
            List<String> pets = this.plugin.getConfig().getStringList(p.getName().toLowerCase() + ".pets");
            pets.add(ig.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(ig.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          } else {
            List<String> pets = new ArrayList<String>();
            pets.add(ig.getUniqueId().toString());
            this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
            this.plugin.getConfig().set(ig.getUniqueId().toString(), p.getName());
            this.plugin.saveConfig();
          }

          p.sendMessage(ChatColor.GREEN + "Pet Spawned!");
          p.getInventory().remove(p.getItemInHand());
        } else {
          p.sendMessage("§cThis world blocks pet spawning!");
        }
    }
  }
}