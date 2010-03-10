package org.zhjh.galaxykit.editor;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.zhjh.galaxykit.parser.ASTFunctionDefiniton;

public class GALLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof ASTFunctionDefiniton){
			return ((ASTFunctionDefiniton)element).getIdentifier();
		}
		return element.toString();
	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return super.getImage(element);
	}
	
}
