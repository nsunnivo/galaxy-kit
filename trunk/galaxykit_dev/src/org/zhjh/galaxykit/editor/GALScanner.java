package org.zhjh.galaxykit.editor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonToken;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.zhjh.galaxykit.options.GALPreferences;
import org.zhjh.galaxykit.parser.GalaxyLexer;
import org.zhjh.galaxykit.utils.DocumentReader;

public class GALScanner implements ITokenScanner {

    private GalaxyLexer lexer;
    private CommonToken lastToken;
    private int startOffset;
    private Set<String> typeSet;
    
    public GALScanner(){
	lexer = new GalaxyLexer();
	typeSet = new HashSet<String>();
	typeSet.add("bool");
	typeSet.add("int");
	typeSet.add("fixed");
	typeSet.add("string");
	typeSet.add("abilcmd");
	typeSet.add("actor");
	typeSet.add("actorscope");
	typeSet.add("aifilter");
	typeSet.add("animtarget");
	typeSet.add("bank");
	typeSet.add("camerainfo");
	typeSet.add("color");
	typeSet.add("doodad");
	typeSet.add("fixed");
	typeSet.add("handle");
	typeSet.add("marker");
	typeSet.add("order");
	typeSet.add("playergroup");
	typeSet.add("point");
	typeSet.add("region");
	typeSet.add("revealer");
	typeSet.add("sound");
	typeSet.add("soundlink");
	typeSet.add("text");
	typeSet.add("timer");
	typeSet.add("transmissionsource");
	typeSet.add("trigger");
	typeSet.add("unit");
	typeSet.add("unitfilter");
	typeSet.add("unitgroup");
	typeSet.add("unitref");
	typeSet.add("wave");
	typeSet.add("waveinfo");
	typeSet.add("wavetarget");
    }
    
    @Override
    public int getTokenLength() {
	return lastToken.getStopIndex() - lastToken.getStartIndex() + 1;
    }

    @Override
    public int getTokenOffset() {
	return startOffset + lastToken.getStartIndex();
    }

    @Override
    public IToken nextToken() {
	final GALPreferences prefs = GALPreferences.getDefault();
	lastToken = (CommonToken)lexer.nextToken();
	switch (lastToken.getType()) {
	case GalaxyLexer.SINGLE_LINE_COMMENT:
	case GalaxyLexer.MULTI_LINE_COMMENT:
	    return new Token(prefs.getCommentTextAttribute());
	case GalaxyLexer.IF:
	case GalaxyLexer.ELSE:
	case GalaxyLexer.WHILE:
	case GalaxyLexer.BREAK:
	case GalaxyLexer.CONTINUE:
	case GalaxyLexer.RETURN:
	case GalaxyLexer.VOID:
	case GalaxyLexer.NATIVE:
	case GalaxyLexer.TRUE:
	case GalaxyLexer.FALSE:
	case GalaxyLexer.NULL:
	    return new Token(prefs.getKeywordTextAttribute());
	case GalaxyLexer.INTEGER:
	case GalaxyLexer.FIXED:
	    return new Token(prefs.getNumberTextAttribute());
	case GalaxyLexer.STRING:
	    return new Token(prefs.getStringTextAttribute());
	case GalaxyLexer.IDENTIFIER:
	    if (typeSet.contains(lastToken.getText())) {
		return new Token(prefs.getTypeTextAttribute());
	    }
	    break;
	case GalaxyLexer.EOF:
	    return Token.EOF;
	}
        return new Token(null);
    }

    @Override
    public void setRange(IDocument document, int offset, int length) {
	final DocumentReader reader = new DocumentReader(document, offset, length);
	startOffset = offset;
	try {
	    final ANTLRReaderStream stream = new ANTLRReaderStream(reader);
	    lexer.setCharStream(stream);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
