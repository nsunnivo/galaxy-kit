package org.zhjh.galaxykit.editor;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.text.IDocument;
import org.zhjh.galaxykit.parser.GalaxyLexer;
import org.zhjh.galaxykit.parser.GalaxyParser;
import org.zhjh.galaxykit.utils.DocumentReader;

public class GALSharedParser {
    
    private GalaxyParser parser;
    private CommonTokenStream tokenStream;
    private Tree ast;
    
    public GALSharedParser() {
	tokenStream = new CommonTokenStream();
	parser = new GalaxyParser(tokenStream);
    }

    public void parse(IDocument doc) {
	DocumentReader reader = new DocumentReader(doc);
	CharStream input;
	try {
	    input = new ANTLRReaderStream(reader);
	    GalaxyLexer lexer = new GalaxyLexer(input);
	    tokenStream.setTokenSource(lexer);
	    ast = (Tree) parser.program().getTree();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (RecognitionException e) {
	    e.printStackTrace();
	}
    }
    
    public Tree getAST() {
	return ast;
    }
    
    public List<RecognitionException> getErrors() {
	return parser.getErrors();
    }
    
}
