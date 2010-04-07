package org.zhjh.galaxykit;

import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.zhjh.galaxykit.utils.SharedTextColors;

/**
 * The activator class controls the plug-in life cycle
 */
public class GALPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "galaxy-kit";

	// The shared instance
	private static GALPlugin plugin;
	
	private ISharedTextColors fSharedTextColors;
	
	/**
	 * The constructor
	 */
	public GALPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
        if (fSharedTextColors != null) {
            fSharedTextColors.dispose();
        }
        plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static GALPlugin getDefault() {
		return plugin;
	}

	public ISharedTextColors getSharedTextColors() {
	    if (fSharedTextColors == null) {
	        fSharedTextColors = new SharedTextColors();
	    }
	    return fSharedTextColors;
	}
	
}
