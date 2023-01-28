import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.max
import kotlin.math.min

private const val SQUAD_SIZE = 11

fun <P : Player> groupByPositions(players: Collection<P>): List<P> {
    val playersList = ArrayList(players)
    val groupedPlayers = ArrayList<P>()

    while (playersList.isNotEmpty()) {
        for (position in Position.values()) {
            val playerIndex = playersList.indexOfFirst { it.positions.contains(position) }
            if (playerIndex >= 0) {
                val player = playersList.removeAt(playerIndex)
                groupedPlayers.add(player)
            }
        }
    }
    return groupedPlayers
}

fun <P : Player> groupByPositionsAndOriginalOrder(players: Collection<P>): List<P> {
    val playersList = ArrayList(players)
    val groupedPlayers = ArrayList<P>()

    while (playersList.isNotEmpty()) {
        for (position in Position.values()) {
            val playerIndex = playersList.indexOfFirst { it.positions.contains(position) }
            if (playerIndex >= 0) {
                val player = playersList.removeAt(playerIndex)
                groupedPlayers.add(player)
            }
            if (playersList.isNotEmpty()) {
                val player = playersList.removeAt(0)
                groupedPlayers.add(player)
            }
        }
    }
    return groupedPlayers
}

fun <P : Player> getFormations(players: List<P>, formations: List<Formation>): Map<Formation, List<P>> {
    return formations.associateWith {
        getPositioning(players, it.positions)
    }.filter { it.value.size >= SQUAD_SIZE }
}

fun <P : Player> getFormation(players: List<P>, formations: List<Formation>): Pair<Formation?, List<P>>? {
    for (formation in formations) {
        val positioning = getPositioning(players, formation.positions)
        if (positioning.size >= SQUAD_SIZE) {
            return Pair(formation, positioning)
        }
    }
    return null
}

fun <P : Player> getPositioning(players: List<P>, positions: List<Position>): List<P> {
    val positioning = getPositioning(players, positions, 0, IntArray(SQUAD_SIZE) { -1 })
    if (positioning != null) {
        val positionedPlayers = ArrayList<P>()
        for (playerIndex in positioning) {
            positionedPlayers.add(players[playerIndex])
        }
        return positionedPlayers
    }
    return emptyList()
}

private fun <P : Player> getPositioning(
    players: List<P>,
    positions: List<Position>,
    playerIndex: Int,
    positioning: IntArray
): IntArray? {
    if (playerIndex >= SQUAD_SIZE) {
        return positioning
    }

    val player = players[playerIndex]

    for ((positionIndex, position) in positions.withIndex()) {
        if (positioning[positionIndex] < 0 && player.positions.contains(position)) {
            positioning[positionIndex] = playerIndex

            if (getPositioning(players, positions, playerIndex + 1, positioning) != null) {
                return positioning
            }
            positioning[positionIndex] = -1
        }
    }
    return null
}

// TODO
fun getChemistry(players: List<Player>): Int {
    val clubCounts = HashMap<String, Int>()
    val nationCounts = HashMap<String, Int>()
    val leagueCounts = HashMap<String, Int>()

    for (player in players) {
        if (!clubCounts.contains(player.club)) {
            clubCounts[player.club] = 0
        }
        if (!nationCounts.contains(player.nation)) {
            nationCounts[player.nation] = 0
        }
        if (!leagueCounts.contains(player.league)) {
            leagueCounts[player.league] = 0
        }

        if (player.isIcon) {
            nationCounts[player.nation] = nationCounts[player.nation]!! + 2
        } else if (player.isHero) {
            nationCounts[player.nation] = nationCounts[player.nation]!! + 1
            leagueCounts[player.league] = leagueCounts[player.league]!! + 2
        } else {
            clubCounts[player.club] = clubCounts[player.club]!! + 1
            nationCounts[player.nation] = nationCounts[player.nation]!! + 1
            leagueCounts[player.league] = leagueCounts[player.league]!! + 1
        }
    }

    var chemistry = 0
    for (player in players) {
        val clubCount = clubCounts[player.club] ?: 0
        val nationCount = nationCounts[player.nation] ?: 0
        val leagueCount = leagueCounts[player.league] ?: 0

        val playerChemistry = if (player.isIcon || player.isHero) {
            3
        } else {
            (if (clubCount >= 7) 3 else if (clubCount >= 4) 2 else if (clubCount >= 2) 1 else 0) +
                (if (nationCount >= 8) 3 else if (nationCount >= 5) 2 else if (nationCount >= 2) 1 else 0) +
                (if (leagueCount >= 8) 3 else if (leagueCount >= 5) 2 else if (leagueCount >= 3) 1 else 0)
        }
        chemistry += min(playerChemistry, 3)
    }
    return chemistry
}

abstract class SquadGenerator<S : Squad<P>, P : Player> {
    abstract fun getNewSquad(): S

    abstract fun getPlayers(): List<P>

    fun generateSquads() {
        val players = getPlayers()
        addPlayers(getNewSquad(), players, players.size)
    }

    fun generateSquadsAsync() {
        val players = getPlayers()
        val threadCount = max(1, Runtime.getRuntime().availableProcessors() - 1)
        val executorService = Executors.newFixedThreadPool(threadCount)

        for (playerIndex in players.indices) {
            val squadBuilderRunnable = SquadGeneratorRunnable(players, playerIndex)
            executorService.execute(squadBuilderRunnable)
        }
        executorService.shutdown()
    }

    private fun addPlayers(squad: S, players: List<P>, maxPlayerIndex: Int) {
        if (maxPlayerIndex < SQUAD_SIZE - squad.players.size) {
            return
        } else if (squad.players.size >= SQUAD_SIZE) {
            if (isSquadApproved(squad)) {
                onSquadApproved(squad)
            }
            return
        }

        for (playerIndex in 0 until maxPlayerIndex) {
            addPlayer(squad, players, playerIndex)
        }
    }

    private fun addPlayer(squad: S, players: List<P>, playerIndex: Int) {
        val player = players[playerIndex]

        squad.players.add(player)
        onAddPlayer(squad, player)

        if (isPlayerApproved(squad, player)) {
            addPlayers(squad, players, playerIndex)
        }

        squad.players.remove(player)
        onRemovePlayer(squad, player)
    }

    open fun onAddPlayer(squad: S, player: P) {
    }

    open fun isPlayerApproved(squad: S, player: P): Boolean {
        return true
    }

    open fun onRemovePlayer(squad: S, player: P) {
    }

    open fun isSquadApproved(squad: S): Boolean {
        return true
    }

    open fun onSquadApproved(squad: S) {
    }

    private inner class SquadGeneratorRunnable(
        private val players: List<P>,
        private val playerIndex: Int
    ) : Runnable {
        override fun run() {
            addPlayer(getNewSquad(), players, playerIndex)
        }
    }
}
