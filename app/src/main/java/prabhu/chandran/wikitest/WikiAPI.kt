package prabhu.chandran.wikitest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiAPI {
    @GET("w/api.php")
    fun search(
        @Query("action") action : Any? = "query",
        @Query("format") format : Any? = "json",
        @Query("prop") prop : Any? = "pageimages|pageterms",
        @Query("generator") generator : Any? = "prefixsearch",
        @Query("redirects") redirects : Int? = 1,
        @Query("formatversion") formatversion : Int? = 2,
        @Query("piprop") piprop : Any? = "thumbnail",
        @Query("wbptterms") wbptterms : Any? = "description",
        @Query("gpssearch") gpssearch : Any?,
        @Query("gpslimit") gpslimit : Int? = 10
    ) : Observable<Response>
}