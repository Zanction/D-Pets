package me.firebreath15.dpets;

import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HandleEvents
  implements Listener
{
  DPets plugin;

  HandleEvents(DPets c)
  {
    this.plugin = c;
  }

  @EventHandler
  public void handleFollowing(EntityTargetLivingEntityEvent e){
    Entity ent = e.getEntity();
    if(this.plugin.getConfig().contains(ent.getUniqueId().toString()) && e.getTarget() instanceof Player){
      Player t = (Player)e.getTarget();
      if (!this.plugin.getConfig().getString(ent.getUniqueId().toString()).equalsIgnoreCase(t.getName()))
        e.setTarget(null);
    }
  }

  @EventHandler
  public void onPetHurtOwner(EntityDamageByEntityEvent e){
    if(this.plugin.getConfig().contains(e.getDamager().getUniqueId().toString()) && e.getEntity() instanceof Player){
      Player p = (Player)e.getEntity();
      if(this.plugin.getConfig().getString(e.getDamager().getUniqueId().toString()).equalsIgnoreCase(p.getName())){
        e.setCancelled(true);
        e.getDamager().removeMetadata("Pets_Following", this.plugin);
      }
    }
  }

  @EventHandler
  public void handleFollowingII(EntityExplodeEvent e){
    if((e.getEntity() instanceof Creeper)){
      Entity ent = e.getEntity();
      if (this.plugin.getConfig().contains(ent.getUniqueId().toString()))
        e.setCancelled(true);
    }
  }

  @EventHandler
  public void onLogin(PlayerJoinEvent e){
    if(this.plugin.getConfig().contains(e.getPlayer().getName().toLowerCase() + ".newpet")){
      if(!this.plugin.getConfig().getStringList(e.getPlayer().getName().toLowerCase() + ".newpet").isEmpty()){
        e.getPlayer().sendMessage("§e>>§c§lREDEEM YOUR PET WITH §a§l/PET REDEEM§c§l!§e<<");
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);
      }else{
        this.plugin.getConfig().set(e.getPlayer().getName().toLowerCase() + ".newpet", null);
        this.plugin.saveConfig();
      }
    }
  }

  @EventHandler
  public void onPlayerClickPet(PlayerInteractEntityEvent e){
    if(this.plugin.getConfig().contains(e.getRightClicked().getUniqueId().toString())){
      String owner = this.plugin.getConfig().getString(e.getRightClicked().getUniqueId().toString());
      if(e.getPlayer().getName().toLowerCase().equalsIgnoreCase(owner)){
        if(e.getPlayer().isSneaking()){
          e.setCancelled(true);
          PetGUI.openGUI(e.getPlayer(), e.getRightClicked());
        }
      }else{
    	  e.getPlayer().sendMessage("§7[§6§lD-PETS§7] §4That is not your pet!");
      }
    }
  }

  @EventHandler
  public void handlePetDamages(EntityDamageEvent e){
    Entity ent = e.getEntity();
    if(this.plugin.getConfig().contains(ent.getUniqueId().toString())){
      e.setCancelled(true);
    }
  }
}