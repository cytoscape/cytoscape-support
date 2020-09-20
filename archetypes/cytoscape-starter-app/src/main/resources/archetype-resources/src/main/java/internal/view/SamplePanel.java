package ${package}.internal.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.events.SetCurrentNetworkEvent;
import org.cytoscape.application.events.SetCurrentNetworkListener;
import org.cytoscape.application.swing.CytoPanelComponent2;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.service.util.CyServiceRegistrar;


public class SamplePanel extends JPanel implements CytoPanelComponent2, SetCurrentNetworkListener, RowsSetListener {

	public static final String ID = "myapp.samplePanel";
	
	
	private final CyServiceRegistrar registrar;
	
	private JLabel networkNameLabel;
	private JLabel nodeCountLabel;
	private JLabel edgeCountLabel;
	private JLabel nodeSelectedLabel;
	private JLabel edgeSelectedLabel;
	
	
	public SamplePanel(CyServiceRegistrar registrar) {
		this.registrar = registrar;

		networkNameLabel = new JLabel();
		nodeCountLabel = new JLabel();
		edgeCountLabel = new JLabel();
		nodeSelectedLabel = new JLabel();
		edgeSelectedLabel = new JLabel();
		
		JLabel networkNameTitle = new JLabel("Network Name: ");
		JLabel nodeCountTitle = new JLabel("Node Count: ");
		JLabel edgeCountTitle = new JLabel("Edge Count: ");
		JLabel nodeSelectedTitle = new JLabel("Selected Nodes: ");
		JLabel edgeSelectedTitle = new JLabel("Selected Edges: ");
		
		JPanel body = new JPanel(new GridLayout(5,2));
		
		body.add(networkNameTitle);
		body.add(networkNameLabel);
		body.add(nodeCountTitle);
		body.add(nodeCountLabel);
		body.add(edgeCountTitle);
		body.add(edgeCountLabel);
		body.add(nodeSelectedTitle);
		body.add(nodeSelectedLabel);
		body.add(edgeSelectedTitle);
		body.add(edgeSelectedLabel);
		
		setLayout(new BorderLayout());
		add(body, BorderLayout.NORTH);
		
		CyApplicationManager applicationManager = registrar.getService(CyApplicationManager.class);
		CyNetwork network = applicationManager.getCurrentNetwork();
		updatePanel(network);
	}
	
	
	@Override
	public void handleEvent(SetCurrentNetworkEvent event) {
		// Called whenever the current network changes.
		updatePanel(event.getNetwork());
	}
	
	
	@Override
	public void handleEvent(RowsSetEvent e) {
		// Called whenever the "selected" column in any table changes. 
		// Need to check if the selection in the current network changed.
		if(e.containsColumn(CyNetwork.SELECTED)) {
			CyApplicationManager applicationManager = registrar.getService(CyApplicationManager.class);
			CyNetwork network = applicationManager.getCurrentNetwork();
			if(network == null) {
				updatePanel(null);
			} else {
				// only handle event if it is for the current network
				if(e.getSource() == network.getDefaultEdgeTable() || e.getSource() == network.getDefaultNodeTable()) {
					updatePanel(network);
				}
			}
		}
	}
	
	private void updatePanel(CyNetwork network) {
		if(network == null) {
			networkNameLabel.setText("-none-");
			nodeCountLabel.setText("");
			edgeCountLabel.setText("");
			nodeSelectedLabel.setText("");
			edgeSelectedLabel.setText("");
		} else {
			String name = network.getRow(network).get(CyNetwork.NAME, String.class);
			int nodeCount = network.getNodeCount();
			int edgeCount = network.getEdgeCount();
			int selectedNodes = network.getDefaultNodeTable().countMatchingRows(CyNetwork.SELECTED, Boolean.TRUE);
			int selectedEdges = network.getDefaultEdgeTable().countMatchingRows(CyNetwork.SELECTED, Boolean.TRUE);
			
			networkNameLabel.setText(name);
			nodeCountLabel.setText(String.valueOf(nodeCount));
			edgeCountLabel.setText(String.valueOf(edgeCount));
			nodeSelectedLabel.setText(String.valueOf(selectedNodes));
			edgeSelectedLabel.setText(String.valueOf(selectedEdges));
		}
	}
	
	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.EAST; // open panel in the "Results" area
	}

	@Override
	public String getTitle() {
		return "My App Sample Panel";
	}

	@Override
	public Icon getIcon() {
		// It is highly recommended to provide your App with a nice icon.
		return null;
	}

	@Override
	public String getIdentifier() {
		return ID;
	}

}
