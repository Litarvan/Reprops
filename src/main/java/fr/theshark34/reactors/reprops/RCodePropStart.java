/*
 * Copyright 2015 TheShark34
 *
 * This file is part of Reprops.

 * Reprops is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Reprops is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Reprops.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.theshark34.reactors.reprops;

import fr.theshark34.reactors.rebyte.RCodeAction;
import fr.theshark34.reactors.rebyte.Rebyte;
import fr.theshark34.reactors.rebyte.RebyteMalformedBytesException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The Reprops PROP START code
 *
 * <p>
 *    This code, is the first code to be read, that explain
 *    the start of a RProp.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class RCodePropStart extends RPCode implements RCodeAction {

    public RCodePropStart(final Reprops reprops) {
        super(reprops);
    }

    public byte getCode() {
        return RepropsCodes.PROP_START;
    }

    public RCodeAction getAction() {
        return this;
    }

    public void onCodeRead(Rebyte rebyte, byte code) throws RebyteMalformedBytesException {
        // The prop infos
        String propIdentifier;
        long propLength;
        byte propTypeIdentifier;
        byte[] prop;
        RProp finalProp;

        // Reading the infos
        propIdentifier = new String(rebyte.readUntil(RepropsCodes.PROP_TYPE_IDENTIFIER));
        propTypeIdentifier = rebyte.getNext();
        propLength = Reprops.fromByteArrayToLong(rebyte.readFor(8));
        prop = rebyte.readFor(propLength);

        // Decrypting the prop identifier
        propIdentifier = Reprops.decrypt(propIdentifier);

        // Getting the corresponding prop type
        Class<? extends RProp> propClass = RPropsManager.getPropTypeFromIdentifier(propTypeIdentifier);
        if(propClass == null)
            throw new RebyteMalformedBytesException("Unknown prop type read : " + propTypeIdentifier);

        // Instantiating the corresponding RProp class with the prop identifier
        try {
            // Getting all constructors of it
            Constructor<?> constructor = null;
            for(Constructor<?> c : propClass.getDeclaredConstructors())
                // If it's a constructor with one string argument
                if(c.getParameterTypes().length == 1 && c.getParameterTypes()[0].equals(String.class))
                    // Getting it
                    constructor = c;

            // Checking the constructor
            if(constructor == null)
                throw new IllegalStateException("Can't find the RProps string only constructor (identifier only constructor). Your RProp class need to have one (a private one is better)");

            // Setting it accessible, in the case of it is not private
            constructor.setAccessible(true);

            // Instantiating it
            finalProp = (RProp) constructor.newInstance(propIdentifier);
        } catch (IllegalAccessException e) {
            throw new RebyteMalformedBytesException("Can't init the prop : " + e);
        } catch (InstantiationException e) {
            throw new RebyteMalformedBytesException("Can't init the prop : " + e);
        } catch (InvocationTargetException e) {
            throw new RebyteMalformedBytesException("Can't init the prop : " + e);
        }

        // Setting the prop data to the read bytes
        finalProp.read(prop);

        // Adding the prop, to the Reprops object
        getReprops().addProp(finalProp);
    }

}
