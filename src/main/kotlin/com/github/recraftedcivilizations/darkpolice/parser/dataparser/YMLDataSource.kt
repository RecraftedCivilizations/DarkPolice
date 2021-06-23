package com.github.recraftedcivilizations.darkpolice.parser.dataparser

import com.github.darkvanityoflight.recraftedcore.api.BukkitWrapper
import org.bukkit.Location
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class YMLDataSource(var filePath: String, private val bukkitWrapper: BukkitWrapper = BukkitWrapper()): IParseData {
    private val dataFile : YamlConfiguration = YamlConfiguration()

    init {
        filePath = if (filePath.endsWith("/")){
            "$filePath${fileName}"
        }else{
            "$filePath/${fileName}"
        }

        val file = File(filePath)

        if (file.exists()){
            dataFile.load(file)
            bukkitWrapper.info("Found old data file, loading data")
        }else{
            file.createNewFile()
        }
    }

    /**
     * Get the location of the current prison
     * @return The location of the prison
     */
    override fun getPrisonLocation(): Location? {
        load()

        return dataFile.getLocation(prisonLocationName)

    }

    /**
     * Set the location of the prison
     * @param location The location to set the prison to
     */
    override fun setPrisonLocation(location: Location) {
        load()

        dataFile.set(prisonLocationName, location)
        save()
    }

    private fun load(){
        dataFile.load(filePath)
    }

    private fun save(){
        dataFile.save(filePath)
    }

    companion object{
        const val fileName = "data.yml"
        const val prisonLocationName = "prisonLocation"
    }
}