package prabhu.chandran.wikitest

import android.databinding.BindingAdapter
import android.util.Log
import android.widget.ImageView
import java.util.*

@Suppress("SENSELESS_COMPARISON")
class BindingHelper {
    companion object {
        @JvmStatic
        fun description(data : List<String>?) : String{
            try {
                if (data!!.isNotEmpty()){
                    return Arrays.toString(data.toTypedArray()).replace("[","").replace("]","")
                }
            } catch (e : Throwable) {
                Log.e("Description","onError:\t${e.localizedMessage}")
            }
            return ""
        }

        @JvmStatic
        @BindingAdapter("bind:imageUrl")
        fun loadImage(imageView: ImageView, thumbnailDetails: ThumbnailDetails?){
//            if (thumbnailDetails != null) {
            try {
                GlideApp.with(imageView.context)
                    .load(thumbnailDetails!!.source!!)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView)
            } catch (it :Throwable){
                Log.e("Exception","onException:\t${it.localizedMessage}")
                imageView.setImageDrawable(imageView.resources.getDrawable(R.mipmap.ic_launcher))
            }
//            } else {
//                imageView.setImageDrawable(imageView.resources.getDrawable(R.mipmap.ic_launcher))
//            }
        }
    }

}
