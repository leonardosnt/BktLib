package io.github.bktlib.event;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DynamicEventsTest {

  public static void testEntity(World w, Location loc, DynamicEvents de) {
    final String id = "testEnt";
    final Entity ent = w.spawnEntity(loc, EntityType.VILLAGER);

    de.registerForEntity(ent, id, EntityDamageByEntityEvent.class, e -> {
      System.out.println(e.getEntity() + " : " + e.getDamager());
      de.unregister(id);
    });
  }
}
