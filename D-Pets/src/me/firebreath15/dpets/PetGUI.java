package me.firebreath15.dpets;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PetGUI implements Listener{
	
  DPets plugin;
  static Entity ent;
  public static Inventory gui = Bukkit.createInventory(null, 9, "Pet Settings");
  static ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
  static ItemStack togsta = new ItemStack(Material.LEVER);
  static ItemStack togfol = new ItemStack(Material.MINECART);
  static ItemStack togtyp = new ItemStack(Material.BOOK);
  static ItemStack togage = new ItemStack(Material.MILK_BUCKET);

  static {
    ItemMeta im = delete.getItemMeta();
    im.setDisplayName("§cDELETE PET");
    delete.setItemMeta(im);

    ItemMeta im2 = togsta.getItemMeta();
    im2.setDisplayName("§aToggle §eStay Put");
    togsta.setItemMeta(im2);

    ItemMeta im3 = togfol.getItemMeta();
    im3.setDisplayName("§aToggle §eFollowing");
    togfol.setItemMeta(im3);

    ItemMeta im4 = togtyp.getItemMeta();
    im4.setDisplayName("§aToggle §ePet Type");
    togtyp.setItemMeta(im4);
    
    ItemMeta im5 = togage.getItemMeta();
    im5.setDisplayName("§aToggle §eAge");
    togage.setItemMeta(im5);

    gui.setItem(8, delete);
    gui.setItem(0, togsta);
    gui.setItem(1, togfol);
    gui.setItem(2, togtyp);
    gui.setItem(3, togage);
  }

  PetGUI(DPets c){
    plugin = c;
  }

  public static void openGUI(Player p, Entity e){
    p.openInventory(gui);
    ent = e;
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    ItemStack c = e.getCurrentItem();
    if(e.getInventory().getName().equalsIgnoreCase(gui.getName()))
      if(c.getType() == Material.REDSTONE_BLOCK){
        e.setCancelled(true);
        p.closeInventory();

        this.plugin.getConfig().set(ent.getUniqueId().toString(), null);
        List<String> pets = this.plugin.getConfig().getStringList(p.getName().toLowerCase() + ".pets");
        pets.remove(ent.getUniqueId().toString());
        this.plugin.getConfig().set(p.getName().toLowerCase() + ".pets", pets);
        this.plugin.saveConfig();

        ent.remove();
        p.sendMessage("§7[§6§lD-PETS§7] §aPet Deleted! §7§oIt was sad to see you leave...");
      }else if (c.getType() == Material.LEVER) {
        e.setCancelled(true);
        p.closeInventory();

        if ((ent instanceof Wolf)) {
          Wolf w = (Wolf)ent;
          if (w.isSitting()) {
            w.setSitting(false);
            p.sendMessage("§7[§6§lD-PETS§7] §aPet is no longer idle");
          } else {
            w.setSitting(true);
            p.sendMessage("§7[§6§lD-PETS§7] §aPet can no longer move");
          }
        }

        if ((ent instanceof Creeper)) {
          Creeper cr = (Creeper)ent;
          if (cr.hasPotionEffect(PotionEffectType.SLOW)) {
            cr.removePotionEffect(PotionEffectType.SLOW);
            p.sendMessage("§7[§6§lD-PETS§7] §aPet is no longer idle");
          } else {
            cr.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72000, 5));
            p.sendMessage("§7[§6§lD-PETS§7] §aPet can no longer move");
          }
        }

        if ((ent instanceof IronGolem)) {
          IronGolem ig = (IronGolem)ent;
          if (ig.hasPotionEffect(PotionEffectType.SLOW)) {
            ig.removePotionEffect(PotionEffectType.SLOW);
            p.sendMessage("§7[§6§lD-PETS§7] §aPet is no longer idle");
          } else {
            ig.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72000, 5));
            p.sendMessage("§7[§6§lD-PETS§7] §aPet can no longer move");
          }
        }

        if(ent instanceof Zombie){
            Zombie z = (Zombie)ent;
            if(z.hasPotionEffect(PotionEffectType.SLOW)){
              z.removePotionEffect(PotionEffectType.SLOW);
              p.sendMessage("§7[§6§lD-PETS§7] §aPet is no longer idle");
            }else{
              z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72000, 5));
              p.sendMessage("§7[§6§lD-PETS§7] §aPet can no longer move");
            }
        }
        
        if ((ent instanceof Ocelot)) {
          Ocelot o = (Ocelot)ent;
          if (o.isSitting()) {
            o.setSitting(false);
            p.sendMessage("§7[§6§lD-PETS§7] §aPet is no longer idle");
          } else {
            o.setSitting(true);
            p.sendMessage("§7[§6§lD-PETS§7] §aPet can no longer move");
          }
        }
      }else if (c.getType() == Material.MINECART) {
        e.setCancelled(true);
        p.closeInventory();

        if (ent.hasMetadata("Pets_Following")) {
          ent.removeMetadata("Pets_Following", this.plugin);
          p.sendMessage("§7[§6§lD-PETS§7] §aPet is no longer following you");
        } else {
          ent.setMetadata("Pets_Following", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
          new TargetTask(this.plugin, ent, p).runTaskTimer(this.plugin, 20L, 10L);
          p.sendMessage("§7[§6§lD-PETS§7] §aPet is now following you");
        }
      }else{
    	  if(c.getType() == Material.BOOK){
    		  e.setCancelled(true);
    		  p.closeInventory();

    		  if(ent instanceof Ocelot){
    			  Ocelot o = (Ocelot)ent;

    			  if(o.getCatType() == Ocelot.Type.BLACK_CAT){
    				  o.setCatType(Ocelot.Type.WILD_OCELOT);
    			  }

    			  if (o.getCatType() == Ocelot.Type.SIAMESE_CAT) {
    				  o.setCatType(Ocelot.Type.BLACK_CAT);
    			  }

    			  if (o.getCatType() == Ocelot.Type.RED_CAT) {
    				  o.setCatType(Ocelot.Type.SIAMESE_CAT);
    			  }

    			  if (o.getCatType() == Ocelot.Type.WILD_OCELOT) {
    				  o.setCatType(Ocelot.Type.RED_CAT);
    			  }
        }else{
        	if(ent instanceof Wolf || ent instanceof IronGolem || ent instanceof Creeper || ent instanceof Zombie){
        		p.sendMessage("§7[§6§lD-PETS§7] §cThat option has no effect for this type of pet!");
        	}
        }
      }else{
    	  if(c.getType()==Material.MILK_BUCKET){
    		  e.setCancelled(true);
    		  p.closeInventory();
    		  
    		  if(ent instanceof Wolf){
    			  Wolf w = (Wolf)ent;
    			  if(w.isAdult()){
    				  w.setBaby();
    			  }else{
    				  w.setAdult();
    			  }
    		  }else{
    			  if(ent instanceof Ocelot){
        			  Ocelot o = (Ocelot)ent;
        			  if(o.isAdult()){
        				  o.setBaby();
        			  }else{
        				  o.setAdult();
        			  }
        		  }else{
        			  if(ent instanceof Zombie){
            			  Zombie z = (Zombie)ent;
            			  if(z.isBaby()){
            				  z.setBaby(false);
            			  }else{
            				  z.setBaby(true);
            			  }
            		  }else{
            			  if(ent instanceof Creeper || ent instanceof IronGolem){
            				  p.sendMessage("§7[§6§lD-PETS§7] §cThat option has no effect for this type of pet!");
            			  }
            		  }
        		  }
    		  }
    	  }
      }
  }
  }
}