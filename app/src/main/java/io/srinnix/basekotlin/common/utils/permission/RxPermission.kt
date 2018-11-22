package io.srinnix.basekotlin.common.utils.permission

import android.app.Activity
import android.os.Build
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Examples:
 */
//val rxPermission = RxPermission(activity)
//
//rxPermission
//.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//.subscribe { (areGrantedAll, notGrantedPermissions) ->
//    if (areGrantedAll) {
//        Log.d("Status", "Permissions are granted all")
//    } else {
//        Log.d("Status", "Some permission is not granted")
//        notGrantedPermissions.forEach {
//            Log.d("Permission", it.permission)
//        }
//    }
//}
class RxPermission constructor(activity: Activity?) {

    private val TAG = HandlePermissionResultFragment.toString()

    private var requestFragment: HandlePermissionResultFragment

    init {
        val fragment = activity?.fragmentManager?.findFragmentByTag(TAG)
        if (fragment == null) {
            requestFragment = HandlePermissionResultFragment()
            activity
                    ?.fragmentManager
                    ?.beginTransaction()
                    ?.add(requestFragment, TAG)
                    ?.commitAllowingStateLoss()
        } else {
            requestFragment = fragment as HandlePermissionResultFragment
        }
    }

    fun request(vararg permissions: String): Observable<Pair<Boolean, List<Permission>>> {
        val permissionObservables = arrayListOf<Observable<Permission>>()
        val unrequestedPermission = arrayListOf<String>()
        permissions.forEach { permission ->
            if (isGranted(permission)) {
                permissionObservables.add(Observable.just(Permission(permission, true, false)))
            } else {
                var subject = requestFragment.getPermissionSubject(permission)
                if (subject == null) {
                    subject = PublishSubject.create<Permission>()
                }
                requestFragment.addPermissionSubject(permission, subject!!)
                permissionObservables.add(subject)
                unrequestedPermission.add(permission)
            }
        }

        if (unrequestedPermission.isNotEmpty() && !beforeAndroid6()) {
            requestFragment.request(unrequestedPermission.toTypedArray())
        }

        return Observable.zip(permissionObservables.asIterable()) { result ->
            val permissionResult = result.map { it as Permission }
            val grantedAll = permissionResult.all { it.granted }
            if (grantedAll) {
                Pair(grantedAll, listOf())
            } else {
                val permissionsNotGranted = permissionResult.filter { !it.granted }
                Pair(grantedAll, permissionsNotGranted)
            }
        }
    }

    private fun isGranted(permission: String): Boolean {
        if (beforeAndroid6()) return true

        return requestFragment.isGranted(permission)
    }

    private fun beforeAndroid6(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }
}