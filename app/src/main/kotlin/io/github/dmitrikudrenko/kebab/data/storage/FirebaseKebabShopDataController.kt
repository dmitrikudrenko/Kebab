package io.github.dmitrikudrenko.kebab.data.storage

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.dmitrikudrenko.kebab.crash.CrashReporter
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.model.KebabShop
import rx.Observable
import java.util.*
import javax.inject.Inject


class FirebaseKebabShopDataController @Inject constructor(val context: Context, val crashReporter: CrashReporter) : IKebabShopDataController {
    private val instance: FirebaseDatabase
    private val cache: MutableList<IKebabShop> = ArrayList<IKebabShop>()
    private val onDataChangedListeners = ArrayList<OnDataChangedListener>()

    init {
        FirebaseApp.initializeApp(context)
        instance = FirebaseDatabase.getInstance()
        instance.getReference("Kebabs").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                error?.let { crashReporter.log(it.toException()) }
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    cache.clear()
                    for (child in it.children) {
                        cache.add(child.getValue(KebabShop::class.java))
                    }
                    dispatchDataChanged()
                }
            }
        })
    }

    override fun getKebabShops(): Observable<List<IKebabShop>> {
        return Observable.create { subscriber ->
            val onDataChangedListener = object : OnDataChangedListener {
                override fun onDataChanged() {
                    if (!subscriber.isUnsubscribed)
                        subscriber.onNext(cache)
                    else unregisterOnDataChangeListener(this)
                }
            }
            registerOnDataChangeListener(onDataChangedListener)
        }
    }

    override fun getKebabShop(id: Long): Observable<IKebabShop> {
        return Observable.create { subscriber ->
            val onDataChangedListener = object : OnDataChangedListener {
                override fun onDataChanged() {
                    if (subscriber.isUnsubscribed)
                        subscriber.onNext(cache.find { it.getId() == id })
                    else unregisterOnDataChangeListener(this)
                }
            }
            registerOnDataChangeListener(onDataChangedListener)
        }
    }

    private fun registerOnDataChangeListener(onDataChangedListener: OnDataChangedListener) {
        onDataChangedListeners.add(onDataChangedListener)
        onDataChangedListener.onDataChanged()
    }

    private fun unregisterOnDataChangeListener(onDataChangedListener: OnDataChangedListener) {
        onDataChangedListeners.remove(onDataChangedListener)
    }

    private fun dispatchDataChanged() {
        for (onDataChangedListener in onDataChangedListeners) {
            onDataChangedListener.onDataChanged()
        }
    }

    private interface OnDataChangedListener {
        fun onDataChanged()
    }
}