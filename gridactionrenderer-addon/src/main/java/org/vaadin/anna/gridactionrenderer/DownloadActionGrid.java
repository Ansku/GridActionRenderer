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

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.anna.gridactionrenderer.GridActionRenderer.GridActionClickEvent;
import org.vaadin.gridfiledownloader.GridFileDownloader;
import org.vaadin.gridfiledownloader.GridFileDownloader.GridStreamResource;

/**
 * Custom Grid for displaying GridActions and GridDoanloadActions with
 * action-specific tooltips.
 *
 * See {@link ActionGrid} for further details.
 */
public abstract class DownloadActionGrid extends ActionGrid {

    private int downloadTimeout = 3000;
    private Object downloadItemId = null;
    private Map<Object, DownloadInfo> downloadActionMap = new HashMap<Object, DownloadInfo>();
    private GridFileDownloader fileDownloader = null;

    /**
     * Constructor.
     *
     * @param actions
     *            all the actions that can be displayed by the default action
     *            renderer
     */
    public DownloadActionGrid(List<GridAction> actions) {
        super(actions);
        fileDownloader = createGridFileDownloader();
        getGridActionRenderer().setFileDownloader(getGridFileDownloader());
    }

    /**
     * Called when a rendered grid action icon gets clicked.
     * <p>
     * NOTE: when overriding this method, you must call super.click(event) for
     * download actions to work.
     */
    @Override
    public void click(GridActionClickEvent event) {
        if (event.getAction() instanceof GridDownloadAction) {
            downloadActionMap.put(event.getItemId(), new DownloadInfo(
                    (GridDownloadAction) event.getAction(), new Date()));
        }
    }

    /**
     * Returns the default file downloader.
     *
     * @return file downloader
     */
    public GridFileDownloader getGridFileDownloader() {
        return fileDownloader;
    }

    /**
     * Creates the default file downloader.
     *
     * @return file downloader
     */
    private GridFileDownloader createGridFileDownloader() {
        return new GridFileDownloader(this, "report", createStreamResource()) {
            @Override
            public void recalculateDownloadColumn() {
                // download column not needed, downloads triggered by external
                // calls from the action renderer
                getState().downloadColumnIndex = -1;
            }

            @Override
            protected void setRowId(Object rowId) {
                // overridden to gain access to id reference
                downloadItemId = rowId;
                super.setRowId(rowId);
            }

            @Override
            protected boolean waitForRPC() {
                // wait for an RPC calls from both the GridFileDownloader
                // extension and the GridActionRenderer
                Date startDate = new Date();
                // GridFileDownloader default check
                if (super.waitForRPC()) {
                    // additional GridActionRenderer check
                    while (new Date().getTime() - startDate.getTime() < getDownloadTimeout()) {
                        if (checkDownloadMap(startDate)) {
                            return true;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ignore) {
                        }
                    }
                }
                return checkDownloadMap(startDate);
            }

            private boolean checkDownloadMap(Date startDate) {
                boolean result = false;
                DownloadInfo downloadInfo = downloadActionMap
                        .get(downloadItemId);
                if (downloadInfo != null
                        && (downloadInfo.date.getTime() - startDate.getTime() < getDownloadTimeout())) {
                    // no timeout, download can proceed
                    result = true;
                }

                // go through all the unresolved actions from the downloadAction
                // map and remove those that have reached their timeout, unless
                // the key matches the current downloadItemId (will get removed
                // automatically once processing has finished).
                for (Entry<Object, DownloadInfo> entry : new HashSet<Entry<Object, DownloadInfo>>(
                        downloadActionMap.entrySet())) {
                    if (!entry.getKey().equals(downloadItemId)
                            && entry.getValue().date.getTime()
                                    - startDate.getTime() >= getDownloadTimeout()) {
                        downloadActionMap.remove(entry.getKey());
                    }
                }

                return result;
            };

            @Override
            protected void markProcessed() {
                downloadActionMap.remove(downloadItemId);
                super.markProcessed();
            }
        };
    }

    /**
     * Gets the timeout in milliseconds for triggering the download. If both RPC
     * calls haven't arrived within this time (in milliseconds), download won't
     * get triggered.
     *
     * @return timeout in milliseconds
     */
    public long getDownloadTimeout() {
        return downloadTimeout;
    }

    /**
     * Sets the timeout in milliseconds for triggering the download. If both RPC
     * calls haven't arrived within this time (in milliseconds), download won't
     * get triggered.
     *
     * @param downloadTimeout
     *            timeout in milliseconds
     */
    public void setDownloadTimeout(int downloadTimeout) {
        this.downloadTimeout = downloadTimeout;
    }

    /**
     * Creates a GridStreamResource for the triggered download.
     *
     * @return stream resource
     */
    public abstract GridStreamResource createStreamResource();

    /**
     * Returns the item id of the currently processed download action, or null
     * if one doesn't exist.
     *
     * @return item id of the download action
     */
    public Object getDownloadItemId() {
        return downloadItemId;
    }

    /**
     * Returns the currently processed download action, or null if one doesn't
     * exist.
     *
     * @return download action
     */
    public GridDownloadAction getDownloadAction() {
        DownloadInfo downloadInfo = downloadActionMap.get(downloadItemId);
        if (downloadInfo != null) {
            return downloadInfo.action;
        }
        return null;
    }

    /**
     * Helper class for downloadActionMap
     */
    private static class DownloadInfo {
        private Date date;
        private GridDownloadAction action;

        public DownloadInfo(GridDownloadAction action, Date date) {
            this.action = action;
            this.date = date;
        }
    }
}
