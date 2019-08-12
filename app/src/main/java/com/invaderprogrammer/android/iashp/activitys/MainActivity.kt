package com.invaderprogrammer.android.iashp.activitys

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.invaderprogrammer.android.iashp.R
import com.invaderprogrammer.android.iashp.di.App
import com.invaderprogrammer.android.iashp.fragments.ReadingSensorFragment
import com.invaderprogrammer.android.iashp.fragments.SensorsListFragment
import com.invaderprogrammer.android.iashp.mvp.contract.MainContract
import com.invaderprogrammer.android.iashp.mvp.presenter.MainPresenter
import com.invaderprogrammer.android.iashp.rest.Zavod
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var adapter: ArrayAdapter<String>
    @Inject
    lateinit var presenter: MainPresenter

    private var frag: Fragment = SensorsListFragment()
    private lateinit var navView: BottomNavigationView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                frag = SensorsListFragment()
                loadFragment()
                item.isChecked = item.isChecked
                true
            }
            R.id.navigation_dashboard -> {
                frag = ReadingSensorFragment()
                item.isChecked = item.isChecked
                loadFragment()
                true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        presenter.attach(this)
        presenter.makeSpiner()
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        zavods!!.onItemSelectedListener = this
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, position: Int, id: Long) {
        when (navView.selectedItemId) {
            R.id.navigation_home -> {
                frag = SensorsListFragment()
            }
            R.id.navigation_dashboard -> {
                frag = ReadingSensorFragment()
            }
        }
        presenter.clickSpiner()
    }

    override fun addZavod(zavod: Zavod) {
        adapter.add(zavod.name)
    }

    override fun showProgress() {
        progress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        zavods.adapter = adapter
    }

    override fun showErrorMessage(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun refresh() {
        adapter.clear()
        adapter.notifyDataSetChanged()
    }

    override fun spinner(): Spinner {
        return zavods
    }

    override fun loadFragment() {
        Log.i("pres", "click")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, frag, null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }
}
