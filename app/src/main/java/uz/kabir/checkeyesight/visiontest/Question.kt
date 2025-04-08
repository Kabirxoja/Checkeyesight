package uz.kabir.checkeyesight.visiontest

data class Question(
    val id: Int,
    var image: Int,
    val variants: List<Int>
)
