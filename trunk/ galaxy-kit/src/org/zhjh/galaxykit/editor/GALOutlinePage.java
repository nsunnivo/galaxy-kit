package org.zhjh.galaxykit.editor;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class GALOutlinePage extends ContentOutlinePage {

	private GALOutlinePageContentProvider fContentProvider;
	private GALLabelProvider fLabelProvider;
	
	GALOutlinePage(){
		super();
		fContentProvider = new GALOutlinePageContentProvider();
		fLabelProvider = new GALLabelProvider();
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		final TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(fContentProvider);
		viewer.setLabelProvider(fLabelProvider);
	}

	@Override
	public void dispose(){
		if (fContentProvider != null){
			fContentProvider.dispose();
			fContentProvider = null;
		}
		if (fLabelProvider != null){
			fLabelProvider.dispose();
			fLabelProvider = null;
		}
		super.dispose();
	}
	
}
