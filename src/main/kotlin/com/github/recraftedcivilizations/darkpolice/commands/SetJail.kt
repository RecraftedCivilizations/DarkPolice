package com.github.recraftedcivilizations.darkpolice.commands

import com.github.recraftedcivilizations.darkpolice.parser.dataparser.IParseData
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetJail(private val dataParser: IParseData): CommandExecutor {
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
        if (sender !is Player){ sender.sendMessage("Console monkey!!"); return true }

        dataParser.setPrisonLocation(sender.location)
        return true
    }
}