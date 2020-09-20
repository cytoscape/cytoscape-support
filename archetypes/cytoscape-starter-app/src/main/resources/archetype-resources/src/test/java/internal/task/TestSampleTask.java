package ${package}.internal.task;

import static org.junit.Assert.assertEquals;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.NetworkTestSupport;
import org.cytoscape.work.TaskMonitor;
import org.junit.Test;
import org.mockito.Mockito;

public class TestSampleTask {
	
	private NetworkTestSupport support = new NetworkTestSupport();
	
	private CyNetwork createTestNetwork() {
		CyNetwork network = support.getNetwork();
		for(int i = 0; i < 10; i++) 
			network.addNode();
		return network;
	}
	
	@Test
	public void testSampleTask() {
		CyNetwork network = createTestNetwork();
		
		SampleTask task = new SampleTask(network);
		task.numToSelect = 5;
		task.type.setSelectedValue("nodes");
		
		TaskMonitor taskMonitor = Mockito.mock(TaskMonitor.class);
		task.run(taskMonitor);
		
		int selected = network.getDefaultNodeTable().countMatchingRows(CyNetwork.SELECTED, Boolean.TRUE);
		assertEquals(5, selected);
	}

}
