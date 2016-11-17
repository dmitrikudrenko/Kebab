package io.github.dmitrikudrenko.kebab.data.storage

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.model.KebabShop
import rx.Observable
import java.io.InputStreamReader

class MockKebabShopController(private val context: Context) : IKebabShopDataController {
    private var cache: List<IKebabShop>? = null

    override fun getKebabShops(): Observable<List<IKebabShop>?> {
        if (cache == null) {
            val inputStream = context.assets.open("kebabShops.json")
            val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
            val shops = gson.fromJson(InputStreamReader(inputStream), Array<KebabShop>::class.java)
            cache = shops.asList()
        }
        return Observable.just(cache)
    }

    override fun getKebabShop(id: Long): Observable<IKebabShop?> {
        return Observable.just(cache?.find { it.getId() == id })
    }

    override fun createKebabShop(kebabShop: IKebabShop): Observable<Boolean> {
        cache?.plus(kebabShop)
        return Observable.just(true)
    }
}