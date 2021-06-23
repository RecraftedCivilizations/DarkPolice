package com.github.recraftedcivilizations.darkpolice.parser.dataparser

import org.bukkit.Location

/**
 * @author DarkVanityOfLight
 */

/**
 * Parse data to a storage and read from it
 */
interface IParseData {

    /**
     * Get the location of the current prison
     * @return The location of the prison
     */
    fun getPrisonLocation(): Location

    /**
     * Set the location of the prison
     * @param location The location to set the prison to
     */
    fun setPrisonLocation(location: Location)
}