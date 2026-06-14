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
    subtitle = "Admin, Setup, Store, Chat, Browser, Messenger en Pay voor de Ai Manus Phone.",
    actions = listOf(
        ManusAction("Admin Room", AdminRoomActivity::class.java),
        ManusAction("Setup", SetupActivity::class.java),
        ManusAction("AI Boss Admin", BossAdminActivity::class.java),
        ManusAction("Store", StoreActivity::class.java),
        ManusAction("Ai Chat", ChatActivity::class.java),
        ManusAction("Browser", BrowserActivity::class.java),
        ManusAction("Messenger", MessengerActivity::class.java),
        ManusAction("Pay", PayActivity::class.java)
    )
)

class AdminRoomActivity : ManusHubActivity(
    title = "Manus Admin Room",
    subtitle = "Beheerdersruimte voor eigenaar, AI Boss, devices, agents, policies, audit en flash readiness.",
    lines = listOf(
        "Owner mode: alleen de beheerder bepaalt policies, installaties, betalingen, device-owner acties en flash-trajecten.",
        "AI Boss: hoofdagent voor taakverdeling, parallelle agents, risico-inschatting en besluitvoorstellen.",
        "Device control: status, provisioning, permissies, installatiestatus en managed mode.",
        "Flash readiness: voorbereiding voor ROM/fastboot trajecten zonder automatisch gevaarlijke flash-acties.",
        "Audit: elke beheeractie krijgt later een logregel met tijd, actor, agent, actie, risico en resultaat.",
        "Backend: deze app is de telefoonbasis; productiebeheer komt via Manus Boss API, device registry en admin console."
    ),
    actions = listOf(
        ManusAction("AI Boss Dashboard", BossDashboardActivity::class.java),
        ManusAction("Command Queue", BossCommandQueueActivity::class.java),
        ManusAction("Medewerker agents", BossAgentsActivity::class.java),
        ManusAction("Admin policies", BossPolicyActivity::class.java),
        ManusAction("Audit log", BossAuditActivity::class.java),
        ManusAction("Device & Flash", DeviceFlashActivity::class.java),
        ManusAction("Backend console", BackendConsoleActivity::class.java)
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

class BossAdminActivity : ManusHubActivity(
    title = "Manus AI Boss",
    subtitle = "Eigen beheerdersruimte voor de hoofdagent die alle AI-medewerker agents aanstuurt.",
    lines = listOf(
        "Rol: jij bent eigenaar/admin. Manus AI Boss coordineert, maar voert beheeracties alleen uit binnen jouw policies.",
        "Command queue: nieuwe opdrachten worden eerst geclassificeerd op risico, doel en benodigde permissies.",
        "Agent routing: taken gaan naar de specialist die het beste past bij de opdracht.",
        "Approval gate: installaties, betalingen, accountacties, device-owner acties en datadeling vragen expliciete goedkeuring.",
        "Audit log: elke agentactie moet later terug te lezen zijn met tijd, agent, opdracht, resultaat en bron.",
        "Parallel mode: meerdere agents mogen tegelijk onderzoek, voorbereiding en controles doen; de Boss neemt de eindbeslissing."
    ),
    actions = listOf(
        ManusAction("AI Boss Dashboard", BossDashboardActivity::class.java),
        ManusAction("Command Queue", BossCommandQueueActivity::class.java),
        ManusAction("Medewerker agents", BossAgentsActivity::class.java),
        ManusAction("Admin policies", BossPolicyActivity::class.java),
        ManusAction("Audit log", BossAuditActivity::class.java)
    )
)

class BossDashboardActivity : ManusHubActivity(
    title = "AI Boss Dashboard",
    subtitle = "Overzicht van de hoofdagent en de operationele status.",
    lines = listOf(
        "Status: telefoonbasis actief in Manus Suite; backend orchestration volgt via Manus Boss API.",
        "Primary loop: opdracht ontvangen, classificeren, agents kiezen, resultaten combineren, goedkeuring vragen, uitvoeren, loggen.",
        "Risk lanes: info, low-risk, managed action, restricted action en blocked action.",
        "Parallel agents: onderzoek en voorbereiding mogen naast elkaar lopen; uitvoerende acties blijven policy-gestuurd.",
        "Escalatie: betalingen, accountwijzigingen, datadeling, installaties, device-owner en flash-acties gaan naar eigenaar/admin.",
        "Output: de Boss levert een besluitvoorstel met bronnen, risico's, benodigde permissies en verwachte impact."
    )
)

class BossCommandQueueActivity : ManusHubActivity(
    title = "Command Queue",
    subtitle = "Wachtrijmodel voor opdrachten aan de AI Boss.",
    lines = listOf(
        "1. Intake: opdracht uit chat, admin room, launcher, browser, store of supportkanaal.",
        "2. Classificatie: doel, urgentie, data-impact, kosten, device-impact en benodigde rechten.",
        "3. Routing: taak naar Guardian, Device, Browser, Messenger, Payments, Store, Fleet, Build of Growth Agent.",
        "4. Parallel work: agents verzamelen opties, checks, status en risico's.",
        "5. Decision pack: Boss maakt een samenvatting met voorgestelde actie.",
        "6. Approval: admin bevestigt gevoelige acties.",
        "7. Execution: alleen toegestane acties worden uitgevoerd.",
        "8. Audit: resultaat en context worden opgeslagen."
    )
)

class BossAgentsActivity : ManusHubActivity(
    title = "AI Medewerker Agents",
    subtitle = "Specialistische agents onder de Manus AI Boss.",
    lines = listOf(
        "Guardian Agent: veiligheid, scams, noodsituaties, privacy en risicosignalen.",
        "Device Agent: instellingen, permissies, batterij, launcher, device-owner en ADB-provisioning.",
        "Browser Agent: webcontrole, phishing, samenvattingen, bronnen en veilige betaalmodus.",
        "Messenger Agent: berichten, groepen, business inbox, vertaling en samenvatting.",
        "Payments Agent: abonnementen, entitlements, facturen, refunds en fraudeflags.",
        "Store Agent: catalogus, updates, release rings, rollback en revoke.",
        "Fleet/Admin Agent: family, beheer, policies, toestellen en auditrapporten.",
        "Build/Install Agent: APK-builds, signingchecks, installatiestatus en release readiness.",
        "Growth Agent: zoekt nieuwe productmiddelen, commerciele kansen en ecosysteem-uitbreidingen."
    )
)

class BossAuditActivity : ManusHubActivity(
    title = "Audit Log",
    subtitle = "Verplicht logmodel voor beheer en agentacties.",
    lines = listOf(
        "Auditvelden: timestamp, device id, actor, agent, opdracht, actie, policy, risico, resultaat en foutmelding.",
        "Install events: APK naam, versie, signer, bron, installatiemethode en bevestiging.",
        "Managed events: device-owner status, policywijziging, app allowlist, update ring en rollback.",
        "Payment events: entitlement, provider, bedrag, valuta, status en refundpad; geen kaartdata in Manus logs.",
        "Messenger events: alleen metadata die nodig is voor beheer; berichtinhoud blijft buiten audit tenzij gebruiker expliciet analyse vraagt.",
        "Flash events: bootloaderstatus, build fingerprint, slot, image-hash, commando en herstelplan."
    )
)

class DeviceFlashActivity : ManusHubActivity(
    title = "Device & Flash Readiness",
    subtitle = "Voorbereiding voor Nothing Phone 3a install, managed mode en latere ROM/flash-track.",
    lines = listOf(
        "Current path: APK-laag installeren via ADB en optioneel device-owner op een schoon toestel.",
        "Managed path: Manus Suite als device owner, daarna policies, managed installs en update rings.",
        "Flash path: alleen readiness en inventarisatie zolang er geen geteste ROM images, device tree, vendor blobs en rollbackplan zijn.",
        "Niet automatisch flashen: geen boot/recovery/vendor/system images schrijven zonder expliciet imagepad, testtoestel en bevestiging.",
        "Checks: ADB device, model, Android versie, build fingerprint, bootloader properties, fastboot beschikbaarheid en APK signatures.",
        "Laptop command: `./scripts/flash-readiness.sh` maakt een lokaal readiness report."
    ),
    actions = listOf(
        ManusAction("Open Android instellingen", Settings.ACTION_SETTINGS),
        ManusAction("Open ontwikkelaarsopties", Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
    )
)

class BackendConsoleActivity : ManusHubActivity(
    title = "Backend Console",
    subtitle = "Koppelpunten voor de productieversie van de Admin Room.",
    lines = listOf(
        "manus-boss-api: opdrachten, agent routing, decision packs en approval flow.",
        "identity-api: eigenaar, adminrollen, sessies, device binding en recovery.",
        "device-registry-api: toestelstatus, build, installaties, policies, rings en audit.",
        "catalog-api: Manus Store metadata, APK hashes, releases, rollback en revoke.",
        "entitlement-api: abonnementen, premium AI, family, fleet, invoices en refunds.",
        "ai-gateway: modelrouter, tools, safety filters, cost limits en logging.",
        "admin-web: desktop beheerdersruimte voor fleet, support, updates en commerciele operatie."
    )
)

class BossPolicyActivity : ManusHubActivity(
    title = "AI Boss Policies",
    subtitle = "Beheersregels voor wat agents wel en niet zelfstandig mogen doen.",
    lines = listOf(
        "Autopilot laag 1: lezen, samenvatten, controleren en voorstellen zonder extra toestemming.",
        "Autopilot laag 2: lage-risico device acties alleen na jouw vooraf ingestelde policy.",
        "Approval vereist: betalen, installeren, verwijderen, accounts wijzigen, data delen of device-owner acties.",
        "No silent install: gewone Android toestellen gebruiken user-confirmed installs.",
        "Managed mode: device-owner toestellen mogen later beheerde installs en policies uitvoeren.",
        "Privacy: persoonlijke data blijft minimaal, doelgebonden en zichtbaar in auditlog.",
        "Fallback: bij twijfel stopt de agent en vraagt Manus AI Boss jouw bevestiging."
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
