package org.zhjh.galaxykit.editor;

import org.eclipse.ui.editors.text.TextEditor;

public class GALEditor extends TextEditor {
    
    public GALEditor() {
        super();
    }

    @Override
    protected void initializeEditor() {
	super.initializeEditor();
	setSourceViewerConfiguration(new GALConfiguration());
    }
    
}
