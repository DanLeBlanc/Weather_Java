import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class WeatherFrame {

	// city variable, defined later by user to choose city to display weather for
	String city = null;
	
	// display functions
	public void displayTemp(Composite shlWeather, CurrentWeather cwd, Label lblTemperature) {
		lblTemperature.setBounds(10, 10, 173, 20);
		lblTemperature.setText("Temperature: " + 
						String.format("%.2f", Calculations.getTemperature(cwd)) + " C");
	}
	
	public void displayHumid(Composite shlWeather, CurrentWeather cwd, Label lblHumidity) {
		lblHumidity.setBounds(10, 36, 173, 20);
		lblHumidity.setText("Humidity: " + Calculations.getHumidity(cwd) + "%");
	}	
	
	public void displayWind(Composite shlWeather, CurrentWeather cwd, Label lblWindspeed) {
		lblWindspeed.setBounds(10, 62, 173, 20);
		lblWindspeed.setText("Windspeed: " + 
				String.format("%.2f",Calculations.getWindSpeed(cwd)) + "km/h");
	}
	
	public void displayWindChill(Composite shlWeather, CurrentWeather cwd, Label lblWindChill) {
		lblWindChill.setBounds(10, 88, 173, 20);
		lblWindChill.setText("Wind Chill: " + 
				String.format("%.2f", Calculations.getWindChill(Calculations.getWindSpeed(cwd), 
				Calculations.getTemperature(cwd))));
	}
	public void displayCloudCover(Composite shlWeather, CurrentWeather cwd, Label lblCloudCover) {
		lblCloudCover.setBounds(10, 114, 173, 20);
		lblCloudCover.setText("Cloud Cover: " + Calculations.getCloudCover(cwd) + "%");
	}
	
	
	protected Shell shlWeather;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			WeatherFrame window = new WeatherFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() 
			throws JSONException, IOException, MalformedURLException {
		Display display = Display.getDefault();
		createContents();
		shlWeather.open();
		shlWeather.layout();
		while (!shlWeather.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() 
		throws JSONException, IOException, MalformedURLException {
	
        // declaring object of "OpenWeatherMap" class, providing API Key
        OpenWeatherMap owm = new OpenWeatherMap(api_key.api_key);
		
		// create window, size, title
		shlWeather = new Shell();
		shlWeather.setSize(450, 227);
		shlWeather.setText("Weather");
		
		// create labels outside button so they can be updated
		Label lblTemperature = new Label(shlWeather, SWT.NONE);
		Label lblHumidity = new Label(shlWeather, SWT.NONE);
		Label lblWindspeed = new Label(shlWeather, SWT.NONE);
		Label lblWindChill = new Label(shlWeather, SWT.NONE);
		Label lblCloudCover = new Label(shlWeather, SWT.NONE);
		
		// button logic
		Button btnSubmit = new Button(shlWeather, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				city = text.getText();
				
				// Display controls
				
		        // getting current weather data for the city
		        CurrentWeather cwd = null;
				try {
					cwd = owm.currentWeatherByCityName(city);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				displayTemp(shlWeather, cwd, lblTemperature);
				displayHumid(shlWeather, cwd, lblHumidity);
				displayWind(shlWeather, cwd, lblWindspeed);
				displayWindChill(shlWeather, cwd, lblWindChill);
				displayCloudCover(shlWeather, cwd, lblCloudCover);
				

				
				// end display controls
				
			}
		});
		btnSubmit.setBounds(314, 79, 90, 30);
		btnSubmit.setText("Submit");
		// end button

		// text area
		text = new Text(shlWeather, SWT.BORDER);
		text.setBounds(194, 36, 210, 26);
		
		// enter city name label
		Label lblEnterCityName = new Label(shlWeather, SWT.NONE);
		lblEnterCityName.setBounds(194, 10, 210, 20);
		lblEnterCityName.setText("Enter city name to get weather:");
		

	}
}
