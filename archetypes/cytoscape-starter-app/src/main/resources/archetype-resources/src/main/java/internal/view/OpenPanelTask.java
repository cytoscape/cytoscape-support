package ${package}.internal.view;

import java.util.Properties;

import org.cytoscape.application.events.SetCurrentNetworkListener;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class OpenPanelTask extends AbstractTask {
	
	public static final String TITLE = "Show Sample Panel";
	
	private final CyServiceRegistrar registrar;
	
	
	public OpenPanelTask(CyServiceRegistrar registrar) {
		this.registrar = registrar;
	}
	
	/**
	 * If the panel hasn't been created yet then create it and register it.
	 */
	@Override
	public void run(TaskMonitor taskMonitor) {
		// Check if the panel is already created
		CytoPanelComponent panel = null;
		try {
			panel = registrar.getService(CytoPanelComponent.class, "(id=" + SamplePanel.ID + ")");
		} catch (Exception ex) {
		}
		
		if(panel == null) {
			panel = new SamplePanel(registrar);
			
			// Set an "id" property so we can look the panel up next time this method runs.
			Properties props = new Properties();
			props.setProperty("id", SamplePanel.ID);
			registrar.registerService(panel, CytoPanelComponent.class, props);
			registrar.registerService(panel, SetCurrentNetworkListener.class, new Properties());
			registrar.registerService(panel, RowsSetListener.class, new Properties());
		}
	}

}