package ai.stay4safe.launcher.ui.home

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import java.text.SimpleDateFormat
import java.util.*

// ═══════════════════════════════════════════════════════════
// MANUS LAUNCHER — Design: Cybernetic Noir
// Zwart + Teal + Glassmorphism
// ═══════════════════════════════════════════════════════════

class HomeActivity : ComponentActivity() {

    private val appUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Refresh app list when apps are installed/removed
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Launcher-specifieke window flags
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            ManusLauncherTheme {
                ManusHomeScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
        registerReceiver(appUpdateReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(appUpdateReceiver)
    }
}

// ═══════════════════════════════════════════════════════════
// KLEUREN & THEMA
// ═══════════════════════════════════════════════════════════

val LauncherBlack = Color(0xFF080810)
val LauncherDark = Color(0xFF0F0F1A)
val LauncherCard = Color(0xFF1A1A2E)
val LauncherTeal = Color(0xFF00D4B4)
val LauncherTealGlow = Color(0xFF00D4B4).copy(alpha = 0.3f)
val LauncherText = Color(0xFFE8E8F0)
val LauncherTextDim = Color(0xFF6666AA)
val LauncherGlass = Color(0xFFFFFFFF).copy(alpha = 0.08f)
val LauncherGlassBorder = Color(0xFFFFFFFF).copy(alpha = 0.12f)

@Composable
fun ManusLauncherTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = LauncherBlack,
            surface = LauncherDark,
            primary = LauncherTeal,
            onPrimary = LauncherBlack,
            onBackground = LauncherText,
            onSurface = LauncherText
        ),
        content = content
    )
}

// ═══════════════════════════════════════════════════════════
// HOOFD HOME SCREEN
// ═══════════════════════════════════════════════════════════

@Composable
fun ManusHomeScreen() {
    val context = LocalContext.current
    var showAppDrawer by remember { mutableStateOf(false) }
    var currentPage by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LauncherBlack)
    ) {
        // Achtergrond grid-patroon (subtiel)
        CyberGridBackground()

        // Hoofd content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Status bar ruimte
            Spacer(modifier = Modifier.statusBarsPadding())

            // Klok & datum widget
            ClockWidget()

            Spacer(modifier = Modifier.height(16.dp))

            // Guardian status mini-widget
            GuardianStatusWidget()

            Spacer(modifier = Modifier.weight(1f))

            // Favoriete apps (dock)
            AppDock(context = context)

            // Navigatie balk ruimte
            Spacer(modifier = Modifier.navigationBarsPadding())
        }

        // App Drawer trigger (swipe omhoog)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount < -50) {
                            showAppDrawer = true
                        }
                    }
                }
        )

        // App Drawer overlay
        AnimatedVisibility(
            visible = showAppDrawer,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            AppDrawerOverlay(
                onDismiss = { showAppDrawer = false },
                context = context
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════
// ACHTERGROND GRID
// ═══════════════════════════════════════════════════════════

@Composable
fun CyberGridBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val gridColor = Color(0xFF00D4B4).copy(alpha = 0.04f)
        val cellSize = 60.dp.toPx()

        // Verticale lijnen
        var x = 0f
        while (x < size.width) {
            drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 0.5f)
            x += cellSize
        }

        // Horizontale lijnen
        var y = 0f
        while (y < size.height) {
            drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 0.5f)
            y += cellSize
        }

        // Glow in het midden
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF00D4B4).copy(alpha = 0.06f),
                    Color.Transparent
                ),
                radius = size.width * 0.6f
            ),
            radius = size.width * 0.6f,
            center = Offset(size.width / 2, size.height / 3)
        )
    }
}

// ═══════════════════════════════════════════════════════════
// KLOK WIDGET
// ═══════════════════════════════════════════════════════════

@Composable
fun ClockWidget() {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    var currentDate by remember { mutableStateOf(getCurrentDate()) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            currentTime = getCurrentTime()
            currentDate = getCurrentDate()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = currentTime,
            color = LauncherText,
            fontSize = 72.sp,
            fontWeight = FontWeight.Thin,
            letterSpacing = (-2).sp,
            lineHeight = 72.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = currentDate,
            color = LauncherTeal,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 1.sp
        )
    }
}

