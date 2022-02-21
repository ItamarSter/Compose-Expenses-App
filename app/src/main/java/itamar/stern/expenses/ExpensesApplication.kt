package itamar.stern.expenses

import android.app.Application
import itamar.stern.expenses.room_db.RoomDB

class ExpensesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ExpensesApplication

        val roomDB: RoomDB by lazy {
            RoomDB.create(instance)
        }
    }
}