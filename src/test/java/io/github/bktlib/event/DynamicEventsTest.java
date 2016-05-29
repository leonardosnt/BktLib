package io.github.bktlib.event;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DynamicEventsTest {

  public static void testEntity(World w, Location loc, DynamicEvents de) {
    final String id = "test";
    final Entity ent = w.spawnEntity(loc, EntityType.VILLAGER);

    de.registerForEntity(ent, id, EntityDamageByEntityEvent.class, e -> {
      System.out.println(e.getEntity() + " : " + e.getDamager());
      de.unregister(id);
    });
  }

  public static void testPlayer(Player p, DynamicEvents de) {
    final String id = "test";
    de.registerForPlayer(p, id, AsyncPlayerChatEvent.class, e -> {
      e.getPlayer().sendMessage("Você digitou " + e.getMessage());
      e.setCancelled(true);
      de.unregister(id);
    });
  }
}
