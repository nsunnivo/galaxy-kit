package org.zhjh.galaxykit;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class GALPlugin extends AbstractUIPlugin {
	public static GALPlugin fgInstance;
	public GALPlugin(){
		fgInstance = this;
	}
	public static GALPlugin getDefault() {
		return fgInstance;
	}
}
