package ru.xPaw.NoWeather;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class EventListener implements Listener
{
	private Main plugin;
	
	public EventListener( Main plugin )
	{
		this.plugin = plugin;
		
		plugin.getServer( ).getPluginManager( ).registerEvents( this, plugin );
	}
	
	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void onWeatherChange( WeatherChangeEvent event )
	{
		if( event.toWeatherState( ) && plugin.isNodeDisabled( "disable-weather", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void onThunderChange( ThunderChangeEvent event )
	{
		if( event.toThunderState( ) && plugin.isNodeDisabled( "disable-thunder", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void onLightningStrike( LightningStrikeEvent event )
	{
		if( plugin.isNodeDisabled( "disable-lightning", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void onBlockForm( BlockFormEvent event )
	{
		Material mat     = event.getNewState( ).getType( );
		String worldName = event.getBlock( ).getWorld( ).getName( );
		
		if( ( mat == Material.ICE  && plugin.isNodeDisabled( "disable-ice-accumulation", worldName ) )
		||  ( mat == Material.SNOW && plugin.isNodeDisabled( "disable-snow-accumulation", worldName ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	@EventHandler( priority = EventPriority.MONITOR, ignoreCancelled = true )
	public void onWorldLoad( WorldLoadEvent event )
	{
		plugin.worldLoaded( event.getWorld( ) );
	}
}
