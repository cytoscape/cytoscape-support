package ${package}.internal.view;

import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class OpenPanelTaskFactory extends AbstractTaskFactory {

	private final CyServiceRegistrar registrar;
	
	public OpenPanelTaskFactory(CyServiceRegistrar registrar) {
		this.registrar = registrar;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new OpenPanelTask(registrar));
	}

}
