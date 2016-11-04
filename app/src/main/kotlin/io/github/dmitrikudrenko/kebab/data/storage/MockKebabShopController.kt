package io.github.dmitrikudrenko.kebab.data.storage

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.model.KebabShop
import rx.Observable
import java.io.InputStreamReader

class MockKebabShopController(private val context: Context) : IKebabShopDataController {
    override fun getKebabShops(): Observable<List<IKebabShop>> {
        val inputStream = context.assets.open("kebabShops.json")
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        val shops = gson.fromJson(InputStreamReader(inputStream), Array<KebabShop>::class.java)
        return Observable.just(shops.asList())
    }
}