package karrel.com.mvvmsample.viewmodel

import io.reactivex.subjects.PublishSubject

/**
 * Created by Rell on 2018. 7. 6..
 */
object ProgressViewModelImpl {
    val input = Input()
    val output = Output()

    class Input : ProgressViewModel.Input {
        override fun hideProgress() {
            output.progressObservable().onNext(false)
        }

        override fun showProgress() {
            output.progressObservable().onNext(true)
        }
    }

    class Output : ProgressViewModel.Output {
        private val progressObservable = PublishSubject.create<Boolean>()
        override fun progressObservable(): PublishSubject<Boolean> {
            return progressObservable
        }
    }
}