package org.zhjh.galaxykit.editor;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.zhjh.galaxykit.IGALConstants;

public class GALEditor extends TextEditor implements IGALConstants {
	
	private GALOutlinePage fOutlinePage;
	
	public GALEditor(){
		super();
	}
	
	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setDocumentProvider(new GALDocumentProvider());
		setSourceViewerConfiguration(new GALSourceViewerConfiguration());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)){
			if (fOutlinePage == null){
				fOutlinePage = new GALOutlinePage();
				updateOutlinePage();
			}
			return fOutlinePage;
		}
		return super.getAdapter(adapter);
	}
	
	protected void updateOutlinePage(){
		
	}
	
}
