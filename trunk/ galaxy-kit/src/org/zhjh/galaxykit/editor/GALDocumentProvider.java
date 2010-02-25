package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class GALDocumentProvider extends FileDocumentProvider {
	public static final String GAL_PARTITIONING = "__gal_partitioning";
	public static final String GAL_CODE = IDocument.DEFAULT_CONTENT_TYPE;
	public static final String GAL_COMMENT = "__gal_comment";
	public static final String[] GAL_CONTENT_TYPES = { GAL_CODE, GAL_COMMENT };
	
	@Override
	protected void setupDocument(Object element, IDocument document) {
		if (document instanceof IDocumentExtension3){
			IDocumentExtension3 de = (IDocumentExtension3)document;
			IDocumentPartitioner partitioner = getDocumentPartitioner();
			de.setDocumentPartitioner(GAL_PARTITIONING, partitioner);
			partitioner.connect(document);
		}
	}

	protected IDocumentPartitioner getDocumentPartitioner(){
		final MultiLineRule multiLineCommentRule = new MultiLineRule("/*", "*/",
				new Token(GAL_COMMENT));
		final EndOfLineRule singleLineCommentRule = new EndOfLineRule("//",
				new Token(GAL_COMMENT));
		final RuleBasedPartitionScanner scanner = new RuleBasedPartitionScanner();
		final IPredicateRule[] rules = { multiLineCommentRule, singleLineCommentRule };
		scanner.setPredicateRules(rules);
		IDocumentPartitioner paritioner = new FastPartitioner(scanner, GAL_CONTENT_TYPES);
		return paritioner;
	}

}
