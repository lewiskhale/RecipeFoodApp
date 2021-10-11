package com.skl.foodrecipeapp.common.domain.mapper

import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe.*
import com.skl.foodrecipeapp.common.domain.model.Recipe.*
import javax.inject.Inject

class InstructionMapper @Inject constructor(): Mapper<CacheInstruction, Instruction> {

    override fun toDomain(cacheModel: CacheInstruction): Instruction {
        return parseInstructions(cacheModel)
    }

    private fun parseInstructions(instruction: CacheRecipe.CacheInstruction) =
        Instruction(
            number = instruction.number,
            instruction = instruction.instruction,
            equipment = instruction.equipment.map { parseEquipment(it) }
        )


    private fun parseEquipment(it: CacheRecipe.CacheInstruction.CacheEquipment)=
        Instruction.Equipment(
            localizedName = it.localizedName,
            name = it.name
        )

    override fun fromDomain(domainModel: Instruction): CacheInstruction {
        return parseToCacheInstructions(domainModel)
    }

    private fun parseToCacheInstructions(instruction: Instruction) =
        CacheInstruction(
            number = instruction.number,
            instruction = instruction.instruction,
            equipment = instruction.equipment.map { parseToCacheEquipment(it) }
        )


    private fun parseToCacheEquipment(it: Instruction.Equipment)=
        CacheInstruction.CacheEquipment(
            localizedName = it.localizedName,
            name = it.name
        )
}