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

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.Action;
import com.vaadin.server.FontIcon;
import com.vaadin.server.ThemeResource;

/**
 * Grid-specific Action with a specific icon and description that is displayed
 * in a tooltip when used together with {@link GridActionRenderer} and
 * {@link ActionGrid}. Can be given given its own style names.
 * <p>
 * NOTE: there is no option for row-specific styling at the moment.
 */
public class GridAction extends Action {

    private String description;
    private boolean download = false;
    private List<String> styleNames = new ArrayList<String>();

    /**
     * Constructor.
     *
     * @param iconPath
     *            path for a ThemeResource icon, required
     * @param description
     *            tooltip text, can be null
     */
    public GridAction(String iconPath, String description) {
        super(null);
        assert iconPath != null;

        setIcon(new ThemeResource(iconPath));
        this.description = description;
    }

    /**
     * Constructor.
     *
     * @param icon
     *            FontIcon icon, required
     * @param description
     *            tooltip text, can be null
     */
    public GridAction(FontIcon icon, String description) {
        super(null);
        assert icon != null;

        setIcon(icon);
        this.description = description;
    }

    /**
     * Constructor. If you want to give the action a tooltip, use
     * {@link #GridAction(String, String)} instead.
     *
     * @param iconPath
     *            path for a ThemeResource icon, required
     */
    public GridAction(String iconPath) {
        this(iconPath, null);
    }

    /**
     * Constructor. If you want to give the action a tooltip, use
     * {@link #GridAction(FontIcon, String)} instead.
     *
     * @param icon
     *            FontIcon icon, required
     */
    public GridAction(FontIcon icon) {
        this(icon, null);
    }

    /**
     * Returns the description that is displayed as a tooltip for the action
     * icon.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description that is displayed as a tooltip for the action icon.
     *
     * @param description
     *            tooltip text, can be null
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Marks the action as a download action.
     * <p>
     * NOTE: this method shouldn't be called except from
     * {@link GridDownloadAction}.
     */
    protected void markDownloadAction() {
        download = true;
    }

    /**
     * Returns {@code true} if the action is a download action, {@code false}
     * otherwise.
     *
     * @return {@code true} if download action, {@code false} otherwise.
     */
    public boolean isDownloadAction() {
        return download;
    }

    /**
     * Adds a custom style name for the action icon.
     * <p>
     * NOTE: the styles can't be changed on the fly
     *
     * @param styleName
     *            custom style name
     */
    public void addStyleName(String styleName) {
        styleNames.add(styleName);
    }

    /**
     * Returns the custom style names that have been added for the action icon.
     *
     * @return list of custom style names
     */
    public List<String> getStyleNames() {
        return new ArrayList<String>(styleNames);
    }
}