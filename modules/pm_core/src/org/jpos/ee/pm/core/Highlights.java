/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
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

public class Highlights {
    public static final String INSTANCE = "instance";
    /**A list of highlights.*/
    private List<Highlight> highlights;

    public int indexOf(Highlight highlight){
        return getHighlights().indexOf(highlight);
    }

    /**
     * @return the first highlight that matches the given value
     */
    public Highlight getHighlight(Entity entity, Field field, Object instance){
        if(field==null) return getHighlight(entity, instance);
        for (Highlight highlight : highlights) {
            if(!highlight.getScope().equals(INSTANCE)){
                if(match(instance, field, highlight))
                    return highlight;
            }
        }
        return null;
    }

    /**
     * @return the first highlight that match in any value of this instance with
     * some of the highlights values.
     */
    public Highlight getHighlight(Entity entity, Object instance){
        for (Highlight highlight : highlights) {
            if(highlight.getScope().equals(INSTANCE)){
                for (Field field : entity.getFields()) {
                    if(match(instance, field, highlight))
                        return highlight;
                }
            }
        }
        return null;
    }

    protected boolean match(Object instance, Field field, Highlight highlight) {
        Object o = EntitySupport.get(instance, field.getId());
        if (o != null && o.toString().equals(highlight.getValue()) && highlight.getField().equals(field.getId())) {
            return true;
        }
        return false;
    }

    /**
     * @param highlights the highlights to set
     */
    public void setHighlights(List<Highlight> highlights) {
        this.highlights = highlights;
    }

    /**
     * @return the highlights
     */
    public List<Highlight> getHighlights() {
        return highlights;
    }
}
