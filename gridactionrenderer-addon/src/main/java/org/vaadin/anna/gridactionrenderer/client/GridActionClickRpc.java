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

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

/**
 * Client-to-server RPC interface for GridAction clicks.
 */
public interface GridActionClickRpc extends ServerRpc {

    /**
     * Called when a GridAction icon has been clicked.
     *
     * @param rowIndex
     *            index of clicked row
     * @param actionKey
     *            identifier key for the triggered GridAction
     * @param mouseDetails
     *            mouse-related data at the time of the event
     */
    void click(int rowIndex, String actionKey, MouseEventDetails mouseDetails);

}
