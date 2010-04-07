package org.zhjh.galaxykit.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.zhjh.galaxykit.options.GALPreferences;
import org.zhjh.galaxykit.parser.GalaxyParser;

public class GALConfiguration extends TextSourceViewerConfiguration {

	private GALEditor editor;

	public GALConfiguration(GALEditor editor) {
		this.editor = editor;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();
		final GALScanner scanner = new GALScanner();
		final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		return new MonoReconciler(new IReconcilingStrategy() {

			private IDocument doc;

			protected void internalReconcile() {
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

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant contentAssistant = new ContentAssistant();
		contentAssistant.setContentAssistProcessor(new GALContentAssistantProcessor(),
				IDocument.DEFAULT_CONTENT_TYPE);
		return contentAssistant;
	}
	public class GALContentAssistantProcessor implements IContentAssistProcessor  {

		@Override
		public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
				int offset) {
			List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
			Map<String, Tree> galaxyNative = GALPreferences.getDefault().getGalaxyNative();
			Point range = viewer.getSelectedRange();
			String leftText = findIdentifierLeftPart(viewer.getDocument(), offset);
			
			for (String name : galaxyNative.keySet()) {
				if (leftText == null) {
					CompletionProposal proposal = new CompletionProposal(
							name, range.x, range.y, name.length());
					proposals.add(proposal);
				} else if (name.startsWith(leftText)){
					CompletionProposal proposal = new CompletionProposal(
							name, range.x - leftText.length(), range.y + leftText.length(), 
							name.length());
					proposals.add(proposal);
				}
			}
			ICompletionProposal[] ret = new ICompletionProposal[proposals.size()];
			ret = proposals.toArray(ret);
			return ret;
		}
		
		private String findIdentifierLeftPart(IDocument doc, int offset){
			try {
				int i = 1;
				while (isGalaxyIdentifierPart(doc.getChar(offset - i))){
					i ++;
				}
				if (isGalaxyIdentifierStart(doc.getChar(offset - i))){
					return doc.get(offset - i, i);
				} else if (i > 1) {
					i --;
					return doc.get(offset - i, i);
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		private boolean isGalaxyIdentifierStart(int ch){
			return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ch == '_';
		}
		private boolean isGalaxyIdentifierPart(int ch){
			return isGalaxyIdentifierStart(ch) || ('0' <= ch && ch <= '9');
		}

		@Override
		public IContextInformation[] computeContextInformation(ITextViewer viewer,
				int offset) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public char[] getCompletionProposalAutoActivationCharacters() {
			return new char[] {
		      ' ' };
		}

		@Override
		public char[] getContextInformationAutoActivationCharacters() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IContextInformationValidator getContextInformationValidator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getErrorMessage() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
