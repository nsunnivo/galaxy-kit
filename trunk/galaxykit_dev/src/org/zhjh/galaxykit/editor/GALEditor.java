package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.MatchingCharacterPainter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.zhjh.galaxykit.options.GALPreferences;

public class GALEditor extends TextEditor {
    
    public GALEditor() {
        super();
    }

    @Override
    protected void initializeEditor() {
	super.initializeEditor();
	setSourceViewerConfiguration(new GALConfiguration());
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
    
    
    
}
