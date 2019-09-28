package com.testrates

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.testrates.bind.BinderAdapterComponent
import com.testrates.data.model.RateItemObject
import com.testrates.databinding.ActivityMainBinding
import com.testrates.utils.isEnableTextWatcher
import com.testrates.utils.isInternetAvailable
import com.testrates.viewmodel.RateResponseViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class MainActivity : DaggerAppCompatActivity(),
    RatesDataAdapter.RateDataAdapterListener, TextWatcher {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var componentBind: BinderAdapterComponent
    private lateinit var responseViewModel: RateResponseViewModel
    private var rateAdapter: RatesDataAdapter? = null
    private var currentSelectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        componentBind = BinderAdapterComponent()
        activityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main, componentBind
        )
        activityMainBinding.rateEditItem.visibility = View.GONE
        responseViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RateResponseViewModel::class.java)
        val mLayoutManager =
            LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false)
        activityMainBinding.recyclerView.layoutManager = mLayoutManager
        activityMainBinding.recyclerView.isNestedScrollingEnabled = false

    }

    override fun afterTextChanged(p0: Editable?) {
        responseViewModel.getCurrentRateChangeValue(if (p0.toString().isNotEmpty()) p0.toString().toDouble() else 0.0)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onResume() {
        super.onResume()
        if (isInternetAvailable(this)) prepareObservableViewModel() else {
            activityMainBinding.progressView.visibility = View.GONE
            val snack = Snackbar.make(
                activityMainBinding.root,
                getString(R.string.network_issue_message),
                Snackbar.LENGTH_LONG
            )
            snack.show()
        }
        activityMainBinding.rateAmount.setOnClickListener {
            activityMainBinding.rateAmount.isFocusable = true

            activityMainBinding.rateAmount.isFocusableInTouchMode = true
            activityMainBinding.rateAmount.addTextChangedListener(this)
        }
    }

    override fun currentItemPosition(currentPosition: Int, itemData: RateItemObject) {
        hideKeyboardFrom(activityMainBinding.root)
        currentSelectedItem = currentPosition+1
        rateAdapter?.swapItem(currentPosition, activityMainBinding.rateSingleInfo!!)
        activityMainBinding.rateSingleInfo = itemData
        activityMainBinding.rateAmount.removeTextChangedListener(this)
        activityMainBinding.rateAmount.isFocusable = false
        activityMainBinding.rateAmount.isFocusableInTouchMode = false
        activityMainBinding.rateNestedScroll.fullScroll(View.FOCUS_UP)
    }

    private fun prepareObservableViewModel() {
        responseViewModel.getRatesList().observe(this, Observer<List<RateItemObject>> { rateList ->

            if (rateAdapter == null) {
                activityMainBinding.rateEditItem.visibility = View.VISIBLE
                activityMainBinding.rateSingleInfo = rateList[0]
                val modifyList = rateList as ArrayList<RateItemObject>
                modifyList.removeAt(0)

                rateAdapter = RatesDataAdapter(modifyList, componentBind, this)
                activityMainBinding.recyclerView.adapter = rateAdapter
                activityMainBinding.progressView.visibility = View.GONE
                isEnableTextWatcher.set(true)
                return@Observer
            }
            if (rateList.isNotEmpty()) {
                activityMainBinding.rateSingleInfo = rateList[currentSelectedItem]
                val modifyList = rateList as ArrayList<RateItemObject>
                modifyList.removeAt(currentSelectedItem)
                rateAdapter?.updateList(modifyList)
            }

        })

        responseViewModel.getUpdateRatesList()
            .observe(this, Observer<List<RateItemObject>> { updateList ->
                if (updateList.isNotEmpty()) {
                    rateAdapter?.updateList(updateList as ArrayList<RateItemObject>)
                }
            })

        responseViewModel.getError().observe(this, Observer<Boolean> { aBoolean ->
            if ((aBoolean != null) and aBoolean) {
                activityMainBinding.progressView.visibility = View.GONE
                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

    inner class LinearLayoutManagerWrapper(
        context: Context,
        orientation: Int,
        reverseLayout: Boolean
    ) : LinearLayoutManager(
        context,
        orientation,
        reverseLayout
    ) {

        override fun supportsPredictiveItemAnimations(): Boolean {
            return false
        }
    }

    fun hideKeyboardFrom(view: View) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
