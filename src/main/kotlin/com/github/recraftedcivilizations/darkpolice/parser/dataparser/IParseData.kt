package com.github.recraftedcivilizations.darkpolice.parser.dataparser

import org.bukkit.Location

/**
 * @author DarkVanityOfLight
 */

/**
 * Parse data to a storage and read from it
 */
interface IParseData {

    fun getPrisonLocation(): Location
    fun setPrisonLocation(location: Location)
}