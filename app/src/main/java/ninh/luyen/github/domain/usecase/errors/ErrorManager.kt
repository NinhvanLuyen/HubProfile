package ninh.luyen.github.domain.usecase.errors

import ninh.luyen.github.data.error.Error
import ninh.luyen.github.data.error.ErrorMapper
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}