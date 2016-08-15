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

import org.vaadin.gridfiledownloader.GridFileDownloader;

import com.vaadin.server.FontAwesome;

/**
 * {@link GridAction} that is dedicated for downloading.
 *
 * @see GridFileDownloader
 */
public class GridDownloadAction extends GridAction {

    /**
     * Constructor.
     *
     * @param iconPath
     *            path for a ThemeResource icon, required
     * @param description
     *            tooltip text, can be null
     */
    public GridDownloadAction(String iconPath, String description) {
        super(iconPath, description);
        markDownloadAction();
    }

    /**
     * Constructor.
     *
     * @param icon
     *            FontAwesome icon, required
     * @param description
     *            tooltip text, can be null
     */
    public GridDownloadAction(FontAwesome icon, String description) {
        super(icon, description);
        markDownloadAction();
    }

    /**
     * Constructor. If you want to give the action a tooltip, use
     * {@link #GridDownloadAction(String, String)} instead.
     *
     * @param iconPath
     *            path for a ThemeResource icon, required
     */
    public GridDownloadAction(String iconPath) {
        this(iconPath, null);
    }

    /**
     * Constructor. If you want to give the action a tooltip, use
     * {@link #GridDownloadAction(FontAwesome, String)} instead.
     *
     * @param icon
     *            FontAwesome icon, required
     */
    public GridDownloadAction(FontAwesome icon) {
        this(icon, null);
    }
}
