/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2008 Alejandro P. Revilla
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

package org.jpos.ee.pm.core;

import java.util.List;

public class Panel {
    Entity entity;
    String action;
    List<?> list;
    List<?> revisions;
    public Panel (Entity entity, String action, List<?> list) {
        super();
        setEntity (entity);
        setAction (action);
        setList (list);
    }
    public Panel (Entity entity, String action, List<?> list, List<?> revisions) {
        super();
        setEntity (entity);
        setAction (action);
        setList (list);
        setRevisions (revisions);
    }
    public void setEntity (Entity entity) {
        this.entity = entity;
    }
    public Entity getEntity () {
        return entity;
    }
    public void setAction (String action) {
        this.action = action;
    }
    public String getAction () {
        return action;
    }
    public void setList (List<?> list) {
        this.list = list;
    }
    public List<?> getList () {
        return list;
    }
    public Object getItem () {
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }
    public void setRevisions (List<?> revisions) {
        this.revisions = revisions;
    }
    public List<?> getRevisions () {
        return revisions;
    }
}

