package org.zhjh.galaxykit.editor;

import java.io.StringReader;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.zhjh.galaxykit.parser.GALNode;
import org.zhjh.galaxykit.parser.GALParser;
import org.zhjh.galaxykit.parser.ParseException;

public class GALOutlinePageContentProvider implements IContentProvider, ITreeContentProvider {

	private GALNode node;
	
	@Override
	public void dispose() {
		node = null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput != null) {
			final IDocument doc = (IDocument) newInput;
			parse(doc.get());
	    }
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement == null){
			return null;
		}
		if (parentElement instanceof GALNode){
			final GALNode parent = (GALNode)parentElement;
			if (parent == node){
				return parent.getChildren();
			}
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element != null){
			return ((GALNode)element).jjtGetParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element) != null && getChildren(element).length != 0;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(node);
	}
	
	protected void parse(final String text){
		final StringReader in = new StringReader(text);
		final GALParser parser = new GALParser(in);
		try {
			node = parser.getAST();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.close();
	}
	
	protected GALNode getAST(){
		return node;
	}

}
