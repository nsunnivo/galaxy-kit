package org.zhjh.galaxykit.editor;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationPresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.zhjh.galaxykit.options.GALPreferences;

public class GALErrorAnnotation extends Annotation implements IAnnotationPresentation {

	public GALErrorAnnotation() {
		super(false);
	}

	public GALErrorAnnotation(String type, String text) {
		super(type, false, text);
	}
	
	@Override
	public int getLayer() {
		return IAnnotationPresentation.DEFAULT_LAYER;
	}

	@Override
	public void paint(GC gc, Canvas canvas, Rectangle bounds) {
		Point canvasSize = canvas.getSize();
		
		int x = 0;
		int y = bounds.y;
		int h = bounds.height;

		if (y + h > canvasSize.y)
			h = canvasSize.y - y;

		if (y < 0) {
			h = h + y;
			y = 0;
		}

		if (h <= 0)
			return;

		gc.setBackground(canvas.getDisplay().getSystemColor(
		        SWT.COLOR_INFO_BACKGROUND));
		gc.fillRectangle(bounds);

		Image image = getErrorImage();
		if (image != null)
			gc.drawImage(image, x, y + 2);
	}
	
	protected Image getErrorImage() {
		return GALPreferences.getDefault().getImage("error.gif");
	}

}
