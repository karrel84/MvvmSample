package karrel.com.mvvmsample.viewmodel

import io.reactivex.Observable

/**
 * Created by Rell on 2018. 7. 6..
 */
interface ProgressViewModel {
    val input: Input
    val output: Output

    interface Input {
        fun showProgress()
        fun hideProgress()

    }

    interface Output {
        fun progressObservable(): Observable<Boolean>
    }
}