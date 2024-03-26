package com.ajouin.noticescraper

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

@RestController
class TestController(
    @Value("\${s3.bucket-name}")
    val bucket: String,
    @Value("\${spring.cloud.aws.region.static}")
    val region: String,
    val s3Client: S3Client,
) {

    @PostMapping("/images")
    fun uploadImage(@RequestParam("images") files: List<MultipartFile>): List<String> {
        if(files.any { it.isEmpty }) {
            throw IllegalArgumentException("하나 이상의 파일이 존재하지 않습니다")
        }
        val uploadedFileKeys = mutableListOf<String>()
        files.forEach { file ->
            val fileKey = UUID.randomUUID().toString()
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileKey)
                .contentType(file.contentType)
                .build()
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.inputStream, file.size))

            uploadedFileKeys.add(fileKey)
        }
        return uploadedFileKeys.map { key -> "https://$bucket.s3.$region.amazonaws.com/$key" }
    }
}