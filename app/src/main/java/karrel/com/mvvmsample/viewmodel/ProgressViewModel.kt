package karrel.com.mvvmsample.viewmodel

import io.reactivex.subjects.PublishSubject

/**
 * Created by Rell on 2018. 7. 6..
 */
interface ProgressViewModel{
    interface Input{
        fun showProgress()
        fun hideProgress()

    }
    interface Output{
        fun progressObservable() : PublishSubject<Boolean>
    }
}