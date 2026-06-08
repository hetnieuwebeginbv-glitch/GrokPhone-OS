package ai.stay4safe.manus

import android.app.Activity
import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

private const val BACKGROUND = "#080810"
private const val SURFACE = "#12121A"
private const val TEXT = "#E8E8F0"
private const val MUTED = "#9AA0B8"
private const val TEAL = "#00D4B4"

class MainActivity : ManusHubActivity(
    title = "Manus Suite",
    subtitle = "Setup, Store, Chat, Browser, Messenger en Pay voor de Ai Manus Phone.",
    actions = listOf(
        ManusAction("Setup", SetupActivity::class.java),
        ManusAction("Store", StoreActivity::class.java),
        ManusAction("Ai Chat", ChatActivity::class.java),
        ManusAction("Browser", BrowserActivity::class.java),
        ManusAction("Messenger", MessengerActivity::class.java),
        ManusAction("Pay", PayActivity::class.java)
    )
)

class SetupActivity : ManusHubActivity(
    title = "Manus Setup",
    subtitle = "Maak de Nothing Phone 3a klaar als Ai Manus Phone.",
    lines = listOf(
        "1. Kies Manus Launcher als standaard launcher.",
        "2. Geef Guardian notificatie-, locatie- en sensorpermissies.",
        "3. Zet batterij-optimalisatie uit voor Guardian en Manus Suite.",
        "4. Configureer noodcontacten en AI consent.",
        "5. Activeer managed provisioning op schone toestellen waar nodig."
    ),
    actions = listOf(
        ManusAction("Open Android instellingen", Settings.ACTION_SETTINGS),
        ManusAction("Open launcher keuze", Settings.ACTION_HOME_SETTINGS)
    )
)

class StoreActivity : ManusHubActivity(
    title = "Manus Store",
    subtitle = "Eigen updatekanaal en appcatalogus voor Manus apps.",
    lines = listOf(
        "Catalogus: Guardian, Launcher, Glyph, Browser, Messenger, Pay en agents.",
        "Release rings: internal, alpha, beta, stable en hotfix.",
        "Normale toestellen gebruiken user-confirmed installs.",
        "Managed toestellen kunnen later device-owner installs gebruiken."
    )
)

class ChatActivity : ManusHubActivity(
    title = "Ai Chat",
    subtitle = "Gebruikerschat voor device help, Guardian uitleg en veilige acties.",
    lines = listOf(
        "Ai Chat is de gebruiker-facing assistent.",
        "Manus AI Boss blijft de orchestrator achter de schermen.",
        "Acties met impact vragen bevestiging of managed policy.",
        "Cloud gateway en on-device classificatie komen in de backendfase."
    )
)

class BrowserActivity : ManusHubActivity(
    title = "Manus Browser",
    subtitle = "AI-browser met Guardian checks en veilige betaalmodus.",
    lines = listOf(
        "Phishing/scam waarschuwingen via Guardian.",
        "AI-samenvatting en betrouwbaarheidsscore.",
        "Private browsing standaard.",
        "Browser risk signalen gaan naar Manus AI Glyph."
    )
)

class MessengerActivity : ManusHubActivity(
    title = "Manus Messenger",
    subtitle = "Eigen secure messenger, geen WhatsApp-kloon.",
    lines = listOf(
        "1-op-1, groepen, media, voice notes en business inbox.",
        "E2EE met bewezen protocolkeuze.",
        "WhatsApp alleen via officiele Business Platform APIs waar toegestaan.",
        "Messenger signalen gaan naar Manus AI Glyph."
    )
)

class PayActivity : ManusHubActivity(
    title = "Manus Pay",
    subtitle = "Eigen entitlement- en betaalmodel voor Manus OS.",
    lines = listOf(
        "Abonnementen, premium AI, family plans en fleet licenses.",
        "Marketplace aankopen en agent subscriptions.",
        "Start via payment provider; geen wallet zonder KYC/AML traject.",
        "Payment signalen gaan naar Manus AI Glyph."
    )
)

open class ManusHubActivity(
    private val title: String = "",
    private val subtitle: String = "",
    private val lines: List<String> = emptyList(),
    private val actions: List<ManusAction> = emptyList()
) : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(buildContent())
    }

    private fun buildContent(): View {
        val scroll = ScrollView(this).apply { setBackgroundColor(Color.parseColor(BACKGROUND)) }
        val column = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(36, 44, 36, 44)
        }

        column.addView(TextView(this).apply {
            text = title
            setTextColor(Color.parseColor(TEAL))
            textSize = 28f
            gravity = Gravity.START
        })
        column.addView(TextView(this).apply {
            text = subtitle
            setTextColor(Color.parseColor(TEXT))
            textSize = 16f
            setPadding(0, 16, 0, 28)
        })

        lines.forEach { line ->
            column.addView(TextView(this).apply {
                text = line
                setTextColor(Color.parseColor(MUTED))
                textSize = 15f
                setPadding(0, 8, 0, 8)
            })
        }

        actions.forEach { action ->
            column.addView(Button(this).apply {
                text = action.label
                setTextColor(Color.parseColor(BACKGROUND))
                setBackgroundColor(Color.parseColor(TEAL))
                setPadding(0, 10, 0, 10)
                setOnClickListener { action.launch(this@ManusHubActivity) }
            })
        }

        scroll.addView(column)
        return scroll
    }
}

data class ManusAction(
    val label: String,
    val activityClass: Class<out Activity>? = null,
    val settingsAction: String? = null
) {
    constructor(label: String, activityClass: Class<out Activity>) : this(label, activityClass, null)
    constructor(label: String, settingsAction: String) : this(label, null, settingsAction)

    fun launch(context: Context) {
        when {
            activityClass != null -> context.startActivity(Intent(context, activityClass))
            settingsAction != null -> runCatching {
                context.startActivity(Intent(settingsAction))
            }.onFailure {
                Toast.makeText(context, "Instelling niet beschikbaar op dit toestel", Toast.LENGTH_SHORT).show()
            }
            else -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone")))
        }
    }
}

class ManusDeviceAdminReceiver : DeviceAdminReceiver()
