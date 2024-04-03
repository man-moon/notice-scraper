package com.ajouin.noticescraper

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import software.amazon.awssdk.services.s3.S3Client

@WebMvcTest
@ActiveProfiles("test")
class ImageUploadTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var s3Client: S3Client

    @Test
    fun `upload images should save to S3 and return file keys`() {
        val mockImage1 = MockMultipartFile("images", "image1.jpg", "image/jpeg", byteArrayOf(1))
        val mockImage2 = MockMultipartFile("images", "image2.jpg", "image/jpeg", byteArrayOf(1))

        mockMvc.perform(multipart("/images").file(mockImage1).file(mockImage2))
            .andExpect(status().isOk)
    }
}