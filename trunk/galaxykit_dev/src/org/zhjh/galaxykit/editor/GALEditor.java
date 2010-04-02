package org.zhjh.galaxykit.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognitionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.MatchingCharacterPainter;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.zhjh.galaxykit.options.GALPreferences;
import org.zhjh.galaxykit.options.IGALPreferencesConstants;

public class GALEditor extends TextEditor {

    private GALSharedParser parser;
    private GALOutlinePage outlinePage;

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
    protected void configureSourceViewerDecorationSupport(
	    SourceViewerDecorationSupport support) {
	super.configureSourceViewerDecorationSupport(support);
    }

    public void update(IDocument doc) {
	try {
	    parser.parse(doc);
	} catch (RecognitionException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    // TODO disable outlinePage!
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
		update(getSourceViewer().getDocument());
		// outlinePage.setInput(parser.getAST());
	    }
	    return outlinePage;
	}
	return super.getAdapter(required);
    }

    private void updateOutlinePage() {
	outlinePage.setInput(parser.getAST());
    }

    @SuppressWarnings("unchecked")
    private void updateAnnotationModel() {
	IAnnotationModel model = getSourceViewer().getAnnotationModel();
	Iterator itor = model.getAnnotationIterator();
	List<Annotation> toBeRemoved = new ArrayList<Annotation>();
	Map<Annotation, Position> toBeAdded = new HashMap<Annotation, Position>(
		16);
	while (itor.hasNext()) {
	    toBeRemoved.add((Annotation) itor.next());
	}
	for (RecognitionException re : parser.getErrors()) {
	    Annotation anno = new Annotation(
		IGALPreferencesConstants.ANNOTATION_TYPE_ERROR, true, re.getMessage());
	    final CommonToken token = (CommonToken)re.token;
	    Position pos = new Position(token.getStartIndex(), token.getStopIndex()
		    - token.getStartIndex() + 1);
	    toBeAdded.put(anno, pos);
	}
	model.getAnnotationIterator();
	((IAnnotationModelExtension) model).replaceAnnotations(
		(Annotation[]) toBeRemoved.toArray(), toBeAdded);
    }

}
