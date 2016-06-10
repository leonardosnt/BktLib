package io.github.bktlib.text;

import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public final class Text {

  private TextComponent component;

  public static Text of(String text) {
    return new Text(Strings.nullToEmpty(text));
  }

  private Text(String text) {
    component = new TextComponent(text);
  }

  public Text color(org.bukkit.ChatColor color) {
    if (color != null) {
      color(ChatColor.valueOf(color.toString()));
    }
    return this;
  }

  public Text color(ChatColor color) {
    component.setColor(color);
    return this;
  }

  public Text hover(HoverEvent.Action action, Text text) {
    return hover(action, new BaseComponent[] { text.toTextComponent() });
  }

  public Text hover(HoverEvent.Action action, String value) {
    return hover(action, new ComponentBuilder(value).create());
  }

  public Text hover(HoverEvent.Action action, BaseComponent[] value) {
    component.setHoverEvent(new HoverEvent(action, value));
    return this;
  }

  public Text bold() {
    return bold(true);
  }

  public Text italic() {
    return italic(true);
  }

  public Text obfuscated() {
    return obfuscated(true);
  }

  public Text strikethrough() {
    return strikethrough(true);
  }

  public Text obfuscated(boolean b) {
    component.setObfuscated(b);
    return this;
  }

  public Text italic(boolean b) {
    component.setItalic(b);
    return this;
  }

  public Text strikethrough(boolean b) {
    component.setStrikethrough(b);
    return this;
  }

  public Text bold(boolean bold) {
    component.setBold(bold);
    return this;
  }

  private BaseComponent toTextComponent() {
    return component;
  }

}
