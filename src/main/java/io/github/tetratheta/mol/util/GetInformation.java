package io.github.tetratheta.mol.util;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides information about server and this library
 */
@SuppressWarnings("unused")
public final class GetInformation {
  /**
   * Returns current Bukkit API version. This would be something like {@code v1_19_R2}.
   *
   * @return Current Bukkit API version
   */
  public static @NotNull String serverVersion() {
    String packageName = Bukkit.getServer().getClass().getPackage().getName();
    return packageName.substring(packageName.lastIndexOf('.') + 1);
  }

  /**
   * Returns base package name of this library. If you don't relocate this library, it would return {@code com.github.tetratheta.mol}.
   *
   * @return Package name of this library
   */
  public static @NotNull String libraryPackage() {
    // This should return 'com.github.tetratheta.mol'
    String currentPackageName = GetInformation.class.getName();
    List<String> list = new ArrayList<>(Arrays.asList(currentPackageName.split("\\.")));
    return String.join(".", list.subList(0, 4));
  }
}
