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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.anna.gridactionrenderer.client.GridActionClickRpc;
import org.vaadin.anna.gridactionrenderer.client.GridActionRendererState;
import org.vaadin.gridfiledownloader.GridFileDownloader;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.server.KeyMapper;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.AbstractRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.util.ReflectTools;

/**
 * Renderer for displaying a row of {@link GridAction} icons. The value of the
 * column must be a String that contains indexes of the visible action icons,
 * separated by comma, or -1 if all actions should be displayed. Order of the
 * indexes does not affect the order of the displayed actions, which is
 * determined by the list given as a constructor parameter.
 */
public class GridActionRenderer extends AbstractRenderer<String> {

    private KeyMapper<GridAction> actionKeyMapper = null;

    /**
     * Constructor. If the contents of the actions list given as a parameter are
     * changed after this constructor is called those changes won't be taken
     * into account. The visibility of each action can be determined by row (see
     * {@link GridActionRenderer} for further details).
     *
     * @param actions
     *            all the actions that are to be displayed by this renderer
     */
    public GridActionRenderer(final List<GridAction> actions) {
        super(String.class);

        actionKeyMapper = new KeyMapper<GridAction>();
        setActions(actions);

        registerRpc(new GridActionClickRpc() {
            @Override
            public void click(int rowIndex, String actionKey,
                    MouseEventDetails mouseDetails) {
                GridAction action = actionKeyMapper.get(actionKey);
                Object itemId = getParentGrid().getContainerDataSource()
                        .getIdByIndex(rowIndex);
                fireEvent(new GridActionClickEvent(getParentGrid(), itemId,
                        action, mouseDetails));
            }
        });
    }

    @Override
    protected GridActionRendererState getState() {
        return (GridActionRendererState) super.getState();
    }

    /**
     * Sets the {@link GridFileDownloader} that is used by
     * {@link GridDownloadAction}s.
     *
     * @param fileDownloader
     *            file downloader
     */
    public void setFileDownloader(GridFileDownloader fileDownloader) {
        getState().fileDownloader = fileDownloader;
    }

    /**
     * Sets all {@link GridActions} that can be displayed by this renderer. The
     * visibility of each action can be determined by row (see
     * {@link GridActionRenderer} for further details).
     *
     * @param gridActions
     *            list of GridActions that can be displayed
     */
    private void setActions(List<GridAction> gridActions) {
        ArrayList<GridActionRendererState.GridAction> stateActions = new ArrayList<GridActionRendererState.GridAction>();

        for (GridAction gridAction : gridActions) {
            String key = actionKeyMapper.key(gridAction);
            setResource(key, gridAction.getIcon());

            GridActionRendererState.GridAction stateAction = new GridActionRendererState.GridAction();
            stateAction.actionKey = key;
            stateAction.description = gridAction.getDescription();
            if (gridAction.isDownloadAction()) {
                stateAction.download = true;
            }
            for (String styleName : gridAction.getStyleNames()) {
                stateAction.styleNames.add(styleName);
            }

            stateActions.add(stateAction);
        }

        getState().gridActions.clear();
        getState().gridActions.addAll(stateActions);
    }

    /**
     * Adds a {@link GridAction} click listener to this renderer. The listener
     * is invoked every time one of the rendered GridAction icons are clicked.
     *
     * @param listener
     *            the click listener to be added
     */
    public void addActionClickListener(GridActionClickListener listener) {
        addListener(GridActionClickEvent.class, listener,
                GridActionClickListener.CLICK_METHOD);
    }

    /**
     * Removes the given {@link GridAction} click listener from this renderer.
     *
     * @param listener
     *            the click listener to be removed
     */
    public void removeGridActionClickListener(GridActionClickListener listener) {
        removeListener(GridActionClickEvent.class, listener);
    }

    /**
     * An interface for listening to {@link GridActionClickEvent GridAction
     * click events} that occur when user clicks a corresponding action icon.
     * <p>
     * See
     * {@link com.vaadin.ui.renderers.ClickableRenderer#addClickListener(RendererClickListener)}
     * for more information.
     */
    public interface GridActionClickListener extends ConnectorEventListener {

        static final Method CLICK_METHOD = ReflectTools.findMethod(
                GridActionClickListener.class, "click",
                GridActionClickEvent.class);

        /**
         * Called when a rendered grid action icon gets clicked.
         *
         * @param event
         *            data about the click
         */
        void click(GridActionClickEvent event);
    }

    /**
     * Class for holding information about a mouse click event that triggers a
     * {@link GridAction}. A GridActionClickEvent is fired when the user clicks
     * on a GridAction icon.
     * <p>
     * See {@link ClickEvent} for more information about the event data.
     */
    public static class GridActionClickEvent extends ClickEvent {
        private Object itemId;
        private GridAction action;

        protected GridActionClickEvent(Grid source, Object itemId,
                GridAction action, MouseEventDetails mouseEventDetails) {
            super(source, mouseEventDetails);

            this.itemId = itemId;
            this.action = action;
        }

        /**
         * Returns the itemId of the row where the action click event was
         * triggered.
         *
         * @return itemId of the clicked row
         */
        public Object getItemId() {
            return itemId;
        }

        /**
         * Returns the triggered {@link GridAction}.
         *
         * @return triggered action
         */
        public GridAction getAction() {
            return action;
        }
    }
}
