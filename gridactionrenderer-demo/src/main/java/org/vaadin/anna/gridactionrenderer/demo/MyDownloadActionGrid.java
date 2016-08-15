package org.vaadin.anna.gridactionrenderer.demo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.anna.gridactionrenderer.DownloadActionGrid;
import org.vaadin.anna.gridactionrenderer.GridAction;
import org.vaadin.anna.gridactionrenderer.GridActionRenderer.GridActionClickEvent;
import org.vaadin.anna.gridactionrenderer.GridDownloadAction;
import org.vaadin.gridfiledownloader.GridFileDownloader.GridStreamResource;

import com.vaadin.data.Item;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class MyDownloadActionGrid extends DownloadActionGrid {

    @SuppressWarnings("unchecked")
    public MyDownloadActionGrid() {
        super(createActions());

        addStyleName("demogrid");
        addColumn("name");
        setCaption("Download Action Grid");

        Column column = addColumn("actions");
        column.setRenderer(getGridActionRenderer());

        for (int i = 0; i < 25; ++i) {
            Item item = getContainerDataSource().addItem(i);
            item.getItemProperty("name").setValue("Item" + i);
            item.getItemProperty("actions").setValue(i % 3 == 0 ? "1,2" : "-1");
        }
    }

    private static List<GridAction> createActions() {
        List<GridAction> actions = new ArrayList<GridAction>();
        actions.add(new GridAction(FontAwesome.USER, "user"));
        actions.add(new GridAction(FontAwesome.GEAR, "settings"));
        actions.add(new GridDownloadAction(FontAwesome.DOWNLOAD, "download"));
        return actions;
    }

    @Override
    public void click(GridActionClickEvent event) {
        super.click(event); // must be called

        if (event.getAction().isDownloadAction()) {
            return; // already handled in extended method
        }
        Item item = getContainerDataSource().getItem(event.getItemId());
        Notification.show(item.getItemProperty("name").getValue() + " - "
                + event.getAction().getDescription(), Type.ERROR_MESSAGE);
    }

    @Override
    public GridStreamResource createStreamResource() {
        return new GridStreamResource() {

            @Override
            public InputStream getStream() {
                // in real use-case you would find or create the file here based
                // on the downloadItemId and action (if there are more than one)
                int writeAtOnce = 1024 * 1024 * 1024;
                byte[] b = new byte[writeAtOnce];
                return new ByteArrayInputStream(b);
            }

            @Override
            public String getFilename() {
                // in real use-case you would find or create the file here based
                // on the downloadItemId and action (if there are more than one)
                GridDownloadAction downloadAction = getDownloadAction();
                Item item = getContainerDataSource().getItem(
                        getDownloadItemId());

                return "file_" + downloadAction.getDescription() + "_"
                        + item.getItemProperty("name").getValue() + ".txt";
            }
        };
    }

}
