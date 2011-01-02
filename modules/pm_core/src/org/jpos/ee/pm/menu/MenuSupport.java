/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.menu;

import java.util.List;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;

/**A helper class to get the associated menu of a user. It builds the full menu 
 * and makes a rebuild without the options the user has not permission to see. 
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class MenuSupport {

    /**Builds the menu for the user.
     * @param permissions List of permissions
     * @return The filtered menu associated to the permissions of the user.
     * @throws PMException
     * */
    public static Menu getMenu(List<String> permissions) throws PMException {
        try {
            MenuBuilder mb = new MenuBuilder(PresentationManager.getPm().getMenu());
            Menu menu = cleanWithoutPerms(mb.getMenu(), permissions);
            return menu;
        } catch (Exception e) {
            throw new PMException("pm_core.cant.load.menu");
        }
    }

    private static Menu cleanWithoutPerms(Menu menu, List<String> permissions) {
        if (menu.getPermission() == null || menu.getPermission().trim().compareTo("") == 0 || permissions.contains(menu.getPermission())) {
            if (menu instanceof MenuItem) {
                return menu;
            } else {
                MenuList ml = new MenuList();
                ml.setText(menu.getText());
                ml.setPermission(menu.getPermission());
                ml.setParent(menu.getParent());
                for (Menu m : ((MenuList) menu).getSubmenus()) {
                    Menu m2 = cleanWithoutPerms(m, permissions);
                    if (m2 != null) {
                        ml.add(m2);
                    }
                }
                return ml;
            }
        } else {
            return null;
        }
    }
}
