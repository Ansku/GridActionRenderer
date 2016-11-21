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
package org.vaadin.anna.gridactionrenderer;

import java.util.List;

import org.vaadin.anna.gridactionrenderer.GridActionRenderer.GridActionClickListener;
import org.vaadin.anna.gridactionrenderer.client.ActionGridConnector;

import com.vaadin.client.connectors.GridConnector;
import com.vaadin.ui.Grid;

/**
 * Custom Grid for displaying GridActions with action-specific tooltips.
 *
 * If you want to use GridActions with all Grids and don't care about the
 * download functionality, extend {@link ActionGridConnector} to connect to
 * {@link com.vaadin.ui.Grid} instead. Note that such solution might cause
 * problems when used in combination with other add-ons that extend
 * {@link GridConnector} -- in such cases you may need to extend that add-on's
 * Grid connector class instead and simply copy over the contents of the
 * ActionGridConnector.
 */
public abstract class ActionGrid extends Grid implements GridActionClickListener {

	private GridActionRenderer actionRenderer;

	/**
	 * Constructor.
	 *
	 * @param actions
	 *            all the actions that can be displayed by the default action
	 *            renderer
	 */
	public ActionGrid(List<GridAction> actions) {
		super();
		this.actionRenderer = createGridActionRenderer(actions);
	}

	/**
	 * Returns the default action renderer.
	 *
	 * @return action renderer
	 */
	public GridActionRenderer getGridActionRenderer() {
		return this.actionRenderer;
	}

	/**
	 * Creates the default action renderer.
	 *
	 * @param actions
	 *            all the actions that can be displayed by the default action
	 *            renderer
	 * @return action renderer
	 */
	protected GridActionRenderer createGridActionRenderer(List<GridAction> actions) {
		GridActionRenderer actionRenderer = new GridActionRenderer(actions);
		actionRenderer.addActionClickListener(this);
		return actionRenderer;
	}
}