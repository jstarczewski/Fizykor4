package com.clakestudio.pc.fizykor.data.source.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.local.equation.EquationDao
import com.clakestudio.pc.fizykor.data.source.local.flashcard.FlashCardDao
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import com.clakestudio.pc.fizykor.util.DataProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable


@Database(entities = [Equation::class, FlashCard::class], version = 1)
abstract class PhysicsDatabase : RoomDatabase() {


    abstract fun equationDao(): EquationDao
    abstract fun flashCardDao(): FlashCardDao

    companion object {

        private var INSTANCE: PhysicsDatabase? = null
        private var populateDisposable = CompositeDisposable()

        private val lock = Any()

        fun getInstance(context: Context): PhysicsDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, PhysicsDatabase::class.java, "equations.db").addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            populateDisposable.add(Single.fromCallable {
                                getInstance(context).equationDao().insertAllEquations(*(DataProvider.provideEquations()).toTypedArray())
                                getInstance(context).flashCardDao().saveAllFlashCards(*(DataProvider.provideFlashCards()).toTypedArray())
                            }.subscribeOn(AppSchedulersProvider.ioScheduler()).observeOn(AppSchedulersProvider.uiScheduler()).subscribe())

                        }
                    })
                            .build()

                }
                populateDisposable.clear()
                return INSTANCE!!
            }

        }
    }


}
