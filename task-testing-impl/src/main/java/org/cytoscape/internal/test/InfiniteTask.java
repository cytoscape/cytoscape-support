
package org.cytoscape.internal.test;

/*
 * #%L
 * Tasks for Testing
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2006 - 2021 The Cytoscape Consortium
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */


import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;


public class InfiniteTask extends AbstractTask {

	public InfiniteTask() { }

	public void run(final TaskMonitor taskMonitor) throws Exception {
		taskMonitor.setProgress(0.0);
		taskMonitor.setStatusMessage("Excuting forever...");
		while(true){ 
			System.out.println("still working..."); 
//			Thread.sleep(1000);
//			if ( cancelled ) {
//				System.out.println("cancelling Infinite Task");
//				return;
//			}
		}
	}
}
