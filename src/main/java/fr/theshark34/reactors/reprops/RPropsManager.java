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

import fr.theshark34.reactors.reprops.props.RPropList;
import fr.theshark34.reactors.reprops.props.RPropNumber;
import fr.theshark34.reactors.reprops.props.RPropString;

import java.util.HashMap;
import java.util.Map;

/**
 * The RProps Manager
 *
 * <p>
 *    The RProps Manager manage all different types of
 *    RProp.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public final class RPropsManager {

    /**
     * The list of all different RProps types, with a byte to identify each
     */
    private static HashMap<Byte, Class<? extends RProp>> propTypes = new HashMap<Byte, Class<? extends RProp>>();

    /**
     * Register the pre-defined RProp types, like the String, the Number, etc...
     */
    static void registerPredefinedRPropTypes() {
        registerPropType(RPropString.IDENTIFIER, RPropString.class);
        registerPropType(RPropNumber.IDENTIFIER, RPropNumber.class);
        registerPropType(RPropList.IDENTIFIER, RPropList.class);
    }

    /**
     * Register a type of RProp
     *
     * @param propTypeIdentifier
     *            The identifier of it (each RProp type need to have one to identify each)
     * @param propType
     *            The class of the RProp type
     */
    public static void registerPropType(Byte propTypeIdentifier, Class<? extends RProp> propType) {
        propTypes.put(propTypeIdentifier, propType);
    }

    /**
     * Return a the prop type corresponding to the given identifier
     *
     * @param propTypeIdentifier
     *            The identifier of the prop type
     * @return The founded prop type
     */
    public static Class<? extends RProp> getPropTypeFromIdentifier(Byte propTypeIdentifier) {
        return propTypes.get(propTypeIdentifier);
    }

    public static Byte getIdentifierFromPropType(Class<? extends RProp> propType) {
        for(Map.Entry<Byte, Class<? extends RProp>> entry : propTypes.entrySet())
            if(entry.getValue().equals(propType))
                return entry.getKey();

        return null;
    }

}