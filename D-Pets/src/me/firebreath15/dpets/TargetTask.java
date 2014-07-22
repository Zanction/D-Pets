package me.firebreath15.dpets;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.scheduler.BukkitRunnable;

public class TargetTask extends BukkitRunnable
{
  DPets plugin;
  Entity ent;
  Player p;

  TargetTask(DPets c, Entity ee, Player owner)
  {
    this.plugin = c;
    this.ent = ee;
    this.p = owner;
  }

  public void run() {
    if (this.ent.hasMetadata("Pets_Following"))
    {
      if (!this.ent.getWorld().getName().equalsIgnoreCase(this.p.getWorld().getName())) {
        this.ent.teleport(this.p);
      }

      if ((this.ent instanceof Wolf)) {
        Wolf w = (Wolf)this.ent;
        if (this.p.getLocation().distance(this.ent.getLocation()) > 4.0D)
          w.setTarget(this.p);
        else {
          w.setTarget(null);
        }
      }

      if ((this.ent instanceof Ocelot)) {
        Ocelot o = (Ocelot)this.ent;
        if (this.p.getLocation().distance(this.ent.getLocation()) > 4.0D)
          o.setTarget(this.p);
        else {
          o.setTarget(null);
        }
      }

      if ((this.ent instanceof Creeper)) {
        Creeper cr = (Creeper)this.ent;
        if (this.p.getLocation().distance(this.ent.getLocation()) > 4.0D)
          cr.setTarget(this.p);
        else {
          cr.setTarget(null);
        }
      }

      if ((this.ent instanceof IronGolem)) {
        IronGolem ig = (IronGolem)this.ent;
        if (this.p.getLocation().distance(this.ent.getLocation()) > 4.0D)
          ig.setTarget(this.p);
        else
          ig.setTarget(null);
      }
    }
    else {
      cancel();
    }
  }
}