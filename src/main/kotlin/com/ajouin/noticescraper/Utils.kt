package com.ajouin.noticescraper

class Utils {
    companion object {
        fun getPostId(link: String): Long {
            val idPatterns = listOf("articleNo=", "article_no=", "num=")

            idPatterns.forEach { pattern ->
                link.substringAfter(pattern, "").substringBefore("&", "").toLongOrNull()?.let {
                    return it
                }
            }

            throw Exception("공지사항 id를 찾을 수 없습니다.")
        }
    }
}
