/*
  Copyright (c) 2006, The Cytoscape Consortium (www.cytoscape.org)

  The Cytoscape Consortium is:
  - Institute for Systems Biology
  - University of California San Diego
  - Memorial Sloan-Kettering Cancer Center
  - Institut Pasteur
  - Agilent Technologies

  This library is free software; you can redistribute it and/or modify it
  under the terms of the GNU Lesser General Public License as published
  by the Free Software Foundation; either version 2.1 of the License, or
  any later version.

  This library is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
  MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
  documentation provided hereunder is on an "as is" basis, and the
  Institute for Systems Biology and the Whitehead Institute
  have no obligations to provide maintenance, support,
  updates, enhancements or modifications.  In no event shall the
  Institute for Systems Biology and the Whitehead Institute
  be liable to any party for direct, indirect, special,
  incidental or consequential damages, including lost profits, arising
  out of the use of this software and its documentation, even if the
  Institute for Systems Biology and the Whitehead Institute
  have been advised of the possibility of such damage.  See
  the GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, write to the Free Software Foundation,
  Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */

package org.cytoscape.internal.test;

import java.awt.event.ActionEvent;

import org.cytoscape.session.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.*;


/**
 *
 */
public class WaitValueAction extends AbstractCyAction {
	private final static long serialVersionUID = 1502339870257629L;

	private TaskManager tm;
	public WaitValueAction(CyApplicationManager appMgr, TaskManager tm) {
		super("Wait Value", appMgr );
		this.tm = tm;
		setPreferredMenu("Help");
	}

	public void actionPerformed(ActionEvent e) {
		Runnable asdf = new Runnable() {
			public void run() {
				DummyTaskFactory tf = new DummyTaskFactory();
				tm.execute(tf);
				System.out.println("got value: " + tf.getValue());
			}};
		new Thread(asdf).start();
	}

	private class DummyTaskFactory implements TaskFactory {
		ValuedTaskExecutor<Integer> vte; 
		public TaskIterator getTaskIterator() {
			vte = new ValuedTaskExecutor<Integer>(new DummyTask());
			return new TaskIterator( vte );
		}
		public int getValue() {
			try {
			return vte.get();
			} catch (Exception e) { return -2; }
		}
	}

	private class DummyTask implements ValuedTask<Integer> {
		boolean cancelled = false;
		public Integer run(TaskMonitor taskMonitor) throws Exception {
			taskMonitor.setProgress(0.0);
			taskMonitor.setStatusMessage("Excuting Valued DUMMY ");
			int i = 0;
			while(i++ < 10){
				System.out.println("still value dummy working..." + Thread.currentThread());
				Thread.sleep(1000);
				if ( cancelled ) {
					System.out.println("cancelling Infinite DUMMY Task");
					return Integer.valueOf(-1);
				}
			}
			return Integer.valueOf(i);
		}
		public void cancel() {
			cancelled = true;
		}
	}
}


