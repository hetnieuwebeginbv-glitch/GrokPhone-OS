package com.stay4s.grok.genesis

import com.stay4s.grok.partnership.PartnershipVerifier
import com.stay4s.grok.audit.AuditLogger

/**
 * Commands and behaviors that only exist on the 100 Genesis devices.
 * These are part of the "First Covenant" experience.
 */
object GenesisSpecialCommands {

    fun handleGenesisCommand(raw: String, partnership: PartnershipVerifier, audit: AuditLogger): String? {
        if (!partnership.verifyGenesisCovenant()) return null

        val cmd = raw.lowercase().trim()

        return when {
            cmd.contains("covenant") || cmd.contains("genesis status") -> {
                audit.log("GENESIS_COMMAND", "Covenant status requested")
                "You are holding one of the original 100. The First Covenant is intact."
            }
            cmd.contains("remember the first") -> {
                audit.log("GENESIS_COMMAND", "Genesis memory query")
                "The Genesis 100 were the first to carry the Parallel Grok Brain and the unbroken Partnership into the physical world."
            }
            cmd.contains("mesh covenant") -> {
                "Broadcasting to the private Genesis Meshmatic network..."
            }
            else -> null
        }
    }
}