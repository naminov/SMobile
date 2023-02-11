package com.naminov.smobile.di

import com.naminov.smobile.presentation.customer.CustomersFragment
import com.naminov.smobile.presentation.glide.GlideModule
import com.naminov.smobile.presentation.login.LoginFragment
import com.naminov.smobile.presentation.order.details.OrderDetailsFragment
import com.naminov.smobile.presentation.order.history.OrderHistoryFragment
import com.naminov.smobile.presentation.product.ProductsFragment
import com.naminov.smobile.presentation.settings.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        SettingsDaoModule::class,
        SettingsRepositoryModule::class,
        LoginApiModule::class,
        LoginRepositoryModule::class,
        OrdersApiModule::class,
        OrdersRepositoryModule::class,
        CustomersApiModule::class,
        CustomersRepositoryModule::class,
        ProductsApiModule::class,
        ProductsRepositoryModule::class,
        LoginUseCaseModule::class,
        GetSettingsUseCaseModule::class,
        SaveSettingsUseCaseModule::class,
        GetConnectionUseCaseModule::class,
        GetAuthorizationUseCaseModule::class,
        SaveAuthorizationUseCaseModule::class,
        GetOrderHistoryUseCaseModule::class,
        GetCustomerImgUseCaseModule::class,
        GetCustomersUseCaseModule::class,
        GetOrderDetailsNewUseCaseModule::class,
        GetOrderDetailsUseCaseModule::class,
        EditOrderCustomerUseCaseModule::class,
        EditOrderCommentUseCaseModule::class,
        EditOrderProductNumberUseCaseModule::class,
        AddOrderProductUseCaseModule::class,
        RemoveOrderProductUseCaseModule::class,
        HasOrderChangesUseCaseModule::class,
        SaveOrderUseCaseModule::class,
        RemoveOrderUseCaseModule::class,
        GetProductsUseCaseModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(glideModule: GlideModule)
    fun inject(loginFragment: LoginFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(orderHistoryFragment: OrderHistoryFragment)
    fun inject(customersFragment: CustomersFragment)
    fun inject(orderDetailsFragment: OrderDetailsFragment)
    fun inject(productsFragment: ProductsFragment)
}