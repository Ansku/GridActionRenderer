# GridActionRenderer Add-on for Vaadin 7

GridActionRenderer is an add-on for Vaadin 7. It's a renderer for adding an action column to Grid with separate tooltips for each action.

There are two extended Grid versions included for ease of use: ActionGrid for regular actions and DownloadActionGrid for when one or more of the actions should trigger a download. The download functionality uses [GridFileDownloader add-on](https://vaadin.com/directory#!addon/gridfiledownloader).

It's possible to use the renderer with a vanilla Grid or some other extended Grid version, but in those cases it's still necessary to copy over the functionality from the aforementioned classes, as well as the ActionGridConnector (or tooltips won't work). Neither action handling nor download triggering are trivial additions to Grid, so take care and test extensively if you make modifications to the default behaviour.

For an example of how to use ActionGrid, see [src/main/java/org/vaadin/anna/gridactionrenderer/demo/MyActionGrid.java](https://github.com/Ansku/GridActionRenderer/blob/master/gridactionrenderer-demo/src/main/java/org/vaadin/anna/gridactionrenderer/demo/MyActionGrid.java)

For an example of how to use DownloadActionGrid, see [src/main/java/org/vaadin/anna/gridactionrenderer/demo/MyDownloadActionGrid.java](https://github.com/Ansku/GridActionRenderer/blob/master/gridactionrenderer-demo/src/main/java/org/vaadin/anna/gridactionrenderer/demo/MyDownloadActionGrid.java)
