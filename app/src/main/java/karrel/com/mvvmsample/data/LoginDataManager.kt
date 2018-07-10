package karrel.com.mvvmsample.data

import io.reactivex.Maybe
import io.reactivex.Maybe.create


/**
 * Created by Rell on 2018. 7. 6..
 */
class LoginDataManager {

    fun login(id: String, password: String): Maybe<Boolean> {
        return create<Boolean> { emitter ->
            Thread.sleep(2000)
            emitter.onSuccess(true)
        }
    }

}