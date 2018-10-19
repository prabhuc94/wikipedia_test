package prabhu.chandran.wikitest

data class Response(
    val batchcomplete : Boolean? = null,
    val query : QueryDetails? = null
)

data class QueryDetails(
    val pages : List<Pages>? = null
)

data class Pages(
    val pageid : Int? = null,
    val title : Any? = null,
    val thumbnail : ThumbnailDetails? = null,
    val terms : Terms? = null
)

data class ThumbnailDetails(
    val source : String? = null,
    val width : Int? = null,
    val height : Int? = null
)

data class Terms(
    val description : List<String?>? = null
)