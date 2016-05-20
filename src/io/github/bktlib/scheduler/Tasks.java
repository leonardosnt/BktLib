/*
 *  Copyright (C) 2016 Leonardosc
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

package io.github.bktlib.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Tasks {

  public static Task execute(Runnable callback) {
    return execute(t -> callback.run());
  }

  public static Task execute(Consumer<Task> callback) {
    return new Task(callback);
  }

  public static class Task {
    private BukkitRunnable handle;
    private Consumer<Task> callback;
    private long interval; // In ticks
    private long delay;    // In ticks
    private boolean async;

    public Task(Consumer<Task> callback) {
      this.callback = callback;
    }

    Task async() {
      async = true;
      return this;
    }

    public Task interval(long interval, TimeUnit unit) {
      this.interval = unit.toSeconds(interval) * 20;
      return this;
    }

    public Task intervalTicks(long ticks) {
      this.interval = ticks;
      return this;
    }

    public Task after(long delay, TimeUnit unit) {
      this.delay = unit.toSeconds(delay) * 20;
      return this;
    }

    public Task afterTicks(long ticks) {
      this.delay = ticks;
      return this;
    }

    public Task submit(Class<? extends JavaPlugin> pluginClass) {
      return submit(JavaPlugin.getProvidingPlugin(pluginClass));
    }

    public Task submit(Plugin plugin) {
      if (handle != null) {
        throw new IllegalStateException("Task already initialized.");
      }
      handle = new BukkitRunnable() {
        @Override
        public void run() {
          callback.accept(Task.this);
        }
      };
      if (async) {
        if (interval == 0) {
          handle.runTaskLaterAsynchronously(plugin, delay);
        } else {
          handle.runTaskTimerAsynchronously(plugin, delay, interval);
        }
      } else {
        if (interval == 0) {
          handle.runTaskLater(plugin, delay);
        } else {
          handle.runTaskTimer(plugin, delay, interval);
        }
      }
      return this;
    }

    public void cancel() {
      if (handle == null) {
        throw new IllegalStateException("Task is not initialized yet.");
      }
      handle.cancel();
    }
  }
}
