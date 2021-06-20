package com.github.recraftedcivilizations.darkpolice

import com.github.darkvanityoflight.recraftedcore.utils.tagutils.TagManager
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import com.github.darkvanityoflight.recraftedcore.utils.tagutils.persistentdatatypes.BooleanItemTagType
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Handcuffs(private val handcuffKey: NamespacedKey = DarkPolice.handcuffKey): Listener {
    private val slowEffect = PotionEffect(PotionEffectType.SLOW, Int.MAX_VALUE, 15, false, false)
    private val handcuffedPlayers = emptySet<Player>().toMutableSet()

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
            handcuffPlayer(attacked)
        }
    }

    private fun handcuffPlayer(player: Player){
        player.addPotionEffect(slowEffect)
        handcuffedPlayers.add(player)
    }

    fun isHandcuffed(player: Player): Boolean{
        return player in handcuffedPlayers
    }

    fun unCuffPlayer(player: Player){
        player.removePotionEffect(PotionEffectType.SLOW)
        handcuffedPlayers.remove(player)
    }

}


