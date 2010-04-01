grammar Galaxy;

options {
  language = Java;
  output = AST;
}

@header {
package org.zhjh.galaxykit.parser;
}

@lexer::header {
package org.zhjh.galaxykit.parser;
}

rule: EOF;

WHITESPACE : (' '| '\t' | '\r' | '\n' | '\f')+ { $channel = HIDDEN; };
SINGLE_LINE_COMMENT : '//' ~('\r' | '\n')* { $channel = HIDDEN; };
MULTI_LINE_COMMENT options { greedy = false; } : '/*' .* '*/' { $channel = HIDDEN; };

IF : 'if';
ELSE : 'else';
WHILE : 'while';
BREAK : 'break';
CONTINUE : 'continue';
RETURN : 'return';
VOID : 'void';
NATIVE : 'native';
TRUE : 'true';
FALSE : 'false';
NULL : 'null';

IDENTIFIER : IDENTIFIER_START IDENTIFIER_PART*;
fragment IDENTIFIER_START : LETTER | '_';
fragment LETTER : 'a'..'z' | 'A'..'Z';
fragment IDENTIFIER_PART : IDENTIFIER_START | DIGIT;

INTEGER : DECIMAL_INTEGER | OCTAL_INTEGER | HEXADECIMAL_INTEGER;
fragment DECIMAL_INTEGER : '0' | NON_ZERO_DIGIT DIGIT*;
fragment HEXADECIMAL_INTEGER : '0x' HEXADECIMAL_DIGIT+;
fragment HEXADECIMAL_DIGIT : DIGIT | 'a'..'f' | 'A'..'F';
fragment OCTAL_INTEGER : '0' DIGIT+;
fragment DIGIT : '0'..'9';
fragment NON_ZERO_DIGIT : '1'..'9';
FIXED : DECIMAL_INTEGER '.' DIGIT+;

STRING :  '"' ( ~('\\'  |'"' | '\r' | '\n') | ('\\' .) )* '"';
