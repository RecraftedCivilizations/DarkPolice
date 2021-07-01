package com.github.recraftedcivilizations.darkpolice

import com.github.darkvanityoflight.recraftedcore.utils.tagutils.TagManager
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import com.github.darkvanityoflight.recraftedcore.utils.tagutils.persistentdatatypes.BooleanItemTagType
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Handcuffs(private val handcuffKey: NamespacedKey = DarkPolice.handcuffKey): Listener {
    private val slowEffect = PotionEffect(PotionEffectType.SLOW, Int.MAX_VALUE, 15, false, false)
    // Key is the handcuffed player value is the player who handcuffed him
    private val handcuffedPlayers = emptyMap<Player, Player>().toMutableMap()

    @EventHandler
    fun onPlayerHitOtherPlayer(e: EntityDamageByEntityEvent){
        val attacked = e.entity
        val attacker = e.damager

        // Check that we have two players
        if (attacker !is Player || attacked !is Player){ return }
        // Check that we are damaged by a normal attack
        if (e.cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK){ return }

        // Get the item the attack came from
        val damagerItem = attacker.inventory.itemInMainHand
        // Create a tag manager for the attacker item
        val tagManager = TagManager("darkPolice", damagerItem)

        // Check if the item is a handcuff
        val isHandcuff = tagManager.getOther(handcuffKey, BooleanItemTagType() as PersistentDataType<Any, Any>) as Boolean

        if (isHandcuff){
            handcuffPlayer(attacked, attacker)
        }
    }

    private fun handcuffPlayer(cuffed: Player, whoCuffed: Player){
        cuffed.addPotionEffect(slowEffect)
        handcuffedPlayers[cuffed] = whoCuffed
    }

    fun isHandcuffed(player: Player): Boolean{
        return player in handcuffedPlayers
    }

    fun unCuffPlayer(player: Player){
        player.removePotionEffect(PotionEffectType.SLOW)
        handcuffedPlayers.remove(player)
    }

    fun cuffedPlayers(): Map<Player, Player>{
        return handcuffedPlayers
    }

    @EventHandler
    fun cancelCommands(e: PlayerCommandPreprocessEvent){
        if (e.player in handcuffedPlayers.keys){
            e.isCancelled = true
        }
    }

}


