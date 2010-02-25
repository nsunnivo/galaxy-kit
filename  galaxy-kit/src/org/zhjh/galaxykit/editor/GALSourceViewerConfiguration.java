package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

public class GALSourceViewerConfiguration extends TextSourceViewerConfiguration {

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		return new GALPresentationReconciler();
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return GALDocumentProvider.GAL_CONTENT_TYPES;
	}

	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		return GALDocumentProvider.GAL_PARTITIONING;
	}

}
