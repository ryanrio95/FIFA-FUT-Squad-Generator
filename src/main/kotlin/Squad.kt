open class Squad<P : Player?> {
    val players = ArrayList<P>()

    override fun toString(): String {
        return players.joinToString("\t")
    }
}
