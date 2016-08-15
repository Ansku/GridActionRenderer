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

import java.util.logging.Logger;

import org.vaadin.anna.gridactionrenderer.client.GridActionRendererState.GridAction;
import org.vaadin.gridfiledownloader.client.GridFileDownloaderConnector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.connectors.AbstractRendererConnector;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

/**
 * Connector class for {@link GridActionRenderer}.
 */
@Connect(org.vaadin.anna.gridactionrenderer.GridActionRenderer.class)
public class GridActionRendererConnector extends
        AbstractRendererConnector<String> {

    private Logger logger = Logger.getLogger(GridActionRenderer.class
            .getSimpleName());

    public static final String TOOLTIP = "grid-action-renderer-tooltip";

    @Override
    protected Renderer<String> createRenderer() {
        return new GridActionRenderer(this);
    }

    @Override
    public GridActionRenderer getRenderer() {
        return (GridActionRenderer) super.getRenderer();
    }

    @Override
    public GridActionRendererState getState() {
        return (GridActionRendererState) super.getState();
    }

    /**
     * Sends RPC call to the server-side about the clicked GridAction and row.
     * If the clicked action was a download action, triggers a remote click to
     * the given GridFileDownloader as well.
     *
     * @param event
     *            triggering click event
     * @param gridAction
     *            triggered action
     * @param rowIndex
     *            index of the row which the action belongs to
     * @param columnIndex
     *            index of the column which the action belongs to
     */
    public void handleClick(ClickEvent event, GridAction gridAction,
            int columnIndex, int rowIndex) {
        MouseEventDetails mouseDetails = MouseEventDetailsBuilder
                .buildMouseEventDetails(event.getNativeEvent());
        getRpcProxy(GridActionClickRpc.class).click(rowIndex,
                gridAction.actionKey, mouseDetails);

        if (gridAction.download) {
            if (getState().fileDownloader != null) {
                ((GridFileDownloaderConnector) getState().fileDownloader)
                        .remoteClick(columnIndex, rowIndex);
            } else {
                logger.severe("No file downloader set!");
            }
        }
    }
}
