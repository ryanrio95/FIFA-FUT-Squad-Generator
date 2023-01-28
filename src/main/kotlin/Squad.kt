open class Squad<P : Player?> {
    val players = ArrayList<P>()

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (player in players) {
            if (stringBuilder.isNotEmpty()) {
                stringBuilder.append("\t")
            }
            stringBuilder.append(player)
        }
        return stringBuilder.toString()
    }
}
