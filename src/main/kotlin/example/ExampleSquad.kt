package example

import Formation
import Squad

open class ExampleSquad : Squad<ExamplePlayer>() {
    var formation: Formation? = null
    var rating = 0
    var chemistry = 0

    override fun toString(): String {
        val stringBuilder = StringBuilder(super.toString())
        stringBuilder.append("\t").append(formation?.name)
        stringBuilder.append("\t").append(rating)
        stringBuilder.append("\t").append(chemistry)

        return stringBuilder.toString()
    }
}
