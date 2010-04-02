package org.zhjh.galaxykit.editor;

import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class GALOutlinePageContentProvider implements IContentProvider,
	ITreeContentProvider {

    private Tree ast;

    @Override
    public void dispose() {
	ast = null;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
	return null;
    }

    @Override
    public Object getParent(Object element) {
	return null;
    }

    @Override
    public boolean hasChildren(Object element) {
	return getChildren(element) != null && getChildren(element).length != 0;
    }

    @Override
    public Object[] getElements(Object inputElement) {
	return getChildren(ast);
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	if (newInput != null) {
	    ast = (Tree)newInput;
	}
    }
}
