package com.skl.foodrecipeapp.common.data.repository

import com.skl.foodrecipeapp.common.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <ResultType, RequestType>networkBoundResource(
    crossinline query: ()-> Flow<ResultType>,
    crossinline saveToDB: suspend (ResultType) -> Unit,
    crossinline fetchCall: suspend () -> RequestType,
    crossinline shouldFetch: (RequestType) -> Boolean = { true }
) =
    flow<List<Recipe>> {
        val data = query().first()

        val flow = if(shouldFetch(fetchCall())){
            try {

            }
            catch (e: Exception){

            }
        }
        else{

        }

        //emitAll(flow)
    }