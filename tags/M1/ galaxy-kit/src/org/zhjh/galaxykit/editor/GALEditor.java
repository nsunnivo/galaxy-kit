package org.zhjh.galaxykit.editor;

import org.eclipse.ui.editors.text.TextEditor;
import org.zhjh.galaxykit.IGALConstants;

public class GALEditor extends TextEditor implements IGALConstants {
	
	public GALEditor(){
		super();
	}
	
	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setDocumentProvider(new GALDocumentProvider());
		setSourceViewerConfiguration(new GALSourceViewerConfiguration());
	}
	
}
