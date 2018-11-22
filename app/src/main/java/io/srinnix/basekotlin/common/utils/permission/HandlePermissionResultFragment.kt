package io.srinnix.basekotlin.common.utils.permission

import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import io.reactivex.subjects.PublishSubject

class HandlePermissionResultFragment : Fragment() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private val mRequestSubjects: HashMap<String, PublishSubject<Permission>> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun request(permissions: Array<out String>) {
        requestPermissions(permissions, PERMISSION_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            permissions.forEachIndexed { index, permission ->
                val permissionSubject = mRequestSubjects[permission]
                val permissionResult = if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    Permission(permission, true, false)
                } else {
                    Permission(permission, false, !shouldShowRequestPermissionRationale(permission))
                }
                permissionSubject?.onNext(permissionResult)
                permissionSubject?.onComplete()
                mRequestSubjects.remove(permission)
            }
        }
    }

    fun addPermissionSubject(permission: String, subject: PublishSubject<Permission>) {
        mRequestSubjects[permission] = subject
    }

    fun getPermissionSubject(permission: String): PublishSubject<Permission>? = mRequestSubjects[permission]

    @RequiresApi(Build.VERSION_CODES.M)
    fun isGranted(permission: String): Boolean {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}