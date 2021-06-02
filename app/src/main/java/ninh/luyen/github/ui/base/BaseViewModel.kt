package ninh.luyen.github.ui.base

import androidx.lifecycle.ViewModel
import ninh.luyen.github.domain.usecase.errors.ErrorManager
import javax.inject.Inject


/**
 * Created by luyen_ninh on 01/06/2021
 */


abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    @Inject
    lateinit var errorManager: ErrorManager
}
