package com.skl.foodrecipeapp.common.data.repository.utils

import android.util.Log
import com.skl.foodrecipeapp.common.data.repository.RecipesRepositoryImpl

fun RecipesRepositoryImpl.listToString(list: List<String>): String {
    if (list.isEmpty()) {
        return ""
    }
    return list.joinToString(separator = ",")
}

fun RecipesRepositoryImpl.getTag(
    item: String,
    list1: List<String>,
    list2: List<String>
): String {
    return if(item.isEmpty() && list1.isEmpty() && list2.isEmpty()){
        ""
    }else {
        val sb = StringBuilder()
        if(item.isNotEmpty()){
            sb.append(item)
        }
        if(list1.isNotEmpty()){
            if(sb.isNotEmpty()){
                sb.append(",")
            }
            sb.append(listToString(list1))
        }
        if (list2.isNotEmpty()){
            if(sb.isNotEmpty()){
                sb.append(",")
            }
            sb.append(listToString(list2))
        }
        sb.toString()
    }
}
