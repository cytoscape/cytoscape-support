package ${package}.internal.task;

import java.util.Collection;
import java.util.Objects;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNetworkTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.ListSingleSelection;

/**
 * This is an example Task that demonstrates several features of the {@link Task} and {@link Tunable} system.
 * 
 * It uses a Tunable annotated fields that demonstrates how users can supply input to a task.
 * 
 * It inherits from an abstract class provided in the core task API that provides pre-built support 
 * for many specific types of tasks (this one is network related).
 */
public class SampleTask extends AbstractNetworkTask {

	// Tunable annotated fields are set by Cytoscape
	
	@Tunable(description="Number of elements to select")
	public int numToSelect = 1; // Default value for the tubable can be set here or in the constructor.
	
	@Tunable(description="The type of elements to select")
	public ListSingleSelection<String> type = new ListSingleSelection<>("nodes", "edges"); // first value is the default


	public SampleTask(CyNetwork network) {
		super(network);
	}
	
	/** 
	 * This is where the actual work of the Task gets accomplished. 
	 */
	public void run(TaskMonitor taskMonitor) {

		boolean nodes = Objects.equals("nodes", type.getSelectedValue());
		
		// Validate the input parameter.  
		// Tunables can also do some validation for themselves.  
		// See the Tunables documentation for more information.
		if(numToSelect < 0)
			throw new IllegalArgumentException("The number of elements to select must be greater than or equal to 0.");

		if(nodes && numToSelect > network.getNodeCount())
			throw new IllegalArgumentException("The number of nodes to select is greater than the number of available nodes.");
		else if(!nodes && numToSelect > network.getEdgeCount())
			throw new IllegalArgumentException("The number of edges to select is greater than the number of available edges.");

		// Give the task a title.
		taskMonitor.setTitle("Selecting.");

		Collection<? extends CyIdentifiable> elements;
		if(nodes)
			elements = network.getNodeList();
		else
			elements = network.getEdgeList();
		
		
		int selected = 0;

		// Loop over all nodes or edges in the network.
		for(CyIdentifiable ele : elements) {

			// cancelled is inherited from AbstractTask and is set when the cancel() method is called.
			// Also consider calling a cleanup method to  undo anyting already accomplished!
			if(cancelled)
				break;

			if(++selected > numToSelect)
				break;

			// Set the selected attribute to true for the given node.
			CyRow row = network.getRow(ele);
			row.set(CyNetwork.SELECTED, true);


			// Update the progress bar with the percent complete
			// and a status message.
			double percent = (double)selected / (double)numToSelect;
			taskMonitor.setProgress(percent);
			taskMonitor.setStatusMessage("selected: " + selected);

			// This just exists so that you will actually see the task dialog.  
			// You should delete the following 3 lines from your code!
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
	}
}
