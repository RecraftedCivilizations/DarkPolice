package com.github.recraftedcivilizations.darkpolice.commands

import com.github.recraftedcivilizations.darkcitizens.dPlayer.DPlayerManager
import com.github.recraftedcivilizations.darkpolice.Handcuffs
import com.github.recraftedcivilizations.darkpolice.Jail
import com.github.recraftedcivilizations.darkpolice.parser.ConfigParser
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SendToJail(private val dPlayerManager: DPlayerManager, private val configParser: ConfigParser, private val handcuffs: Handcuffs, private val jail: Jail): CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br></br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player){ sender.sendMessage("You are a mf console, not the police"); return true }

        val job = dPlayerManager.getDPlayer(sender)?.job

        if (job?.toLowerCase() in configParser.jobsThatCanJail){
            var numberOfCuffedPlayer: Int = 0
            var oneCuffed: Player? = null
            for ((cuffed, whoCuffed) in handcuffs.cuffedPlayers().entries){
                if (whoCuffed == sender){
                    numberOfCuffedPlayer++
                    oneCuffed = cuffed
                }
            }

            when (numberOfCuffedPlayer) {
                0 -> {
                    sender.sendMessage("${ChatColor.RED}You don't have any players with cuffs")
                }
                1 -> {
                    jail.jail(oneCuffed!!)
                }
                else -> {
                    for ((cuffed, whoCuffed) in handcuffs.cuffedPlayers().entries){
                        if (whoCuffed == sender && cuffed.name in args){
                            jail.jail(cuffed)
                            return true
                        }
                    }

                    sender.sendMessage("${ChatColor.RED}Please specify the player you want to send to jail!")
                    return false

                }
            }

        }
        return true
    }


    }
}