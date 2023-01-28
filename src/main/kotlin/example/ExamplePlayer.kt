package example

import Player
import Position

open class ExamplePlayer(
    var name: String,
    club: String,
    nation: String,
    league: String,
    var rating: Int,
    positions: List<Position>,
    isIcon: Boolean,
    isHero: Boolean
) : Player(club, nation, league, positions, isIcon, isHero) {
    override fun toString(): String {
        return "$rating $name"
    }
}
