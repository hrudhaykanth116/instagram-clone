package com.hrudhaykanth116.instagramclone.data.repository.repositories

import androidx.annotation.MainThread
import java.util.concurrent.Executor
import javax.inject.Singleton

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 *
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */

/** https://github.com/android/architecture-components-samples/tree/master/GithubBrowserSample */
// TODO: 13/06/21 Use this where applicable

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor(
    private val appExecutors: AppExecutors
) {


    // private val result = MediatorLiveData<Resource<ResultType?>>()
    //
    // init {
    //    // Send loading state to UI
    //    result.value = Resource.loading()
    //    val dbSource = this.loadFromDb()
    //    result.addSource(dbSource) { data ->
    //        result.removeSource(dbSource)
    //        if (shouldFetch(data)) {
    //            fetchFromNetwork(dbSource)
    //        } else {
    //            result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
    //        }
    //    }
    // }
    //
    //
    // /**
    // * Fetch the data from network and persist into DB and then
    // * send it back to UI.
    // */
    //
    // private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
    //    val apiResponse = createCall()
    //    // we re-attach dbSource as a new source, it will dispatch its latest value quickly
    //    result.addSource(dbSource) { result.setValue(Resource.loading()) }
    //    result.addSource(apiResponse) { response ->
    //        result.removeSource(apiResponse)
    //        result.removeSource(dbSource)
    //
    //        response?.apply {
    //            if (isSuccessful) {
    //                appExecutors.diskIO().execute {
    //                    processResponse(this)?.let { requestType -> saveCallResult(requestType) }
    //                    appExecutors.mainThread().execute {
    //                        // we specially request a new live data,
    //                        // otherwise we will get immediately last cached value,
    //                        // which may not be updated with latest results received from network.
    //                        result.addSource(loadFromDb()) { newData ->
    //                            setValue(
    //                                Resource.success(
    //                                    newData
    //                                )
    //                            )
    //                        }
    //                    }
    //                }
    //            } else {
    //                onFetchFailed()
    //                result.addSource(dbSource) {
    //                    result.setValue(
    //                        Resource.error(
    //                            "Error message", error = null
    //                        )
    //                    )
    //                }
    //            }
    //        }
    //    }
    // }
    //
    // @MainThread
    // private fun setValue(newValue: Resource<ResultType?>) {
    //    if (result.value != newValue) result.value = newValue
    // }
    //
    // protected fun onFetchFailed() {}
    //
    // fun asLiveData(): LiveData<Resource<ResultType?>> {
    //    return result
    // }
    //
    // @WorkerThread
    // private fun processResponse(response: Response<RequestType>): RequestType? {
    //    return response.body()
    // }
    //
    // @WorkerThread
    // protected abstract fun saveCallResult(item: RequestType)
    //
    // @MainThread
    // protected abstract fun shouldFetch(data: ResultType?): Boolean
    //
    // @MainThread
    // protected abstract fun loadFromDb(): LiveData<ResultType>
    //
    // @MainThread
    // protected abstract fun createCall(): LiveData<Response<RequestType>>
}

@Singleton
open class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    //@Inject
    //constructor() : this(
    //    Executors.newSingleThreadExecutor(),
    //    Executors.newFixedThreadPool(3),
    //    MainThreadExecutor()
    //)
    //
    //fun diskIO(): Executor {
    //    return diskIO
    //}
    //
    //fun networkIO(): Executor {
    //    return networkIO
    //}
    //
    //fun mainThread(): Executor {
    //    return mainThread
    //}
    //
    //private class MainThreadExecutor : Executor {
    //    private val mainThreadHandler = Handler(Looper.getMainLooper())
    //    override fun execute(command: Runnable) {
    //        mainThreadHandler.post(command)
    //    }
    //}
}
