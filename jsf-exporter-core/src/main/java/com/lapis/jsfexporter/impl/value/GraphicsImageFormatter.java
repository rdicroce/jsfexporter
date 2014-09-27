package com.lapis.jsfexporter.impl.value;

import com.lapis.jsfexporter.spi.IValueFormatter;

import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;

public class GraphicsImageFormatter implements IValueFormatter<HtmlGraphicImage> {
    @Override
    public Class<HtmlGraphicImage> getSupportedClass() {
        return HtmlGraphicImage.class;
    }

    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    public String formatValue(FacesContext context, HtmlGraphicImage component) {
        return component.getAlt();
    }
}
