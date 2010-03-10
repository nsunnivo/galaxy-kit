package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class GALOutlinePage extends ContentOutlinePage {

	private GALOutlinePageContentProvider fContentProvider;
	private GALLabelProvider fLabelProvider;
	private IDocument fDocument;
	
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
	
	public void setInput(IDocument input) {
		fDocument = input;
	}

	public void update() {
		final TreeViewer viewer = getTreeViewer();
		if (viewer != null) {
			final Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				if (this.fDocument != null) {
					viewer.refresh(this.fDocument, true);
				}
				control.setRedraw(true);
			}
		}
	}

}
