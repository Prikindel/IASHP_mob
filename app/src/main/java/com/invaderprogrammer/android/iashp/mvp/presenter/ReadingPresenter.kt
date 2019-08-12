package com.invaderprogrammer.android.iashp.mvp.presenter

import com.invaderprogrammer.android.iashp.di.App
import com.invaderprogrammer.android.iashp.mvp.contract.ReadingContract
import com.invaderprogrammer.android.iashp.rest.Reading
import com.invaderprogrammer.android.iashp.rest.SmisApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReadingPresenter : ReadingContract.Presenter() {

    @Inject
    lateinit var smisApi: SmisApi

    @Inject
    lateinit var presenter: MainPresenter

    init {
        App.appComponent.inject(this)
    }

    override fun makeReading() {
        view.showProgress()

        subscribe(smisApi.getReading(presenter.idZavod())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                view.addReading(
                    Reading(
                        electricity = it.electricity,
                        water = it.water,
                        heat = it.heat,
                        vape = it.vape,
                        gas = it.gas,
                        specGas = it.specGas
                    )
                )
            }
            .doOnComplete {
                view.hideProgress()
            }
            .subscribe({
                view.hideProgress()
            }, {
                view.showErrorMessage(it.message)
                view.hideProgress()
                it.printStackTrace()
            })
        )

    }

    override fun refreshReading() {
        view.refresh()
        makeReading()
    }
}