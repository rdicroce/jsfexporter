/*
 * #%L
 * Lapis JSF Exporter Core
 * %%
 * Copyright (C) 2013 - 2014 Lapis Software Associates
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
package com.lapis.jsfexporter.impl.value;

import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.omnifaces.util.selectitems.SelectItemsCollector;

import com.lapis.jsfexporter.spi.IValueFormatter;

public class SelectOneFormatter implements IValueFormatter<UISelectOne> {

	@Override
	public Class<UISelectOne> getSupportedClass() {
		return UISelectOne.class;
	}

	@Override
	public int getPrecedence() {
		return 1000;
	}

	@Override
	public String formatValue(FacesContext context, UISelectOne component) {
		for (SelectItem item : SelectItemsCollector.collectFromParent(context, component)) {
			if (item.getValue() == null) {
				if (component.getValue() == null) {
					return item.getLabel();
				}
			} else if (item.getValue().equals(component.getValue())) {
				return item.getLabel();
			}
		}
		
		throw new IllegalArgumentException("Could not find any SelectItem with a value equal to the component's value");
	}

}
