tree grammar OutlineWalker;

options {
  language = Java;
  output = AST;
  tokenVocab = Galaxy;
  ASTLabelType = CommonTree;
}

@header {
package org.zhjh.galaxykit.parser;
}

rule: ;
