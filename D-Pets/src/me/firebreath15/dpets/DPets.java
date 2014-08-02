package me.firebreath15.dpets;

import java.util.ArrayList;
import java.util.List;
//
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class DPets extends JavaPlugin
{
  public void onEnable()
  {
    reloadConfig();
    getServer().getPluginManager().registerEvents(new HandleEvents(this), this);
    getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
    getServer().getPluginManager().registerEvents(new PetGUI(this), this);
  }

  public WorldGuardPlugin getWorldGuard() {
    Plugin plug = getServer().getPluginManager().getPlugin("WorldGuard");
    if (plug == null) {
      return null;
    }
    return (WorldGuardPlugin)plug;
  }

  @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    if (cmd.getName().equalsIgnoreCase("pet") || cmd.getName().equalsIgnoreCase("dpets")) {
      if ((sender instanceof Player)) {
        Player p = (Player)sender;
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("redeem")) {
            if (getConfig().contains(p.getName().toLowerCase() + ".newpet")) {
              List<String> newpets = getConfig().getStringList(p.getName().toLowerCase() + ".newpet");
              try{
                for (String type : newpets) {
                  newpets.remove(type);

                  if (newpets.size() != 0) {
                    getConfig().set(p.getName().toLowerCase() + ".newpet", newpets);
                    saveConfig();
                    p.sendMessage("§e>>§c§lYOU STILL HAVE PETS TO REDEEM§e<<");
                  } else {
                    getConfig().set(p.getName().toLowerCase() + ".newpet", null);
                    saveConfig();
                  }

                  ItemStack tag = new ItemStack(Material.NAME_TAG);
                  ItemMeta im = tag.getItemMeta();
                  im.setDisplayName("§aSpawn Pet");
                  List lore = new ArrayList();

                  if(type.equalsIgnoreCase("creeper")){
                    lore.clear();
                    lore.add("creeper");
                  }
                  if(type.equalsIgnoreCase("wolf")){
                    lore.clear();
                    lore.add("wolf");
                  }
                  if(type.equalsIgnoreCase("cat")){
                    lore.clear();
                    lore.add("cat");
                  }
                  if(type.equalsIgnoreCase("iron_golem")){
                    lore.clear();
                    lore.add("iron_golem");
                  }
                  if(type.equalsIgnoreCase("zombie")){
                      lore.clear();
                      lore.add("zombie");
                  }

                  im.setLore(lore);
                  tag.setItemMeta(im);
                  p.getInventory().addItem(tag);
                  p.sendMessage("§e§l>>§a§lPET REDEEMED§e§l<<");
                  p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
                }
              }catch(Exception e){}
            }else{
            	p.sendMessage("§7[§6§lD-PETS§7] §4You do not have any pets to redeem"); 
            }
          }else{
            p.sendMessage("§7[§6§lD-PETS§7] §cInvalid command or usage!");
          }
        }else if (args.length == 2){
          if(!args[0].equalsIgnoreCase("redeem")){
            if(p.hasPermission("dpets.give")){
              if(args[1].equalsIgnoreCase("wolf") || args[1].equalsIgnoreCase("cat") || args[1].equalsIgnoreCase("iron_golem") || args[1].equalsIgnoreCase("creeper") || args[1].equalsIgnoreCase("zombie")){
                if(getConfig().contains(args[0].toLowerCase() + ".newpet")){
                  List<String> newpets = getConfig().getStringList(args[0].toLowerCase() + ".newpet");
                  newpets.add(args[1].toLowerCase());
                  getConfig().set(args[0].toLowerCase() + ".newpet", newpets);
                  saveConfig();
                }else{
                  List<String> newpets = new ArrayList<String>();
                  newpets.add(args[1].toLowerCase());
                  getConfig().set(args[0].toLowerCase() + ".newpet", newpets);
                  saveConfig();
                }

                if(Bukkit.getPlayer(args[0]) != null){
                  Bukkit.getPlayer(args[0]).sendMessage("§e>>§c§lREDEEM YOUR PET WITH §a§l/PET REDEEM§c§l!§e<<");
                  Bukkit.getPlayer(args[0]).playSound(Bukkit.getPlayer(args[0]).getLocation(), Sound.LEVEL_UP, 5.0F, 1.0F);
                }else{
                  getServer().dispatchCommand(getServer().getConsoleSender(), "mail send " + args[0] + " Your pet is waiting to be redeemed! Use /dpets redeem!");
                }

                p.sendMessage("§7[§6§lD-PETS§7] §aPet was gifted successfully!");
              }else{
                p.sendMessage("§7[§6§lD-PETS§7] §cInvalid pet type! Use §eCat, Wolf, Zombie, Creeper, §cor §eIron_Golem");
              }
            }else p.sendMessage("§7[§6§lD-PETS§7] §cYou don't have permission!");
          }else p.sendMessage("§c/dpets redeem");
        }else p.sendMessage("§c/dpets <redeem|(username)> [type]");
      }else if(args[1].equalsIgnoreCase("wolf") || args[1].equalsIgnoreCase("cat") || args[1].equalsIgnoreCase("iron_golem") || args[1].equalsIgnoreCase("creeper") || args[1].equalsIgnoreCase("zombie")) {
        if(getConfig().contains(args[0].toLowerCase() + ".newpet")){
          List<String> newpets = getConfig().getStringList(args[0].toLowerCase() + ".newpet");
          newpets.add(args[1].toLowerCase());
          getConfig().set(args[0].toLowerCase() + ".newpet", newpets);
          saveConfig();
        }else{
          List<String> newpets = new ArrayList<String>();
          newpets.add(args[1].toLowerCase());
          getConfig().set(args[0].toLowerCase() + ".newpet", newpets);
          saveConfig();
        }

        if(Bukkit.getPlayer(args[0]) != null){
          Bukkit.getPlayer(args[0]).sendMessage("§e>>§c§lREDEEM YOUR PET WITH §a§l/PET REDEEM§c§l!§e<<");
          Bukkit.getPlayer(args[0]).playSound(Bukkit.getPlayer(args[0]).getLocation(), Sound.LEVEL_UP, 5.0F, 1.0F);
        }else{
          getServer().dispatchCommand(getServer().getConsoleSender(), "mail send " + args[0] + " Your pet is waiting to be redeemed! Use /dpets redeem!");
        }

        sender.sendMessage("§7[§6§lD-PETS§7] §aPet was gifted!");
      }else{
        sender.sendMessage("§7[§6§lD-PETS§7] §cInvalid pet type! Use §eCat, Wolf, Zombie, Creeper, §cor §eIron_Golem");
      }

      return true;
    }
    return false;
  }
}