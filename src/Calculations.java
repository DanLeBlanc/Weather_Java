import net.aksingh.owmjapis.*;

// all the functions to display the weather information
public class Calculations  {
	
	// func to convert fahrenheit to celsisus
	public static float getCelsius(float temp) {
		return (temp - 32)*5/9;
	}
	
	public static float getTemperature(CurrentWeather cwd) {
		return getCelsius(cwd.getMainInstance().getTemperature());
	}
	
	public static float getWindChill(float v, float t) {
		return (float)(13.12 + 0.6215*t -  11.37*Math.pow(v, 0.16) + 0.3965*t*Math.pow(v, 0.16));
	}
	
	public static float getHumidity(CurrentWeather cwd) {
		return cwd.getMainInstance().getHumidity();
	}
	
	// converts from mp/h to km/h at the end
	public static float getWindSpeed(CurrentWeather cwd) {
		return cwd.getWindInstance().getWindSpeed()*(float)1.6;
	}
	
	public static float getCloudCover(CurrentWeather cwd) {
		return cwd.getCloudsInstance().getPercentageOfClouds();
	}
}
