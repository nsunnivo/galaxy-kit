package org.zhjh.galaxykit.editor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.text.IDocument;
import org.zhjh.galaxykit.parser.GalaxyLexer;
import org.zhjh.galaxykit.parser.GalaxyParser;
import org.zhjh.galaxykit.utils.DocumentReader;

public class GALSharedParser {

	private GalaxyParser parser;
	private CommonTokenStream tokenStream;
	private List<RecognitionException> errorList;
	private Tree ast;

	public GALSharedParser() {
		tokenStream = new CommonTokenStream();
		parser = new GalaxyParser(tokenStream);
		errorList = new ArrayList<RecognitionException>();
	}

	public void parse(InputStream stream) {
		try {
			CharStream input = new ANTLRInputStream(stream);
			parse(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parse(CharStream input){
		try {
			GalaxyLexer lexer = new GalaxyLexer(input);
			tokenStream.setTokenSource(lexer);
			lexer.clearErrors();
			parser.clearErrors();
			ast = (Tree) parser.program().getTree();
			errorList.clear();
			if (lexer.getErrors() != null) {
				errorList.addAll(lexer.getErrors());
			}
			if (parser.getErrors() != null) {
				errorList.addAll(parser.getErrors());
			}
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parse(IDocument doc) {
		try {
			DocumentReader reader = new DocumentReader(doc);
			CharStream input = new ANTLRReaderStream(reader);
			parse(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Tree getAST() {
		return ast;
	}
	
	public Token getToken(int index) {
		return tokenStream.get(index);
	}

	public List<RecognitionException> getErrors() {
		return errorList;
	}
	
}
