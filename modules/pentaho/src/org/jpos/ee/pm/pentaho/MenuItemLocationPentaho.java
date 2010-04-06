package org.jpos.ee.pm.pentaho;

import org.jpos.ee.pm.menu.MenuItem;
import org.jpos.ee.pm.struts.MenuItemLocationStruts;

public class MenuItemLocationPentaho extends MenuItemLocationStruts {

    protected String buildLink(MenuItem item, Object... params) {
        String link = PentahoQBean.getService().getUrl()+item.getLocation_value();
        return link;
    }
}