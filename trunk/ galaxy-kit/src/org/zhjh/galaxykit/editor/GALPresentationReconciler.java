package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class GALPresentationReconciler extends PresentationReconciler {
	
	public GALPresentationReconciler(){
		super();
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				new GALCodeScanner());
		setDocumentPartitioning(GALDocumentProvider.GAL_PARTITIONING);
		setDamager(dr, GALDocumentProvider.GAL_CODE);
		setRepairer(dr, GALDocumentProvider.GAL_CODE);
	}
	
	static class GALCodeScanner extends BufferedRuleBasedScanner {
		
		public static final String[] GAL_KEYWORDS = {
			"if",
			"else",
			"while",
			"do",
			"break",
			"continue",
			"struct",
			"include",
			"true",
			"false",
			"null",
			"return",
			"void",
			"static",
			"native",
			"const",
			"typedef"
		};
		
		public static final String[] GAL_TYPES = {
			"abilcmd",
			"actor",
			"actorscope",
			"aifilter",
			"animtarget",
			"bank",
			"bool",
			"byte",
			"camerainfo",
			"char",
			"color",
			"doodad",
			"fixed",
			"handle",
			"int",
			"marker",
			"order",
			"playergroup",
			"point",
			"region",
			"revealer",
			"sound",
			"soundlink",
			"string",
			"text",
			"timer",
			"transmissionsource",
			"trigger",
			"unit",
			"unitfilter",
			"unitgroup",
			"unitref",
			"wave",
			"waveinfo",
			"wavetarget"
		};
		
		private Color cKeyword;
		private Color cType;
		private Color cNumber;
		private Color cString;
		
		public GALCodeScanner(){
			reinit();
		}
		
		protected void reinit(){
			reinitPrefs();
			reinitRules();
		}
		
		protected void reinitPrefs(){
			Display display = Display.getDefault();
			cKeyword = new Color(display, new RGB(0, 0, 0));
			cType = new Color(display, new RGB(0, 0, 255));
			cNumber = new Color(display, new RGB(255, 0, 0));
			cString = new Color(display, new RGB(0, 255, 0));
		}
		
		protected void reinitRules(){
			WordRule keywordRule = new WordRule(
					new GALWordDetector());
			IToken keywordToken = new Token(
					new TextAttribute(cKeyword, null, SWT.BOLD));
			IToken typeToken = new Token(
					new TextAttribute(cType));
			IToken numberToken = new Token(
					new TextAttribute(cNumber));
			IToken stringToken = new Token(
					new TextAttribute(cString));
			for (int i=0; i<GAL_KEYWORDS.length; i++){
				keywordRule.addWord(GAL_KEYWORDS[i], keywordToken);
			}
			for (int i=0; i<GAL_TYPES.length; i++){
				keywordRule.addWord(GAL_TYPES[i], typeToken);
			}
			NumberRule numberRule = new NumberRule(numberToken);
			SingleLineRule stringRule = new SingleLineRule("\"", "\"", stringToken);
			IRule[] rules = { keywordRule, numberRule, stringRule };
			setRules(rules);
		}
		
		class GALWordDetector implements IWordDetector {

			@Override
			public boolean isWordPart(char c) {
				return Character.isJavaIdentifierPart(c);
			}

			@Override
			public boolean isWordStart(char c) {
				return Character.isJavaIdentifierStart(c);
			}
			
		}
	}
	
}
