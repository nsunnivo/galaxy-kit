package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.MatchingCharacterPainter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.zhjh.galaxykit.options.GALPreferences;

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
	    final MatchingCharacterPainter painter = new MatchingCharacterPainter(viewer,
		    new DefaultCharacterPairMatcher(new char[] { '(',
			    ')', '[', ']', '{', '}' }));
	    painter.setColor(GALPreferences.getDefault().getCharacterMatchingColor());
	    ((ITextViewerExtension2) viewer).addPainter(painter);
	}
	
    }

    @Override
    protected void configureSourceViewerDecorationSupport(
	    SourceViewerDecorationSupport support) {
	super.configureSourceViewerDecorationSupport(support);
    }
    
    public void update(IDocument doc){
	parser.parse(doc);
	Display.getDefault().asyncExec(new Runnable() {

	    @Override
	    public void run() {
		outlinePage.setInput(parser.getAST());
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
		//outlinePage.setInput(parser.getAST());
	    }
	    return outlinePage;
	}
	return super.getAdapter(required);
    }
    
}
