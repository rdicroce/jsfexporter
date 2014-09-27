/*
 * #%L
 * Lapis JSF Exporter Core
 * %%
 * Copyright (C) 2013 Lapis Software Associates
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
//		Properties mavenProps = new Properties();
//		try {
//todo cannot find this resource -> NullPointerException => server cannot start at all (which is quite unfortunate...)
//           mavenProps.load(getClass().getResourceAsStream("/META-INF/maven/com.lapis.jsfexporter/jsf-exporter-core/pom.properties"));
//		} catch (IOException e) {
//			L.warn("Failed to read exporter version", e);
//		}
//		L.info("Lapis JSF Exporter version {}", mavenProps.getProperty("version"));
		
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
