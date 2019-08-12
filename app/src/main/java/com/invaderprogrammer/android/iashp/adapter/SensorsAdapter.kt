package com.invaderprogrammer.android.iashp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.invaderprogrammer.android.iashp.R
import com.invaderprogrammer.android.iashp.rest.Sensor
import kotlinx.android.synthetic.main.sensor.view.*

class SensorsAdapter : BaseAdapter<SensorsAdapter.SensorsViewHolder>() {

    //создает ViewHolder и инициализирует views для списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorsAdapter.SensorsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sensor, parent, false)
        return SensorsViewHolder(v)
    }

    //реализация вьюхолдера
    class SensorsViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {

        init {
            //слушатель клика про элементам списка
            itemView.setOnClickListener {

            }
        }

        //привязываем элементы представления списка к RecyclerView и заполняем данными
        override fun bind(item: Any) {
            let {
                item as Sensor
                view.sensor.text = item.name
                view.switch_sensor.isChecked = item.sensor == 1
            }
        }

    }
}