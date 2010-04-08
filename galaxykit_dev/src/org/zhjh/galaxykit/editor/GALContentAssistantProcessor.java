package org.zhjh.galaxykit.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Point;
import org.zhjh.galaxykit.options.GALPreferences;

public class GALContentAssistantProcessor implements IContentAssistProcessor {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		Map<String, Tree> galaxyNative = GALPreferences.getDefault()
				.getGalaxyNative();
		Point range = viewer.getSelectedRange();
		String leftText = findIdentifierLeftPart(viewer.getDocument(), offset);
		for (String name : galaxyNative.keySet()) {
			String displayText = getDisplayText(galaxyNative.get(name));
			if (leftText == null) {
				CompletionProposal proposal = new CompletionProposal(name,
						range.x, range.y, name.length(), null, displayText,
						null, null);
				proposals.add(proposal);
			} else if (name.startsWith(leftText)) {
				CompletionProposal proposal = new CompletionProposal(name,
						range.x - leftText.length(), range.y
								+ leftText.length(), name.length(), null,
						displayText, null, null);
				proposals.add(proposal);
			}
		}
		ICompletionProposal[] ret = new ICompletionProposal[proposals.size()];
		ret = proposals.toArray(ret);
		return ret;
	}
	
	private String getDisplayText(Tree node){
		String name = node.getChild(0).getText();
		String returnType = node.getChild(1).getText();
		StringBuilder buffer = new StringBuilder();
		buffer.append(name);
		buffer.append("(");
		Tree parameters = node.getChild(2);
		for (int i=0; i<parameters.getChildCount(); i++) {
			Tree parameter = parameters.getChild(i);
			buffer.append(parameter.getChild(0).getText());
			buffer.append(" ");
			buffer.append(parameter.getChild(1).getText());
			if (i != parameters.getChildCount() - 1) {
				buffer.append(", ");
			}
		}
		buffer.append(")");
		if (!returnType.equals("void")) {
			buffer.append(" : ");
			buffer.append(returnType);
		}
		return buffer.toString();
	}

	private String findIdentifierLeftPart(IDocument doc, int offset) {
		try {
			int i = 1;
			while (isGalaxyIdentifierPart(doc.getChar(offset - i))) {
				i++;
			}
			if (isGalaxyIdentifierStart(doc.getChar(offset - i))) {
				return doc.get(offset - i, i);
			} else if (i > 1) {
				i--;
				return doc.get(offset - i, i);
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private boolean isGalaxyIdentifierStart(int ch) {
		return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z')
				|| ch == '_';
	}

	private boolean isGalaxyIdentifierPart(int ch) {
		return isGalaxyIdentifierStart(ch) || ('0' <= ch && ch <= '9');
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

}