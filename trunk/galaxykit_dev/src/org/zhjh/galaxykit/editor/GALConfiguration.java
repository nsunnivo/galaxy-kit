package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

public class GALConfiguration extends TextSourceViewerConfiguration {
    
    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        final PresentationReconciler reconciler = new PresentationReconciler();
        final GALScanner scanner = new GALScanner();
        final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        return reconciler;
    }
    
}
