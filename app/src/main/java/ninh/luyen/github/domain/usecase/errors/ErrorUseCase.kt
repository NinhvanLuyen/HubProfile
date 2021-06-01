package ninh.luyen.github.domain.usecase.errors

import ninh.luyen.github.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
