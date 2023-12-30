package com.raveendra.finalproject_binar.presentation.payment.payment_webview

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.raveendra.finalproject_binar.databinding.ActivityPaymentWebviewBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class PaymentWebViewActivity :
    BaseViewModelActivity<PaymentWebViewViewModel, ActivityPaymentWebviewBinding>() {
    companion object {
        const val EXTRA_TRANSACTION_URL = "EXTRA_TRANSACTION_URL"
        fun navigate(context: Context, url: String) = with(context) {
            startActivity(
                Intent(
                    this,
                    PaymentWebViewActivity::class.java
                ).putExtra(EXTRA_TRANSACTION_URL, url).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

    private val transactionUrl by lazy {
        intent.getStringExtra(EXTRA_TRANSACTION_URL) ?: "-"
    }

    override val viewModel: PaymentWebViewViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityPaymentWebviewBinding
        get() = ActivityPaymentWebviewBinding::inflate

    override fun onBackPressed() {
        super.onBackPressed()
        MainActivity.navigateWithFlag(this@PaymentWebViewActivity)
    }

    override fun setupViews() = with(binding){
        openUrlFromWebView(transactionUrl)
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun openUrlFromWebView(url: String) {
        binding.wvTransaction.webViewClient = object : WebViewClient() {
            val pd = ProgressDialog(this@PaymentWebViewActivity)
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val requestUrl = request.url.toString()
                return if (requestUrl.contains("gojek://")
                    || requestUrl.contains("shopeeid://")
                    || requestUrl.contains("//wsa.wallet.airpay.co.id/") // This is handle for sandbox Simulator
                    || requestUrl.contains("/gopay/partner/")
                    || requestUrl.contains("/shopeepay/")
                ) {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    startActivity(intent)
                    // `true` means for the specified url, will be handled by OS by starting Intent
                    true
                } else {
                    // `false` means any other url will be loaded normally by the WebView
                    false
                }
            }

//            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
//                pd.setMessage("loading")
//                pd.show()
//                super.onPageStarted(view, url, favicon)
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//                pd.hide()
//                super.onPageFinished(view, url)
//            }
        }
        binding.wvTransaction.settings.loadsImagesAutomatically = true
        binding.wvTransaction.settings.javaScriptEnabled = true
        binding.wvTransaction.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding.wvTransaction.loadUrl(url)
    }

    override fun setupObservers() {

    }

}