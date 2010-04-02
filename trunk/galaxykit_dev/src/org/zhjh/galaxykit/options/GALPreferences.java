package org.zhjh.galaxykit.options;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.zhjh.galaxykit.GALPlugin;

public class GALPreferences extends AbstractPreferenceInitializer
	implements IGALPreferencesConstants {

    private static GALPreferences fgInstance;

    public static GALPreferences getDefault() {
	if (fgInstance == null) {
	    fgInstance = new GALPreferences();
	}
	return fgInstance;
    }

    public GALPreferences() {
	super();
	fgInstance = this;
    }
    
    protected void setDefaultColor(String key, Color color) {
	final IPreferenceStore store = getPreferenceStore();
	PreferenceConverter.setDefault(store, key, color.getRGB());
    }
    
    @Override
    public void initializeDefaultPreferences() {
	Display display = Display.getCurrent();
	setDefaultColor(IGALPreferencesConstants.COMMENT_COLOR, display.getSystemColor(SWT.COLOR_GRAY));
	setDefaultColor(IGALPreferencesConstants.KEYWORD_COLOR, display.getSystemColor(SWT.COLOR_BLACK));
	setDefaultColor(IGALPreferencesConstants.NUMBER_COLOR, display.getSystemColor(SWT.COLOR_RED));
	setDefaultColor(IGALPreferencesConstants.STRING_COLOR, display.getSystemColor(SWT.COLOR_DARK_GREEN));
	setDefaultColor(IGALPreferencesConstants.TYPE_COLOR, display.getSystemColor(SWT.COLOR_BLUE));
    }

    protected IPreferenceStore getPreferenceStore() {
	return GALPlugin.getDefault().getPreferenceStore();
    }

    protected ISharedTextColors getSharedTextColors() {
	return GALPlugin.getDefault().getSharedTextColors();
    }

    protected Color getColor(String key) {
	final IPreferenceStore store = getPreferenceStore();
	final ISharedTextColors colors = getSharedTextColors();
	final RGB rgb = PreferenceConverter.getColor(store, key);
	return colors.getColor(rgb);
    }

    public TextAttribute getKeywordTextAttribute() {
	final Color color = getColor(IGALPreferencesConstants.KEYWORD_COLOR);
	return new TextAttribute(color, null, SWT.BOLD);
    }

    public TextAttribute getStringTextAttribute() {
	final Color color = getColor(IGALPreferencesConstants.STRING_COLOR);
	return new TextAttribute(color);
    }

    public TextAttribute getNumberTextAttribute() {
	final Color color = getColor(IGALPreferencesConstants.NUMBER_COLOR);
	return new TextAttribute(color);
    }

    public TextAttribute getCommentTextAttribute() {
	final Color color = getColor(IGALPreferencesConstants.COMMENT_COLOR);
	return new TextAttribute(color, null, SWT.ITALIC);
    }
    
    public TextAttribute getTypeTextAttribute() {
	final Color color = getColor(IGALPreferencesConstants.TYPE_COLOR);
	return new TextAttribute(color, null, SWT.BOLD);
    }
    
    public Color getCharacterMatchingColor() {
	return getColor(IGALPreferencesConstants.CHARACTER_MATCHING_COLOR);
    }
}
