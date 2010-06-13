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
package org.jpos.ee.pm.validator;
import java.util.ArrayList;
import java.util.List;

import org.jpos.ee.pm.core.PMMessage;


/**The result of a validation.
 */
public class ValidationResult {
    /**True when the validation was successful*/
    private boolean successful;
    /**Error messages. The key is the field id and the value is the error message*/
    private List<PMMessage> messages;
    
    /**Default constructor*/
    public ValidationResult() {
        super();
        setMessages(new ArrayList<PMMessage>());
    }

    /**
     * @param successful the successful to set
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * @return the successful
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<PMMessage> messages) {
        this.messages = messages;
    }

    /**
     * @return the messages
     */
    public List<PMMessage> getMessages() {
        return messages;
    }

}