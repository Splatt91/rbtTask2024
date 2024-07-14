package com.vacation_tracker.admin.util

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

fun createBufferedReaderForFile(file:MultipartFile): BufferedReader {
    val inputStreamReader = InputStreamReader(file.inputStream)
    return BufferedReader(inputStreamReader)
}

inline fun <reified T> parseFile(csvMapper: CsvMapper,
                                 bufferedReader: BufferedReader,
                                 mapFunction: (T) -> T): Set<T> {
    val schema = CsvSchema.emptySchema().withHeader()
    val result: List<T> = csvMapper.readerFor(T::class.java)
        .with(schema)
        .readValues<T>(bufferedReader)
        .readAll()
        .map { mapFunction(it) }
    return result.toSet()
}