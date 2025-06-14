package com.breens.foodadminapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideTodoChampDB(): FirebaseFirestore {
        return Firebase.firestore
    }
    @Provides
    @Singleton
    fun provideRealtimeDB(): FirebaseDatabase {
        return Firebase.database
    }
    @Provides
    @Singleton
    fun provideAccountDB(): FirebaseAuth {
        return Firebase.auth
    }
}
