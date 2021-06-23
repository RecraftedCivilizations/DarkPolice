package com.github.recraftedcivilizations.darkpolice.parser

import com.github.darkvanityoflight.recraftedcore.configparser.ARecraftedConfigParser
import org.bukkit.configuration.file.FileConfiguration

class ConfigParser(config: FileConfiguration) : ARecraftedConfigParser(config) {
    val jobsThatCanUseHandcuffs: MutableSet<String> = emptySet<String>().toMutableSet()
    val jobsThatCanJail: MutableSet<String> = emptySet<String>().toMutableSet()
    var jailTime = 20

    override fun read() {

        jobsThatCanUseHandcuffs.addAll(config.getStringList(jobsThatCanUseHandcuffsName))
        jobsThatCanJail.addAll(config.getStringList(jobsThatCanJailName))

        jailTime = config.getInt(jailTimeName)
    }

    companion object{
        const val jobsThatCanUseHandcuffsName = "jobsThatCanUseHandcuffs"
        const val jobsThatCanJailName = "jobsThatCanUseHandcuffs"
        const val jailTimeName = "jailTime"
    }
}