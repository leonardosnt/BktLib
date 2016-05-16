package io.github.bktlib.inventory;

import io.github.bktlib.nbt.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

public class ItemsTest {

  public static void test1(ItemStack item) {
    NBTTagCompound cmp = Items.getNbtTag(item);
    cmp.removeTag("testArr");
    Items.setNbtTag(item, cmp);
  }

  public static void test2(ItemStack item) {
    NBTTagCompound cmp = Items.getNbtTag(item);
    cmp.setString("testStr", "Hello World!!");
    Items.setNbtTag(item, cmp);
  }

  public static void test3(ItemStack item) {
    NBTTagCompound tag = Items.getNbtTag(item);
    tag.setIntArray("testArr", new int[] {1, 2, 3, 4, 5, 6});
    Items.setNbtTag(item, tag);
  }

  public static void test4(ItemStack item) {
    Items.modifyTag(item, tag -> {
      tag.getCompoundTag("display").setString("Name", "§3Nome legal");
    });
  }
}
