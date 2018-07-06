package karrel.com.mvvmsample.extensions

import io.reactivex.disposables.Disposable

operator fun AutoClearedDisposable.plusAssign(disposable: Disposable) = this.add(disposable)
