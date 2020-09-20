package ${package}.internal.task;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.task.AbstractNetworkTaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * The TaskFactory is a Singleton object whose sole purpose is to create new instances of a specific kind of Task.  
 * TaskFactory objects are meant to be registered as OSGi services for the specific TaskFactory
 * interface they implement (in this case NetworkTaskFactory). 
 */
public class SampleTaskFactory extends AbstractNetworkTaskFactory {

	/**
	 * This method should return a new instance of SampleTask each time it gets called.
	 */
	public TaskIterator createTaskIterator(CyNetwork network) {
		return new TaskIterator(new SampleTask(network));
	}
}

