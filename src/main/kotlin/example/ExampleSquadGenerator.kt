package example

import FORMATIONS
import SquadGenerator
import getChemistry
import getFormation
import groupByPositions

private const val MIN_SQUAD_CHEMISTRY = 33

fun main() {
    ExampleSquadGenerator.generateSquadsAsync()
}

object ExampleSquadGenerator : SquadGenerator<ExampleSquad, ExamplePlayer>() {
    private var bestSquadRating = 0

    override fun getNewSquad(): ExampleSquad {
        return ExampleSquad()
    }

    override fun getPlayers(): List<ExamplePlayer> {
        val players = listOf(
            ExamplePlayer("Pelé", "", "Brazil", "", 98, listOf(Position.CAM), true, false),
            ExamplePlayer("Ronaldo", "", "Brazil", "", 96, listOf(Position.ST), true, false),
            ExamplePlayer(
                "Zinedine Zidane",
                "",
                "France",
                "",
                96,
                listOf(Position.CAM),
                true,
                false
            ),
            ExamplePlayer(
                "Diego Forlán",
                "",
                "Uruguay",
                "LaLiga Santander",
                91,
                listOf(Position.ST, Position.CF),
                false,
                true
            ),
            ExamplePlayer(
                "Karim Benzema",
                "Real Madrid",
                "France",
                "LaLiga Santander",
                91,
                listOf(Position.CF, Position.ST),
                false,
                false
            ),
            ExamplePlayer(
                "Kevin De Bruyne",
                "Manchester City",
                "Belgium",
                "Premier League",
                91,
                listOf(Position.CM, Position.CAM),
                false,
                false
            ),
            ExamplePlayer(
                "Kylian Mbappé",
                "Paris SG",
                "France",
                "Ligue 1",
                91,
                listOf(Position.ST, Position.CF),
                false,
                false
            ),
            ExamplePlayer(
                "Lionel Messi",
                "Paris SG",
                "Argentina",
                "Ligue 1",
                91,
                listOf(Position.RW, Position.RM),
                false,
                false
            ),
            ExamplePlayer(
                "Robert Lewandowski",
                "FC Barcelona",
                "Poland",
                "LaLiga Santander",
                91,
                listOf(Position.ST, Position.CF),
                false,
                false
            ),
            ExamplePlayer(
                "Rudolf Völler",
                "",
                "Germany",
                "Serie A TIM",
                91,
                listOf(Position.ST, Position.CF),
                false,
                true
            ),
            ExamplePlayer(
                "Cristiano Ronaldo",
                "Manchester Utd",
                "Portugal",
                "Premier League",
                90,
                listOf(Position.ST, Position.CF),
                false,
                false
            ),
            ExamplePlayer(
                "Manuel Neuer",
                "FC Bayern",
                "Germany",
                "Bundesliga",
                90,
                listOf(Position.GK),
                false,
                false
            ),
            ExamplePlayer(
                "Mohamed Salah",
                "Liverpool",
                "Egypt",
                "Premier League",
                90,
                listOf(Position.RW, Position.RM),
                false,
                false
            ),
            ExamplePlayer(
                "Thibaut Courtois",
                "Real Madrid",
                "Belgium",
                "LaLiga Santander",
                90,
                listOf(Position.GK),
                false,
                false
            ),
            ExamplePlayer(
                "Virgil van Dijk",
                "Liverpool",
                "Netherlands",
                "Premier League",
                90,
                listOf(Position.CB),
                false,
                false
            ),
            ExamplePlayer(
                "Alisson",
                "Liverpool",
                "Brazil",
                "Premier League",
                89,
                listOf(Position.GK),
                false,
                false
            ),
            ExamplePlayer(
                "Casemiro",
                "Manchester Utd",
                "Brazil",
                "Premier League",
                89,
                listOf(Position.CDM),
                false,
                false
            ),
            ExamplePlayer(
                "Ederson",
                "Manchester City",
                "Brazil",
                "Premier League",
                89,
                listOf(Position.GK),
                false,
                false
            ),
            ExamplePlayer(
                "Harry Kane",
                "Spurs",
                "England",
                "Premier League",
                89,
                listOf(Position.ST, Position.CF),
                false,
                false
            ),
            ExamplePlayer(
                "Heung Min Son",
                "Spurs",
                "Korea Republic",
                "Premier League",
                89,
                listOf(Position.LW, Position.LM),
                false,
                false
            ),
            ExamplePlayer(
                "Jan Oblak",
                "Atlético de Madrid",
                "Slovenia",
                "LaLiga Santander",
                89,
                listOf(Position.GK),
                false,
                false
            ),
            ExamplePlayer(
                "Joshua Kimmich",
                "FC Bayern",
                "Germany",
                "Bundesliga",
                89,
                listOf(Position.CDM, Position.RB),
                false,
                false
            ),
            ExamplePlayer(
                "N'Golo Kanté",
                "Chelsea",
                "France",
                "Premier League",
                89,
                listOf(Position.CDM, Position.CM),
                false,
                false
            ),
            ExamplePlayer(
                "Neymar Jr",
                "Paris SG",
                "Brazil",
                "Ligue 1",
                89,
                listOf(Position.LW, Position.LM),
                false,
                false
            ),
            ExamplePlayer(
                "Sadio Mané",
                "FC Bayern",
                "Senegal",
                "Bundesliga",
                89,
                listOf(Position.LM, Position.CF),
                false,
                false
            ),
            ExamplePlayer(
                "Bernardo Silva",
                "Manchester City",
                "Portugal",
                "Premier League",
                88,
                listOf(Position.CAM, Position.CM),
                false,
                false
            ),
            ExamplePlayer(
                "Erling Haaland",
                "Manchester City",
                "Norway",
                "Premier League",
                88,
                listOf(Position.ST, Position.CF),
                false,
                false
            ),
            ExamplePlayer(
                "Gianluigi Donnarumma",
                "Paris SG",
                "Italy",
                "Ligue 1",
                88,
                listOf(Position.GK),
                false,
                false
            ),
            ExamplePlayer(
                "João Cancelo",
                "Manchester City",
                "Portugal",
                "Premier League",
                88,
                listOf(Position.LB, Position.RB),
                false,
                false
            ),
            ExamplePlayer(
                "Keylor Navas",
                "Paris SG",
                "Costa Rica",
                "Ligue 1",
                88,
                listOf(Position.GK),
                false,
                false
            ),
            ExamplePlayer(
                "Luka Modric",
                "Real Madrid",
                "Croatia",
                "LaLiga Santander",
                88,
                listOf(Position.CM),
                false,
                false
            ),
            ExamplePlayer(
                "Marquinhos",
                "Paris SG",
                "Brazil",
                "Ligue 1",
                88,
                listOf(Position.CB),
                false,
                false
            ),
            ExamplePlayer(
                "Rúben Dias",
                "Manchester City",
                "Portugal",
                "Premier League",
                88,
                listOf(Position.CB),
                false,
                false
            ),
            ExamplePlayer(
                "Toni Kroos",
                "Real Madrid",
                "Germany",
                "LaLiga Santander",
                88,
                listOf(Position.CM),
                false,
                false
            ),
            ExamplePlayer(
                "ter Stegen",
                "FC Barcelona",
                "Germany",
                "LaLiga Santander",
                88,
                listOf(Position.GK),
                false,
                false
            )
        )
        return groupByPositions(players)
    }

    override fun onAddPlayer(squad: ExampleSquad, player: ExamplePlayer) {
        squad.rating += player.rating
    }

    override fun onRemovePlayer(squad: ExampleSquad, player: ExamplePlayer) {
        squad.rating -= player.rating
    }

    override fun isSquadApproved(squad: ExampleSquad): Boolean {
        if (squad.rating < bestSquadRating) {
            return false
        }

        squad.chemistry = getChemistry(squad.players)
        if (squad.chemistry < MIN_SQUAD_CHEMISTRY) {
            return false
        }

        squad.formation = getFormation(squad.players, FORMATIONS)?.first
        if (squad.formation == null) {
            return false
        }

        return true
    }

    override fun onSquadApproved(squad: ExampleSquad) {
        bestSquadRating = squad.rating

        println(squad)
    }
}
