package io.github.bktlib.inventory;

import org.bukkit.inventory.ItemStack;

public class ItemsTest {

  public static void test1(ItemStack item) {
    Items.Meta.removeKey(item, "testArr");
  }

  public static void test2(ItemStack item) {
    Items.Meta.setString(item, "testStr", "Hello World!!");
  }

  public static void test3(ItemStack item) {
    Items.Meta.setIntArray(item, "testArr", new int[] {1, 2, 3, 4, 5, 6});
  }
}
