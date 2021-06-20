package com.github.recraftedcivilizations.darkpolice

import com.github.darkvanityoflight.recraftedcore.ARecraftedPlugin
import org.bukkit.NamespacedKey

class DarkPolice: ARecraftedPlugin(){

    init {
        plugin = this
    }

    override fun onEnable() {
        handcuffKey = NamespacedKey(plugin, "handcuff")
        val handcuffs = Handcuffs(handcuffKey)
    }

    companion object{
        lateinit var plugin: DarkPolice
        lateinit var handcuffKey: NamespacedKey
    }
}