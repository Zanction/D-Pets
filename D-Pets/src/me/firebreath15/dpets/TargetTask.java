package me.firebreath15.dpets;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

public class TargetTask extends BukkitRunnable{
  DPets plugin;
  Entity ent;
  Player p;

  TargetTask(DPets c, Entity ee, Player owner){
    plugin = c;
    ent = ee;
    p = owner;
  }

  public void run(){
    if(ent.hasMetadata("Pets_Following")){
      if(!ent.getWorld().getName().equalsIgnoreCase(p.getWorld().getName())){
        ent.teleport(p);
      }

      if(ent instanceof Wolf){
        Wolf w = (Wolf)ent;
        if(p.getLocation().distance(ent.getLocation()) > 4){
          w.setTarget(p);
        }else{
          w.setTarget(null);
        }
      }

      if(ent instanceof Ocelot){
        Ocelot o = (Ocelot)ent;
        if(p.getLocation().distance(ent.getLocation()) > 4){
          o.setTarget(p);
        }else{
          o.setTarget(null);
        }
      }

      if(ent instanceof Creeper){
        Creeper cr = (Creeper)ent;
        if(p.getLocation().distance(ent.getLocation()) > 4){
          cr.setTarget(p);
        }else{
          cr.setTarget(null);
        }
      }
      
      if(ent instanceof Zombie){
          Zombie z = (Zombie)ent;
          if(p.getLocation().distance(ent.getLocation()) > 4){
            z.setTarget(p);
          }else{
            z.setTarget(null);
          }
      }

      if(ent instanceof IronGolem){
        IronGolem ig = (IronGolem)ent;
        if(p.getLocation().distance(ent.getLocation()) > 4){
          ig.setTarget(p);
        }else{
          ig.setTarget(null);
        }
      }
      
    }else{
    	this.cancel();
    }
  }
}