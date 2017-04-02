package com.example.ryota.kotlinapp.gateway

import android.util.Log
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.model.alarm.JoinedUser
import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.repository.AlarmRepository
import com.google.firebase.database.*
import org.jetbrains.anko.doAsyncResult
import java.security.InvalidParameterException
import java.util.*
import java.util.concurrent.Future

/**
 * Created by ryota on 2017/03/21.
 */

class AlarmGateway : AlarmRepository {
    fun Alarm.toMap(): Map<String, Any> {
        fun joinedUserToMap(user: JoinedUser): Map<String, Any> = mapOf(
                "name" to user.name,
                "id" to user.id,
                "status" to when (user.status) {
                    JoinedUser.Status.Joined -> 0
                    JoinedUser.Status.Sleeping -> 1
                    JoinedUser.Status.Wokeup -> 2
                })

        fun Alarm.JoinedUsers.toMap() = mapOf(*this.value.map { user -> user.id to user }.toTypedArray())

        return mapOf(
                "title" to title,
                "userId" to userId,
                "duration" to duration,
                "time" to time.time,
                "musicURL" to musicUrl,
                "vibration" to when (this.vibration) {
                    Alarm.Vibration.OFF -> 0
                    Alarm.Vibration.ON -> 1
                },
                "joinedUsers" to joinedUsers.toMap())
    }

    fun Alarm.toMapWithoutJoinedUser(): Map<String, Any> {
        return this.toMap().filter { it.key != "joinedUsers" }
    }

    class AlarmDto {
        lateinit var id: String
        var time: Long? = null
        lateinit var userId: String
        var duration: Int? = null
        lateinit var title: String
        var vibration: Int? = null
        lateinit var musicURL: String
        var joinedUsers: Map<String, JoinedUserDto>? = null
        fun Map<String, JoinedUser>.toList() = this.values.toList()

        override fun toString(): String = "$id, $title, $duration, $userId, $vibration, $musicURL, $joinedUsers"
    }

    class JoinedUserDto {
        lateinit var id: String
        lateinit var name: String
        var status: Int? = null
        fun Int.toStatus(): JoinedUser.Status = when (this) {
            0 -> JoinedUser.Status.Joined
            1 -> JoinedUser.Status.Sleeping
            2 -> JoinedUser.Status.Wokeup
            else -> throw InvalidParameterException()
        }
    }

    fun joinedUserDto2Model(dto: JoinedUserDto): JoinedUser = with(dto) {
        JoinedUser(id = id,
                name = name,
                status = status?.toStatus() ?: throw InvalidParameterException())
    }

    fun alarmDto2Model(dto: AlarmDto): Alarm = with(dto) {
        val vib = vibration ?: throw InvalidParameterException()
        val date = time?.let { it * 1000 }
        try {
            return@with Alarm(Alarm.ID(id), Date(date ?: throw InvalidParameterException()),
                    User.ID(userId), duration ?: throw InvalidParameterException(),
                    Alarm.Title(title), Alarm.Vibration.fromBool(vib == 1), musicURL,
                    Alarm.JoinedUsers(dto.joinedUsers?.values?.toList()?.map(this@AlarmGateway::joinedUserDto2Model) ?: emptyList()))
        } catch(err: Throwable) {
            throw InvalidParameterException(dto.toString())
        }
    }


    override fun getOneById(id: Alarm.ID, onSuccess: (Alarm) -> Unit, onError: (Error) -> Unit): Unit {
        return onError(Error("no impl"))
    }

    override fun subscribeOne(userId: User.ID, id: Alarm.ID, onSuccess: (Alarm) -> Unit, onError: (Error) -> Unit): () -> Unit {
        val ref = FirebaseDatabase.getInstance().getReference("/alarm/${userId.value}/${id.value}")
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                onError(Error(error?.message))
            }

            override fun onDataChange(db: DataSnapshot?) {
                db?.let {
                    if (it.value != null) {
                        onSuccess(alarmDto2Model(it.getValue(AlarmDto::class.java)))
                    }
                }
            }
        }

        ref.addValueEventListener(listener)

        return { ref.removeEventListener(listener) }
    }


    override fun store(alarm: Alarm, onSuccess: (Alarm) -> Unit, onError: (Error) -> Unit): Unit {
        val id = FirebaseDatabase.getInstance().getReference("/alarm/${alarm.userId}").push().key
        val willStoreAlarm = alarm.copy(id = Alarm.ID(id))

        val requestStoreForUser = FirebaseDatabase
                .getInstance()
                .getReference("/user/${alarm.userId}/alarms/$id")
                .updateChildren(willStoreAlarm.toMapWithoutJoinedUser())

        requestStoreForUser.addOnSuccessListener {
            val requestStore = FirebaseDatabase.getInstance()
                    .getReference("/alarm/${alarm.userId}/$id")
                    .updateChildren(willStoreAlarm.toMap())

            requestStore.addOnFailureListener { onError(Error(it.message)) }
            requestStore.addOnSuccessListener { onSuccess(willStoreAlarm) }
        }
        requestStoreForUser.addOnFailureListener {
            onError(Error(it.message))
        }

    }

    override fun getListByUserId(id: User.ID, onSuccess: (List<Alarm>) -> Unit, onError: (Error) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference("/user/${id.value}/alarms")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                Log.d("onCancelled", error.toString())
                onError(Error())
            }

            override fun onDataChange(db: DataSnapshot) {
                val list = db.children.map { child ->
                    alarmDto2Model(child.getValue(AlarmDto::class.java))
                }.toList()

                onSuccess(list)
            }
        })
    }
}