package ru.xPaw.NoWeather;

import java.io.IOException;
import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

public class Main extends JavaPlugin
{
	public FileConfiguration config;
	
	@Override
	public void onEnable( )
	{
		config = getConfig( );
		
		new EventListener( this );
		
		// Load worlds
		List<World> worlds = getServer( ).getWorlds( );
		
		for( World world : worlds )
		{
			worldLoaded( world );
		}
		
		// Metrics
		try
		{
			new MetricsLite( this ).start( );
		}
		catch( IOException e )
		{
			getLogger( ).warning( "[Metrics] " + e.getMessage( ) );
		}
	}
	
	public boolean isNodeDisabled( String name, String worldName )
	{
		return config.getBoolean( worldName + "." + name, true );
	}
	
	public void setConfigNode( String name, String worldName, Boolean value )
	{
		config.set( worldName + "." + name, value );
	}
	
	public void worldLoaded( World world )
	{
		String worldName = world.getName( );
		
		if( !config.contains( worldName ) )
		{
			getLogger( ).info( worldName + " - no configuration, generating defaults" );
		}
		
		Boolean disWeather   = isNodeDisabled( "disable-weather", worldName );
		Boolean disThunder   = isNodeDisabled( "disable-thunder", worldName );
		Boolean disLightning = isNodeDisabled( "disable-lightning", worldName );
		Boolean disIce       = isNodeDisabled( "disable-ice-accumulation", worldName );
		Boolean disSnow      = isNodeDisabled( "disable-snow-accumulation", worldName );
		
		if( disWeather && world.hasStorm( ) )
		{
			world.setStorm( false );
			getLogger( ).info( "Stopped storm in " + worldName );
		}
		
		if( disThunder && world.isThundering( ) )
		{
			world.setThundering( false );
			getLogger( ).info( "Stopped thunder in " + worldName );
		}
		
		setConfigNode( "disable-weather", worldName, disWeather );
		setConfigNode( "disable-thunder", worldName, disThunder );
		setConfigNode( "disable-lightning", worldName, disLightning );
		setConfigNode( "disable-ice-accumulation", worldName, disIce );
		setConfigNode( "disable-snow-accumulation", worldName, disSnow );
		saveConfig( );
	}
}
