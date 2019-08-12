package com.invaderprogrammer.android.iashp.mvp.presenter

import com.invaderprogrammer.android.iashp.di.App
import com.invaderprogrammer.android.iashp.mvp.contract.MainContract
import com.invaderprogrammer.android.iashp.rest.SmisApi
import com.invaderprogrammer.android.iashp.rest.Zavod
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter : MainContract.Presenter() {


    @Inject
    lateinit var smisApi: SmisApi

    private var zavodsList: ArrayList<Zavod>

    init {
        App.appComponent.inject(this)
        zavodsList = ArrayList()
    }

    override fun makeSpiner() {
        view.showProgress()

        subscribe(smisApi.getZavod()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                zavodsList.clear()
                zavodsList.addAll(it)
            }
            .flatMap { Observable.fromIterable(it) }
            .doOnNext {
                view.addZavod(
                    Zavod(
                        it.id,
                        it.name
                    )
                )
                //zavodsList.add(Zavod(it.id, it.name))
            }
            .doOnComplete {
                view.hideProgress()
                view.loadFragment()
            }
            .subscribe({
                //view.hideProgress()
            }, {
                view.showErrorMessage(it.message)
                //view.hideProgress()
                it.printStackTrace()
            })
        )
    }

    override fun refreshSpiner() {
        view.refresh()
        makeSpiner()
    }

    fun idZavod(): Int {
        if (zavodsList.count() != 0) {
            var id = zavodsList[0].id
            val name = view.spinner().selectedItem.toString()
            for ((id1, name1) in zavodsList) {
                if (name == name1) {
                    id = id1
                    break
                }
            }
            return id
        } else {
            return -1
        }
    }

    override fun clickSpiner() {
        view.loadFragment()
    }

}