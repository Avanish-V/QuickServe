package com.byteappstudio.blooddonate.DI


import com.byteapps.serrvicewala.Address.AddressRepoImpl
import com.byteapps.serrvicewala.Address.AddressRepository
import com.byteapps.serrvicewala.Features.Crousel.CarouselRepository
import com.byteapps.serrvicewala.Features.Crousel.CarouselRepositoryImpl
import com.byteapps.serrvicewala.Features.Offers.OfferRepository
import com.byteapps.serrvicewala.Features.Offers.OfferRepositoryImpl
import com.byteapps.serrvicewala.Features.Orders.OrdersRepoImpl
import com.byteapps.serrvicewala.Features.Orders.OrdersRepository
import com.byteapps.serrvicewala.Features.PaymentGetway.Data.PaymentRepositoryImpl
import com.byteapps.serrvicewala.Features.PaymentGetway.Domain.PaymentRepository
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceRepository
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceRepositoryImpl
import com.byteapps.serrvicewala.Features.Reviews.ServiceReviewsRepoImpl
import com.byteapps.serrvicewala.Features.Reviews.ServiceReviewsRepository
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceCategoryRepoImpl
import com.byteapps.serrvicewala.Features.ServiceCategories.Domain.Repository.ServiceCategoryRepository
import com.byteapps.serrvicewala.Features.UserProfile.UserProfileRepoImpl
import com.byteapps.serrvicewala.Features.UserProfile.UserProfileRepository
import com.byteapps.wiseschool.GeoFencing.GeoLocation.GeofencingImpl
import com.byteapps.wiseschool.GeoFencing.GeoLocation.GeofencingInterface
import com.byteappstudio.b2ccart.Authentications.AuthRepository
import com.byteappstudio.b2ccart.Authentications.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesAuthRepository(repo: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideServiceCategory(serviceCategoryRepoImpl: ServiceCategoryRepoImpl): ServiceCategoryRepository


    @Binds
    abstract fun providesGeofencingRepository(geofencingImpl: GeofencingImpl): GeofencingInterface

    @Binds
    abstract fun providesAddressRepository(addressRepoImpl: AddressRepoImpl): AddressRepository

    @Binds
    abstract fun providesPriceCalculationRepository(priceRepositoryImpl: PriceRepositoryImpl): PriceRepository

    @Binds
    abstract fun providesOrdersRepository(ordersRepoImpl: OrdersRepoImpl): OrdersRepository

    @Binds
    abstract fun providesServiceReviewsRepository(serviceReviewsRepoImpl: ServiceReviewsRepoImpl): ServiceReviewsRepository

    @Binds
    abstract fun providesPaymentRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository

    @Binds
    abstract fun providesOfferRepository(offerRepositoryImpl: OfferRepositoryImpl): OfferRepository

    @Binds
    abstract fun providesUserProfileRepository(userProfileRepoImpl: UserProfileRepoImpl): UserProfileRepository

    @Binds
    abstract fun providesCarouselRepository(carouselRepositoryImpl: CarouselRepositoryImpl): CarouselRepository


}