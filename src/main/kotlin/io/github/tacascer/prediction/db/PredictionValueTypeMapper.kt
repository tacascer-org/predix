package io.github.tacascer.prediction.db

import io.github.tacascer.prediction.Prediction
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface PredictionValueTypeMapper {
    fun toEntity(prediction: Prediction): PredictionValueType

    fun toDto(predictionValueType: PredictionValueType): Prediction

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun partialUpdate(
        prediction: Prediction,
        @MappingTarget predictionValueType: PredictionValueType,
    ): PredictionValueType
}
