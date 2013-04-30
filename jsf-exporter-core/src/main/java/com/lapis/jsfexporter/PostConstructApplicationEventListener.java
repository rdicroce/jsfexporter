package com.lapis.jsfexporter;

import java.io.IOException;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostConstructApplicationEventListener implements SystemEventListener {

	private static final Logger L = LoggerFactory.getLogger(PostConstructApplicationEventListener.class);
	
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		Properties mavenProps = new Properties();
		try {
			mavenProps.load(getClass().getResourceAsStream("/META-INF/maven/com.lapis.jsfexporter/jsf-exporter-core/pom.properties"));
		} catch (IOException e) {
			L.warn("Failed to read exporter version", e);
		}
		L.info("Lapis JSF Exporter version {}", mavenProps.getProperty("version"));
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExportTypeFactoryFactory.initialize(context);
		ExportSourceFactory.initialize(context);
		ValueFormatterFactory.initialize(context);
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return true;
	}

}
