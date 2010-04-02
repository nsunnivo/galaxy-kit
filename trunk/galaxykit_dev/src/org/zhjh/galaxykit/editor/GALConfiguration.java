package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

public class GALConfiguration extends TextSourceViewerConfiguration {
    
    private GALEditor editor;
    
    public GALConfiguration(GALEditor editor) {
	this.editor = editor;
    }
    
    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        final PresentationReconciler reconciler = new PresentationReconciler();
        final GALScanner scanner = new GALScanner();
        final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        return reconciler;
    }

    @Override
    public IReconciler getReconciler(ISourceViewer sourceViewer) {
	return new MonoReconciler(new IReconcilingStrategy(){

	    private IDocument doc;

	    protected void internalReconcile(){
		editor.update(doc);
	    }
	    
	    @Override
	    public void reconcile(IRegion partition) {
		internalReconcile();
	    }

	    @Override
	    public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		internalReconcile();
	    }

	    @Override
	    public void setDocument(IDocument document) {
		doc = document;
	    }
	    
	}, false);
    }
    
}
