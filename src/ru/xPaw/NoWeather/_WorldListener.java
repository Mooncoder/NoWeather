package ru.xPaw.NoWeather;

import org.bukkit.World;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

public class _WorldListener extends WorldListener
{
	private Main plugin;
	
	public _WorldListener( Main plugin )
	{
		this.plugin = plugin;
	}
	
	public void onWorldLoad( WorldLoadEvent event )
	{
		WorldLoaded( event.getWorld( ) );
	}
	
	public void WorldLoaded( World world )
	{
		String worldName = world.getName();
		
		if( !plugin.config.contains( worldName ) )
		{
			plugin.log.info( "[NoWeather] " + worldName + " - no configuration, generating defaults." );
		}
		
		Boolean disWeather   = plugin.isNodeDisabled( "disable-weather", worldName );
		Boolean disThunder   = plugin.isNodeDisabled( "disable-thunder", worldName );
		Boolean disLightning = plugin.isNodeDisabled( "disable-lightning", worldName );
		Boolean disSnow      = plugin.isNodeDisabled( "disable-snow-accumulation", worldName );
		
		if( disWeather && world.hasStorm( ) )
		{
			world.setStorm( false );
			plugin.log.info( "[NoWeather] Stopped storm in " + worldName );
		}
		
		if( disThunder && world.isThundering( ) )
		{
			world.setThundering( false );
			plugin.log.info( "[NoWeather] Stopped thunder in " + worldName );
		}
		
	//	plugin.log.info( "[NoWeather] " + worldName + " - Weather  : " + disWeather.toString() );
	//	plugin.log.info( "[NoWeather] " + worldName + " - Thunder  : " + disThunder.toString() );
	//	plugin.log.info( "[NoWeather] " + worldName + " - Lightning: " + disLightning.toString() );
		
		plugin.setConfigNode( "disable-weather", worldName, disWeather );
		plugin.setConfigNode( "disable-thunder", worldName, disThunder );
		plugin.setConfigNode( "disable-lightning", worldName, disLightning );
		plugin.setConfigNode( "disable-snow-accumulation", worldName, disSnow );
		plugin.saveConfig( );
	}
}