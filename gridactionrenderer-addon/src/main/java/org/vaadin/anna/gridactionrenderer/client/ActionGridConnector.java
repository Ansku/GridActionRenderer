/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.anna.gridactionrenderer.client;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.TooltipInfo;
import com.vaadin.client.connectors.GridConnector;
import com.vaadin.shared.ui.Connect;

/**
 * There is no built-in tooltip support for custom rendered cells where only a
 * part of the cell gets a tooltip, and even less so when the contents require
 * several different tooltips, as is the case with action icons. This connector
 * overrides the default behaviour to makes that special tooltip handling
 * possible.
 */
@Connect(org.vaadin.anna.gridactionrenderer.ActionGrid.class)
public class ActionGridConnector extends GridConnector {

    @Override
    public TooltipInfo getTooltipInfo(Element element) {
        if (element.hasAttribute(GridActionRendererConnector.TOOLTIP)) {
            return new TooltipInfo(
                    element.getAttribute(GridActionRendererConnector.TOOLTIP),
                    null);
        }
        return super.getTooltipInfo(element);
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }
}
