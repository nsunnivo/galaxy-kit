package org.zhjh.galaxykit.editor;

import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class GALOutlinePage extends ContentOutlinePage {

    private GALOutlinePageContentProvider contentProvider;
    private GALOutlinePageLabelProvider labelProvider;
    private IDocument document;

    GALOutlinePage() {
	super();
	contentProvider = new GALOutlinePageContentProvider();
	labelProvider = new GALOutlinePageLabelProvider();
    }

    @Override
    public void createControl(Composite parent) {
	super.createControl(parent);
	final TreeViewer viewer = getTreeViewer();
	viewer.setContentProvider(contentProvider);
	viewer.setLabelProvider(labelProvider);
    }

    @Override
    public void dispose() {
	if (contentProvider != null) {
	    contentProvider.dispose();
	    contentProvider = null;
	}
	if (labelProvider != null) {
	    labelProvider.dispose();
	    labelProvider = null;
	}
	super.dispose();
    }

    public void setInput(Tree ast) {
	final TreeViewer viewer = getTreeViewer();
	if (viewer != null) {
	    viewer.setInput(ast);
	}
	update();
    }

    public void update() {
	final TreeViewer viewer = getTreeViewer();
	if (viewer != null) {
	    final Control control = viewer.getControl();
	    if (control != null && !control.isDisposed()) {
		control.setRedraw(false);
		if (this.document != null) {
		    viewer.refresh(this.document, true);
		}
		control.setRedraw(true);
	    }
	}
    }

}
