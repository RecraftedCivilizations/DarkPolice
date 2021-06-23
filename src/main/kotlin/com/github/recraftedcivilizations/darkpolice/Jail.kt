package com.github.recraftedcivilizations.darkpolice

import com.github.darkvanityoflight.recraftedcore.ARecraftedPlugin
import com.github.recraftedcivilizations.darkpolice.parser.ConfigParser
import com.github.recraftedcivilizations.darkpolice.parser.dataparser.IParseData
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class UnJail(private val jail: Jail, private val player: Player): BukkitRunnable() {
    override fun run() {
        jail.unJail(player)
    }

}

class Jail(private val dataParser: IParseData, private val configParser: ConfigParser, private val plugin: ARecraftedPlugin) {
    private val playersJailed = emptySet<Player>().toMutableSet()

    fun jail(player: Player){
        dataParser.getPrisonLocation()?.let { player.teleport(it) }
        UnJail(this, player).runTaskLater(plugin, configParser.jailTime*20L)
        playersJailed.add(player)
    }

    fun unJail(player: Player){
        player.teleport(player.world.spawnLocation)
        playersJailed.remove(player)
    }

    fun getJailedPlayers(): Set<Player>{
        return playersJailed
    }
}