private fun getCurrentTime(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
}

private fun getCurrentDate(): String {
    return SimpleDateFormat("EEEE, d MMMM", Locale("nl")).format(Date())
        .replaceFirstChar { it.uppercase() }
}

// ═══════════════════════════════════════════════════════════
// GUARDIAN STATUS MINI-WIDGET
// ═══════════════════════════════════════════════════════════

@Composable
fun GuardianStatusWidget() {
    val infiniteTransition = rememberInfiniteTransition(label = "guardian")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(LauncherGlass)
            .border(1.dp, LauncherGlassBorder, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Pulserende dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(LauncherTeal.copy(alpha = alpha))
            )

            Column {
                Text(
                    text = "Stay4S Guardian",
                    color = LauncherText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Alle 4 lagen actief — Geen bedreigingen",
                    color = LauncherTextDim,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = LauncherTeal,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════
// APP DOCK (onderste balk)
// ═══════════════════════════════════════════════════════════

@Composable
fun AppDock(context: Context) {
    val dockApps = remember {
        listOf(
            DockApp("Telefoon", "com.android.dialer", Icons.Default.Call),
            DockApp("Berichten", "com.google.android.apps.messaging", Icons.Default.Message),
            DockApp("Guardian", "ai.stay4safe.guardian", Icons.Default.Shield),
            DockApp("Camera", "com.nothing.camera", Icons.Default.CameraAlt),
            DockApp("Browser", "com.android.chrome", Icons.Default.Language)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(LauncherGlass)
            .border(1.dp, LauncherGlassBorder, RoundedCornerShape(28.dp))
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            dockApps.forEach { app ->
                DockIcon(app = app, context = context)
            }
        }
    }
}

@Composable
fun DockIcon(app: DockApp, context: Context) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        // App starten
                        val intent = context.packageManager
                            .getLaunchIntentForPackage(app.packageName)
                        intent?.let { context.startActivity(it) }
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (app.packageName == "ai.stay4safe.guardian")
                        LauncherTeal.copy(alpha = 0.2f)
                    else
                        LauncherCard
                )
                .border(
                    1.dp,
                    if (app.packageName == "ai.stay4safe.guardian")
                        LauncherTeal.copy(alpha = 0.5f)
                    else
                        LauncherGlassBorder,
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = app.icon,
                contentDescription = app.label,
                tint = if (app.packageName == "ai.stay4safe.guardian") LauncherTeal else LauncherText,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════
// APP DRAWER
// ═══════════════════════════════════════════════════════════

@Composable
fun AppDrawerOverlay(onDismiss: () -> Unit, context: Context) {
    val installedApps = remember {
        context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.packageName != context.packageName }
            .sortedBy { context.packageManager.getApplicationLabel(it).toString() }
            .take(24)  // Eerste 24 apps tonen
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LauncherBlack.copy(alpha = 0.95f))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {
            // Zoekbalk
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(LauncherGlass)
                    .border(1.dp, LauncherGlassBorder, RoundedCornerShape(16.dp))
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = LauncherTextDim,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Zoek apps...",
                        color = LauncherTextDim,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(installedApps.size) { index ->
                    val app = installedApps[index]
                    AppGridItem(
                        label = context.packageManager.getApplicationLabel(app).toString(),
                        packageName = app.packageName,
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
fun AppGridItem(label: String, packageName: String, context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                val intent = context.packageManager.getLaunchIntentForPackage(packageName)
                intent?.let { context.startActivity(it) }
            }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(LauncherCard)
                .border(1.dp, LauncherGlassBorder, RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label.take(1).uppercase(),
                color = LauncherTeal,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label.take(10),
            color = LauncherText,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ═══════════════════════════════════════════════════════════
// DATA CLASSES
// ═══════════════════════════════════════════════════════════

data class DockApp(
    val label: String,
    val packageName: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
