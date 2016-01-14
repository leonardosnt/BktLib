package io.github.bktlib.ui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.github.bktlib.ui.events.ItemClickedEvent;

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

        if ( !( inv.getHolder() instanceof MenuHolder ) || 
        	 !( e.getWhoClicked() instanceof Player ) ) return;

        MenuHolder holder = (MenuHolder) inv.getHolder();
        Menu menu = holder.getMenu();
        List<MenuItem> items = menu.getItems();

        int clickedSlot = e.getSlot();
        int action = e.getAction().ordinal();

        if ( clickedSlot < 0 || 
        	 clickedSlot > items.size() || 
        	 items.get(clickedSlot) == null ) return;

        e.setCancelled( true );

        if ( action != 1 && action != 3 ) return;
        
        MenuItem item = items.get( e.getSlot() );
        
        item.getOnClicked().ifPresent( consumer ->
        {
            ItemClickedEvent.MouseButton button = action == 1
                    ? ItemClickedEvent.MouseButton.LEFT
                    : ItemClickedEvent.MouseButton.RIGHT;

            ItemClickedEvent event = new ItemClickedEvent( 
            		button,
            		(Player) e.getWhoClicked(),
            		menu,
            		item,
            		clickedSlot
            );
            
            consumer.accept( event );
        });
    }
}