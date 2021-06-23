package com.github.recraftedcivilizations.darkpolice

import com.github.darkvanityoflight.recraftedcore.ARecraftedPlugin
import com.github.recraftedcivilizations.darkpolice.commands.SendToJail
import com.github.recraftedcivilizations.darkpolice.commands.Unarrest
import com.github.recraftedcivilizations.darkpolice.parser.ConfigParser
import com.github.recraftedcivilizations.darkpolice.parser.dataparser.YMLDataSource
import org.bukkit.NamespacedKey

class DarkPolice: ARecraftedPlugin(){

    init {
        plugin = this
    }

    override fun onEnable() {
        val dataParser = YMLDataSource(dataFolder.absolutePath)
        val configParser = ConfigParser(config)

        handcuffKey = NamespacedKey(plugin, "handcuff")

        val handcuffs = Handcuffs(handcuffKey)
        val jail = Jail(dataParser, configParser, this)

        plugin.getCommand("unarrest")?.setExecutor(Unarrest(handcuffs, configParser))
        plugin.getCommand("jail")?.setExecutor(SendToJail(configParser, handcuffs, jail))

    }

    companion object{
        lateinit var plugin: DarkPolice
        lateinit var handcuffKey: NamespacedKey
    }
}