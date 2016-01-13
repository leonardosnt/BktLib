package io.github.bktlib.ui;

import io.github.bktlib.ui.events.ItemClickedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * Classe interna para o gerenciamento
 * de eventos relacionados aos menus.
 * 
 * @author Leonardosc
 */
class MenuListener implements Listener
{
    @EventHandler
    void onInventoryClick( InventoryClickEvent e )
    {
        Inventory inv = e.getInventory();

        if ( !(inv.getHolder() instanceof MenuHolder) ) return;

        MenuHolder holder = (MenuHolder) inv.getHolder();
        Menu menu = holder.getMenu();
        List<MenuItem> items = menu.getItems();

        int clickedSlot = e.getSlot();
        int action = e.getAction().ordinal();

        boolean invalid = clickedSlot < 0;
        invalid |= clickedSlot > items.size();
        invalid |= items.get(clickedSlot) == null;

        if ( invalid ) return;

        e.setCancelled( true );

        if ( action != 1 && action != 3 ) return;

        items.get( e.getSlot() ).getOnClicked().ifPresent( consumer ->
        {
            ItemClickedEvent.MouseButton button = action == 1
                    ? ItemClickedEvent.MouseButton.LEFT
                    : ItemClickedEvent.MouseButton.RIGHT;

            consumer.accept( new ItemClickedEvent( button ) );
        });
    }
}