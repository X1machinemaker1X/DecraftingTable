package me.x1machinemaker1x.decraftingtable.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class DesmelterRecipe {
	
	public static boolean createRecipe(Plugin pl) {
		ItemStack table = CustomSkulls.getDesmelter();
		ShapedRecipe r = new ShapedRecipe(new NamespacedKey(pl, "Desmelter"), table);
		r.shape("123","456","789");
		char[] chars = new char[] {'1','2','3','4','5','6','7','8','9'};
		for (int i = 0; i < 9; i++) {
			String itemName;
			if (i <= 2) {
				itemName = pl.getConfig().getString("CraftingRecipes.Desmelter.Line1").split("\\|")[i];
			}
			else if (i <=5) {
				itemName = pl.getConfig().getString("CraftingRecipes.Desmelter.Line2").split("\\|")[i-3];
			}
			else {
				itemName = pl.getConfig().getString("CraftingRecipes.Desmelter.Line3").split("\\|")[i-6];
			}
			
			if (itemName.equalsIgnoreCase("null")) {
				r.setIngredient((char) (i+1), Material.AIR);
				continue;
			}
			
			if (itemName.contains(":")) {
				if (Material.matchMaterial(itemName.split(":")[0]) == null) {
					pl.getLogger().info("The configuration file is not set up correctly! Disabling plugin!");
					pl.getPluginLoader().disablePlugin(pl);
					return false;
				}
				else {
					ItemStack item = new ItemStack(Material.matchMaterial(itemName.split(":")[0]));
					try {
						item.setDurability(Short.parseShort(itemName.split(":")[1]));
					} catch (NumberFormatException e) {
						pl.getLogger().info("The configuration file is not set up correctly! Disabling plugin!");
						pl.getPluginLoader().disablePlugin(pl);
						return false;
					}
					r.setIngredient(chars[i], item.getData());
				}
			}
			else {
				if (Material.matchMaterial(itemName) == null) {
					pl.getLogger().info("The configuration file is not set up correctly! Disabling plugin!");
					pl.getPluginLoader().disablePlugin(pl);
					return false;
				}
				else {
					r.setIngredient(chars[i], Material.matchMaterial(itemName));
				}
			}
		}
		
		pl.getServer().addRecipe(r);
		return true;
	}
}