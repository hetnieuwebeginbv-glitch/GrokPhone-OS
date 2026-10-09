package ai.stay4safe.guardian.ui.screens

import ai.stay4safe.guardian.model.GuardianSettings
import ai.stay4safe.guardian.model.ThreatLevel
import ai.stay4safe.guardian.service.GuardianState
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ═══════════════════════════════════════════════════════════
// STAY4S GUARDIAN — DESIGN TOKENS
// Cybernetic Noir: Diep zwart + Elektrisch Teal + Nood Rood
// ═══════════════════════════════════════════════════════════

val GuardianBlack = Color(0xFF0A0A0F)
val GuardianDark = Color(0xFF12121A)
val GuardianCard = Color(0xFF1A1A26)
val GuardianTeal = Color(0xFF00D4B4)
val GuardianTealDim = Color(0xFF007A68)
val GuardianRed = Color(0xFFFF3B3B)
val GuardianAmber = Color(0xFFFFB800)
val GuardianGreen = Color(0xFF00FF88)
val GuardianText = Color(0xFFE8E8F0)
val GuardianTextDim = Color(0xFF8888AA)

@Composable
fun MainScreen(
    guardianState: GuardianState = GuardianState.ACTIVE,
    threatLevel: ThreatLevel = ThreatLevel.NONE,
    settings: GuardianSettings = GuardianSettings(),
    onFastbutton: () -> Unit = {},
    onEmergency: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GuardianBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            GuardianHeader()

            Spacer(modifier = Modifier.height(24.dp))

            // Guardian Ring — centrale status indicator
            GuardianRing(
                state = guardianState,
                threatLevel = threatLevel
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Threat Level Banner
            if (threatLevel != ThreatLevel.NONE) {
                ThreatBanner(level = threatLevel)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Vier beschermingslagen
            ProtectionLayersGrid(settings = settings)

            Spacer(modifier = Modifier.height(24.dp))

            // Fastbutton
            FastbuttonWidget(onClick = onFastbutton)

            Spacer(modifier = Modifier.height(16.dp))

            // Noodknop
            EmergencyButton(onClick = onEmergency)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun GuardianHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Stay4S Guardian",
                color = GuardianTeal,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Ai Manus Phone — Stay4Safe Ai",
                color = GuardianTextDim,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }

        // Status badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(GuardianTeal.copy(alpha = 0.15f))
                .border(1.dp, GuardianTeal.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "● ACTIEF",
                color = GuardianTeal,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
private fun GuardianRing(
    state: GuardianState,
    threatLevel: ThreatLevel
) {
    val infiniteTransition = rememberInfiniteTransition(label = "guardian_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val ringColor = when (threatLevel) {
        ThreatLevel.NONE -> GuardianTeal
        ThreatLevel.LOW -> GuardianAmber
        ThreatLevel.MEDIUM -> GuardianAmber
        ThreatLevel.HIGH -> GuardianRed
        ThreatLevel.CRITICAL -> GuardianRed
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {
        // Outer glow ring
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            ringColor.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Main ring
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .border(2.dp, ringColor.copy(alpha = 0.6f), CircleShape)
                .background(GuardianDark),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Guardian",
                    tint = ringColor,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (state) {
                        GuardianState.ACTIVE -> "BESCHERMD"
                        GuardianState.MONITORING -> "MONITOREN"
                        GuardianState.ALERT -> "ALERT"
                        GuardianState.EMERGENCY -> "NOODGEVAL"
                        GuardianState.USER_RESPONDED -> "OK"
                        GuardianState.PAUSED -> "GEPAUZEERD"
                    },
                    color = ringColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

@Composable
private fun ThreatBanner(level: ThreatLevel) {
    val color = when (level) {
        ThreatLevel.LOW -> GuardianAmber
        ThreatLevel.MEDIUM -> GuardianAmber
        ThreatLevel.HIGH -> GuardianRed
        ThreatLevel.CRITICAL -> GuardianRed
        ThreatLevel.NONE -> GuardianTeal
    }

    val message = when (level) {
        ThreatLevel.LOW -> "Licht verhoogd risico gedetecteerd"
        ThreatLevel.MEDIUM -> "Verdachte activiteit gedetecteerd"
        ThreatLevel.HIGH -> "HOOG RISICO — Actie vereist"
        ThreatLevel.CRITICAL -> "KRITIEK — Noodprotocol actief"
        ThreatLevel.NONE -> ""
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = message,
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ProtectionLayersGrid(settings: GuardianSettings) {
    Text(
        text = "BESCHERMINGSLAGEN",
        color = GuardianTextDim,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 2.sp,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ProtectionCard(
                icon = Icons.Default.FavoriteBorder,
                title = "SAFETY",
                subtitle = "Valdetectie actief",
                isActive = settings.fallDetectionEnabled,
                modifier = Modifier.weight(1f)
            )
            ProtectionCard(
                icon = Icons.Default.Security,
                title = "SECURITY",
                subtitle = "Scam Defender actief",
                isActive = settings.scamCallDefenderEnabled,
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ProtectionCard(
                icon = Icons.Default.Lock,
                title = "PRIVACY",
                subtitle = "Sensor monitor actief",
                isActive = settings.privacyMonitorEnabled,
                modifier = Modifier.weight(1f)
            )
            ProtectionCard(
                icon = Icons.Default.Support,
                title = "SUPPORT",
                subtitle = "AI Assistent gereed",
                isActive = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ProtectionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (isActive) GuardianTeal else GuardianTextDim

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(GuardianCard)
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Column {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = color,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = GuardianTextDim,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun FastbuttonWidget(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GuardianTeal,
            contentColor = GuardianBlack
        )
    ) {
        Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "FASTBUTTON",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }
}

@Composable
private fun EmergencyButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, GuardianRed.copy(alpha = 0.6f)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = GuardianRed
        )
    ) {
        Icon(
            imageVector = Icons.Default.Emergency,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "NOODPROTOCOL",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.sp
        )
    }
}
