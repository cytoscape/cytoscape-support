package org.cytoscape.internal.test.tunables;

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


import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.TunableValidator;
import org.cytoscape.work.TunableValidator.ValidationState;
import org.cytoscape.work.util.BoundedDouble;
import org.cytoscape.work.util.BoundedFloat;
import org.cytoscape.work.util.BoundedInteger;
import org.cytoscape.work.util.BoundedLong;
import org.cytoscape.work.util.ListMultipleSelection;
import org.cytoscape.work.util.ListSingleSelection;


public class TunablesTestTask extends AbstractTask implements TunableValidator {
	@Tunable(description="String")
	public String s;

	@Tunable(description="int")
	public int i;

	@Tunable(description="Integer")
	public Integer i2;

	@Tunable(description="long")
	public long l;

	@Tunable(description="Long")
	public Long l2;

	@Tunable(description="double")
	public double d;

	@Tunable(description="Double")
	public Double d2;

	@Tunable(description="float")
	public float f;

	@Tunable(description="Float")
	public Float f2;

	@Tunable(description="File")
	public File file;

	@Tunable(description="BoundedDouble")
	public BoundedDouble bd;

	@Tunable(description="boolean")
	public boolean b;

	@Tunable(description="Boolean")
	public Boolean b2;

	@Tunable(description="Must be \"valid\"")
	public String vt;

	private int getterSetterInt;

	public TunablesTestTask() {
		i2 = Integer.valueOf(1);
		l2 = Long.valueOf(2L);
		d2 = Double.valueOf(3.0);
		f2 = Float.valueOf(4.0f);
//		file = new File("/");
		bd = new BoundedDouble(-10.0, 5.0, +10.0, /* lowerStrict = */ true, /* upperStrict = */ true);
		b2 = Boolean.valueOf(true);
		getterSetterInt = 22;
		vt = "";
	}

	public void run(TaskMonitor e) {
		System.err.println("String="+s);
		System.err.println("int="+i);
		System.err.println("Integer="+i2);
		System.err.println("long="+l);
		System.err.println("Long="+l2);
		System.err.println("double="+d);
		System.err.println("Double="+d2);
		System.err.println("float="+f);
		System.err.println("Float="+f2);
		System.err.println("File="+file);
		System.err.println("BoundedDouble="+bd.getValue());
		System.err.println("boolean="+b);
		System.err.println("Boolean="+b2);
		System.err.println("getterSetterInt="+getterSetterInt);
		System.err.println("Validated tunable="+vt);
	}

	@Tunable(description="Getter/setter int")
	public Integer getInt() { return new Integer(getterSetterInt); }

	public void setInt(final Integer newValue) { getterSetterInt = newValue; }

	public ValidationState getValidationState(final Appendable errMsg) {
		if (vt != null && vt.equals("valid"))
			return ValidationState.OK;

		try {
			errMsg.append("Bad input (" + vt + "): \"valid\" expected.");
		} finally {
			return ValidationState.INVALID;
		}
	}

}
