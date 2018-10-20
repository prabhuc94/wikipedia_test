package prabhu.chandran.wikitest

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class WebViewPage : AppCompatActivity() {
    @BindView(R.id.webView) lateinit var webView: WebView
    @BindView(R.id.backBtn) lateinit var backBtn: ImageView
    @BindView(R.id.progressBar) lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_web_page)
        ButterKnife.bind(this)
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onStart() {
        super.onStart()
        @Suppress("SENSELESS_COMPARISON")
        if (webView != null) {

            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true

            webSettings.builtInZoomControls = true
            webView.loadUrl("https://en.m.wikipedia.org/wiki/${intent.extras!!["title"]}")
            webView.webViewClient = (object :  WebViewClient(){
                override fun shouldOverrideUrlLoading(view: WebView?, request: String?): Boolean {
                    if (request!!.contentEquals("https://en.m.wikipedia.org/wiki/${intent.extras!!["title"]}")){
                        return false
                    }
                    return true
                }
            })
            webView.webChromeClient = (object : WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    progressBar.progress = newProgress
                    if (newProgress == 100){
                        progressBar.visibility = View.GONE
                    } else {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            })

        }
        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
