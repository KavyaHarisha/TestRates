package com.testrates.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.databinding.ObservableBoolean
import com.testrates.R
import java.text.NumberFormat

val CURRENCY_NAMES = arrayOf(
    "AUD", "BGN", "BRL", "CAD","CHF","CNY", "CZK", "DKK", "EUR","GBP","HKD","HRK", "HUF","IDR","ILS","INR",
    "ISK","JPY","KRW", "MXN","MYR","NOK","NZD","PHP","PLN","RON","RUB","SEK","SGD","THB","TRY","USD",
    "ZAR")

// Currency long names
val CURRENCY_LONG_NAMES = arrayOf(
    R.string.long_aud,R.string.long_bgn,R.string.long_brl,R.string.long_cad,R.string.long_chf,
    R.string.long_cny,R.string.long_czk,R.string.long_dkk,R.string.long_eur,R.string.long_gbp,
    R.string.long_hkd,R.string.long_hrk,R.string.long_huf,R.string.long_idr,R.string.long_ils,
    R.string.long_inr,R.string.long_isk,R.string.long_jpy,R.string.long_krw,R.string.long_mxn,
    R.string.long_myr,R.string.long_nok,R.string.long_nzd,R.string.long_php, R.string.long_pln,
    R.string.long_ron,R.string.long_rub,R.string.long_sek,R.string.long_sgd,R.string.long_thb,
    R.string.long_try, R.string.long_usd, R.string.long_zar)

// Currency flags
val CURRENCY_FLAGS = arrayOf(
    R.drawable.flag_aud,R.drawable.flag_bgn,R.drawable.flag_brl,R.drawable.flag_cad,R.drawable.flag_chf,
    R.drawable.flag_cny,R.drawable.flag_czk,R.drawable.flag_dkk,R.drawable.flag_eur,R.drawable.flag_gbp,
    R.drawable.flag_hkd,R.drawable.flag_hrk, R.drawable.flag_huf,R.drawable.flag_idr,R.drawable.flag_ils,
    R.drawable.flag_inr,R.drawable.flag_isk,R.drawable.flag_jpy,R.drawable.flag_kpw,R.drawable.flag_mxn,
    R.drawable.flag_myr,R.drawable.flag_nok,R.drawable.flag_nzd,R.drawable.flag_php,R.drawable.flag_pln,
    R.drawable.flag_ron,R.drawable.flag_rub,R.drawable.flag_sek,R.drawable.flag_sgd,R.drawable.flag_thb,
    R.drawable.flag_try, R.drawable.flag_usd, R.drawable.flag_zar)


@Suppress("DEPRECATION")
fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }
    }
    return result
}

fun getNumberFormat(): NumberFormat {
    val numberFormat = NumberFormat.getInstance()
    numberFormat.minimumFractionDigits = 3
    //numberFormat.maximumFractionDigits = 3
    return numberFormat
}

var isEnableTextWatcher = ObservableBoolean(false)



