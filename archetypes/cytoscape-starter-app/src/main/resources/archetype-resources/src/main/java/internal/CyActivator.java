package ${package}.internal;

import static org.cytoscape.work.ServiceProperties.*;

import java.util.Properties;

import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.work.TaskFactory;
import ${package}.internal.task.SampleTaskFactory;
import ${package}.internal.view.OpenPanelTaskFactory;
import org.osgi.framework.BundleContext;

/**
 * CyActivator start method runs when your App is loaded.
 * Use it to register services and initialize you App.
 */
public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) {
		// Register a task that can be run both from the main menu and as a command from a script.
		{
			SampleTaskFactory taskFactory = new SampleTaskFactory();
			
			Properties props = new Properties();
			// props to register as menu action
			props.put(TITLE, "Select Nodes or Edges");
			props.put(PREFERRED_MENU, APPS_MENU + ".MyApp");
			props.put(ENABLE_FOR, "network");
			// props to register as command
			props.put(COMMAND, "select");
			props.put(COMMAND_NAMESPACE, "myapp");
			props.put(COMMAND_DESCRIPTION, "Select nodes or edges");
			props.put(COMMAND_LONG_DESCRIPTION, "Select X number of random nodes or edges.");
			
			registerService(context, taskFactory, NetworkTaskFactory.class, props); 
		}
		
		// Register a task that opens a panel.
		{
			//
			CyServiceRegistrar registrar = getService(context, CyServiceRegistrar.class);
			OpenPanelTaskFactory panelTaskFactory = new OpenPanelTaskFactory(registrar);
			
			Properties props = new Properties();
			// props to register as menu action
			props.put(TITLE, "Show Sample Panel");
			props.put(PREFERRED_MENU, APPS_MENU + ".MyApp");
			
			registerService(context, panelTaskFactory, TaskFactory.class, props); 
		}
	}

}
