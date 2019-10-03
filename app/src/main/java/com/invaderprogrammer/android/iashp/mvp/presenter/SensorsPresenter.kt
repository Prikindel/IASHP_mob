package com.invaderprogrammer.android.iashp.mvp.presenter

import com.invaderprogrammer.android.iashp.di.App
import com.invaderprogrammer.android.iashp.mvp.contract.SensorsContract
import com.invaderprogrammer.android.iashp.rest.Sensor
import com.invaderprogrammer.android.iashp.rest.SmisApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SensorsPresenter : SensorsContract.Presenter() {

    //внедряем источник данных
    @Inject
    lateinit var smisApi: SmisApi

    @Inject
    lateinit var presenter: MainPresenter

    //инициализируем компоненты даггера
    init {
        App.appComponent.inject(this)
    }

    //создаем список, загружая данные с помощью RX
    override fun makeList() {
        view.showProgress()
        var count = 1

        //подписываемся на поток данных
        subscribe(smisApi.getSensor(presenter.idZavod())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Observable.fromIterable(it) }
            .doOnNext {
                view.addSensor(
                    Sensor(
                        it.name,
                        it.sensor,
                        count
                    )
                )
                count++
            }
            .doOnComplete {
                view.hideProgress()
            }
            .subscribe({
                view.hideProgress()
                view.notifyAdapter()
            }, {
                view.showErrorMessage(it.message)
                view.hideProgress()
                it.printStackTrace()
            })
        )
    }

    override fun postList(position: Int, isChecked: Boolean) {
        val isCheck = if (isChecked) "1" else "0"
        subscribe(
            smisApi.setSensor("b${position}", presenter.idZavod(), isCheck)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun refreshList() {
        view.refresh()
        makeList()
    }
}