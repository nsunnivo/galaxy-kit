package org.zhjh.galaxykit.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import org.eclipse.jface.text.IDocument;
import org.zhjh.galaxykit.model.GALError;
import org.zhjh.galaxykit.model.GALModel;
import org.zhjh.galaxykit.parser.GalaxyLexer;
import org.zhjh.galaxykit.parser.GalaxyParser;
import org.zhjh.galaxykit.utils.DocumentReader;

public class GALSharedParser {

	private GalaxyParser parser;
	private CommonTokenStream tokenStream;
	private List<RecognitionException> errorList;
	private Tree ast;
	private GALModel model;

	public GALSharedParser() {
		tokenStream = new CommonTokenStream();
		parser = new GalaxyParser(tokenStream);
		errorList = new ArrayList<RecognitionException>();
	}

	public void parse(IDocument doc) {
		DocumentReader reader = new DocumentReader(doc);
		CharStream input;
		try {
			input = new ANTLRReaderStream(reader);
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

			model = new GALModel();
			model.errorList = new ArrayList<GALError>();
			for (RecognitionException re : errorList) {
				GALError error = new GALError();
				error.offset = re.index;
				error.length = re.token == null ? 1 : re.token.getText().length();
				error.message = re.getLocalizedMessage();
				model.errorList.add(error);
			}
			ASTWalker walker = new ASTWalker();
			walker.walk(ast, model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Tree getAST() {
		return ast;
	}
	
	public GALModel getModel(){
		return null;
	}
	
	public Token getToken(int index) {
		return tokenStream.get(index);
	}

	public List<RecognitionException> getErrors() {
		return errorList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Token> getTokens(int start, int stop){
		return tokenStream.getTokens(start, stop);
	}
	
	private class ASTWalker {
		Object walk(Tree node, Object context){
			return null;
		}
	}
}
