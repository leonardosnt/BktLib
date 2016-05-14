package io.github.bktlib.misc;

import org.bukkit.Bukkit;

public class BukkitUtil {

  private static String cachedImplVersion;

  /**
   * Pega a versao da implementação do bukkit.
   * Exemplo: {@code v1_8_R3}
   *
   * @return versão do craftbukkit
   */
  public static String getImplVersion() {
    if (cachedImplVersion == null) {
      final String craftServerPkg = Bukkit.getServer().getClass().getPackage().getName();
      cachedImplVersion = craftServerPkg.substring(craftServerPkg.lastIndexOf('.') + 1);
    }
    return cachedImplVersion;
  }

}
