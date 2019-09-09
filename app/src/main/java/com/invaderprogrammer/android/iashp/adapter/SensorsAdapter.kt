package com.invaderprogrammer.android.iashp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import com.invaderprogrammer.android.iashp.R
import com.invaderprogrammer.android.iashp.di.App
import com.invaderprogrammer.android.iashp.mvp.presenter.SensorsPresenter
import com.invaderprogrammer.android.iashp.rest.Sensor
import kotlinx.android.synthetic.main.sensor.view.*
import javax.inject.Inject


class SensorsAdapter : BaseAdapter<SensorsAdapter.SensorsViewHolder>() {

    @Inject
    lateinit var presenter: SensorsPresenter
    @Inject
    lateinit var context: Context
    private var lastPosition: Int = -1

    init {
        App.appComponent.inject(this)
    }

    //создает ViewHolder и инициализирует views для списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sensor, parent, false)
        return SensorsViewHolder(v)
    }

    //связывает views с содержимым
    override fun onBindViewHolder(holder: SensorsViewHolder, position: Int) {
        holder.bind(getItem(position), position, presenter)
        setAnimation(holder.itemView, position)
    }

    //реализация вьюхолдера
    class SensorsViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {

        init {
            //слушатель клика про элементам списка
            itemView.setOnClickListener {

            }
        }

        //привязываем элементы представления списка к RecyclerView и заполняем данными
        override fun bind(item: Any, position: Int, presenter: SensorsPresenter) {
            let {
                item as Sensor
                view.sensor.text = item.name
                view.switch_sensor.isChecked = item.sensor == 1

                view.switch_sensor.setOnClickListener { view ->
                    item.sensor = if (view.switch_sensor.isChecked) 1 else 0
                    presenter.postList(position, view.switch_sensor.isChecked)
                }
            }
        }

    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it animated
        if (position > lastPosition) {
            val animation = loadAnimation(context, R.anim.push_left_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}