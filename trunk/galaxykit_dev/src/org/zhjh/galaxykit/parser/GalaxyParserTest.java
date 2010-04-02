package org.zhjh.galaxykit.parser;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.*;

public class GalaxyParserTest {

    private static final String PATH = "GalaxyTest.galaxy";
    
    public static void main(String[] args) {
	try {
	    GalaxyLexer lexer = new GalaxyLexer();
	    InputStream stream = GalaxyParserTest.class.getResourceAsStream(PATH);
	    lexer.setCharStream(new ANTLRInputStream(stream));
	    GalaxyParser parser = new GalaxyParser(
	    	new CommonTokenStream(lexer)
	    	);
	    parser.program();
	    System.out.println("ok!");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (RecognitionException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
