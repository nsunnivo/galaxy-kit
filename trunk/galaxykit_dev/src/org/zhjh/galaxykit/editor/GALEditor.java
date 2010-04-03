package org.zhjh.galaxykit.editor;

import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.MatchingCharacterPainter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.MarkerAnnotationPreferences;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.zhjh.galaxykit.GALPlugin;
import org.zhjh.galaxykit.options.GALPreferences;
import org.zhjh.galaxykit.options.IGALPreferencesConstants;

public class GALEditor extends TextEditor {

	private GALSharedParser parser;
	private GALOutlinePage outlinePage;
	private boolean disableOutlinePage;

	public GALEditor() {
		super();
		parser = new GALSharedParser();
	}

	public GALSharedParser getSharedParser() {
		return parser;
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new GALConfiguration(this));
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		// character matching.
		final ISourceViewer viewer = getSourceViewer();
		if (viewer instanceof ITextViewerExtension2) {
			final MatchingCharacterPainter painter = new MatchingCharacterPainter(
					viewer, new DefaultCharacterPairMatcher(new char[] { '(',
							')', '[', ']', '{', '}' }));
			painter.setColor(GALPreferences.getDefault()
					.getCharacterMatchingColor());
			((ITextViewerExtension2) viewer).addPainter(painter);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	protected void configureSourceViewerDecorationSupport(
			SourceViewerDecorationSupport support) {
		super.configureSourceViewerDecorationSupport(support);

		MarkerAnnotationPreferences prefs = new MarkerAnnotationPreferences();
		MarkerAnnotationPreferences.initializeDefaultValues(GALPlugin.getDefault().getPreferenceStore());
		Iterator<Object> itor = prefs.getAnnotationPreferences().iterator();
		while (itor.hasNext()) {
			support.setAnnotationPreference((AnnotationPreference) itor.next());
		}
		support.install(GALPlugin.getDefault().getPreferenceStore());
	}

	public void update(IDocument doc) {
		disableOutlinePage = false;
		parser.parse(doc);
		if (parser.getErrors().size() > 0) {
			disableOutlinePage = true;
		}
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				updateOutlinePage();
				updateAnnotationModel();
			}

		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(final Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage = new GALOutlinePage();
				outlinePage.addSelectionChangedListener(outlineSelectionChangedListener);
				update(getSourceViewer().getDocument());
				// outlinePage.setInput(parser.getAST());
			}
			return outlinePage;
		}
		return super.getAdapter(required);
	}
	private ISelectionChangedListener outlineSelectionChangedListener = new ISelectionChangedListener() {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection.isEmpty()) {
				resetHighlightRange();
			} else {
				Tree node = (Tree)((IStructuredSelection)selection).getFirstElement();
				CommonToken token = (CommonToken)parser.getToken(node.getTokenStartIndex());
				int start = token.getStartIndex();
				int length = token.getStopIndex() - token.getStartIndex() + 1;
		        selectAndReveal(start, length);
		        resetHighlightRange();
		        setHighlightRange(start, length, true);
		        markInNavigationHistory();
			}
		}

	};
	
	private void updateOutlinePage() {
		if (disableOutlinePage) {
			outlinePage.setInput(null);
		} else {
			outlinePage.setInput(parser.getAST());
		}
	}

	@SuppressWarnings("unchecked")
	private void updateAnnotationModel() {
		IAnnotationModel model = getSourceViewer().getAnnotationModel();
		Iterator itor = model.getAnnotationIterator();
		while (itor.hasNext()) {
			Annotation annotation = (Annotation) itor.next();
			if (annotation.getType().equals(
					IGALPreferencesConstants.ANNOTATION_TYPE_ERROR)) {
				model.removeAnnotation(annotation);
				annotation.markDeleted(true);
			}
		}
		List<RecognitionException> errors = parser.getErrors();
		if (errors != null) {
			for (RecognitionException re : errors) {
				GALErrorAnnotation anno = new GALErrorAnnotation(
						IGALPreferencesConstants.ANNOTATION_TYPE_ERROR, false, re
								.getMessage());
				Position pos = null;
				if (re.token == null) {
					pos = new Position(re.index, 1);
				} else {
					final CommonToken token = (CommonToken) re.token;
					pos = new Position(token.getStartIndex(), token
							.getStopIndex()
							- token.getStartIndex() + 1);
				}
				model.addAnnotation(anno, pos);
			}
		}
	}

}
