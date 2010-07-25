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
package org.jpos.ee.pm.core;

import java.util.List;

/**
 * Encapsulate a list of highlights
 * 
 * @author jpaoletti
 */
public class Highlights extends PMCoreObject{
    private static final String INSTANCE = "instance";
    private List<Highlight> highlights;

    /**
     * Return the index of the given hightlight in the list
     * @param highlight The highlight
     * @return The indec
     */
    public int indexOf(Highlight highlight){
        return getHighlights().indexOf(highlight);
    }

    /**
     * Return the first highlight that matches the given instance in the given field
     * of the given entity
     * 
     * @param entity The entity
     * @param field The field
     * @param instance The instance
     * @return The highlight
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
     * Return the first highlight that match in any value of this instance with
     * some of the highlights values.
     *
     * @param entity The entity
     * @param instance The instance
     * @return The Highlinght
     */
    public Highlight getHighlight(Entity entity, Object instance){
        for (Highlight highlight : highlights) {
            if(highlight.getScope().equals(INSTANCE)){
                for (Field field : entity.getOrderedFields()) {
                    if(match(instance, field, highlight))
                        return highlight;
                }
            }
        }
        return null;
    }

    /**
     * Indicate if the field of the given instance matches with the given
     * highlight
     *
     * @param instance The instance
     * @param field The field
     * @param highlight The highlight
     * @return true if match
     */
    protected boolean match(Object instance, Field field, Highlight highlight) {
        Object o = getPresentationManager().get(instance, field.getProperty());
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
