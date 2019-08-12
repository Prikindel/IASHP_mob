package com.invaderprogrammer.android.iashp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.invaderprogrammer.android.iashp.mvp.presenter.SensorsPresenter

abstract class BaseAdapter<VH : BaseAdapter.BaseViewHolder> : RecyclerView.Adapter<VH>() {

    //список элементов списка
    var items: ArrayList<Any> = ArrayList()

    //возвращающает размер списка
    override fun getItemCount(): Int {
        return items.size
    }

    //возвращает позицию элемента в списке
    fun getItem(position: Int): Any {
        return items[position]
    }

    //функция добавления одного элемента
    fun add(newItem: Any) {
        items.add(newItem)
    }

    //функция добавления всех элементов
    fun add(newItems: List<Any>) {
        items.addAll(newItems)
    }

    //абстрактный класс ViewHolder
    abstract class BaseViewHolder(protected val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: Any, position: Int, presenter: SensorsPresenter)
    }
}