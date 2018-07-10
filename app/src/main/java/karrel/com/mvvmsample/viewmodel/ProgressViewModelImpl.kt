package karrel.com.mvvmsample.viewmodel

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Rell on 2018. 7. 6..
 */
object ProgressViewModelImpl : ProgressViewModel {
    override val input = Input()
    override val output = Output()
    private val progressObservable = PublishSubject.create<Boolean>()

    class Input : ProgressViewModel.Input {
        override fun hideProgress() {
            progressObservable.onNext(false)
        }

        override fun showProgress() {
            progressObservable.onNext(true)
        }
    }

    class Output : ProgressViewModel.Output {

        override fun progressObservable(): Observable<Boolean> {
            return progressObservable
        }
    }
}