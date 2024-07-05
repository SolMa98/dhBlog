package com.dhkim.blog.util;

import java.util.Map;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class PaginationUtils {
    private static final Logger log = LoggerFactory.getLogger(PaginationUtils.class);
    private boolean nowFirst;
    private boolean nowEnd;
    private int newArticleCount;
    private long totalCount;
    private long totalPageCount;
    private long pageNo;
    private int naviSize;
    private long preLastPage;
    private long startPage;
    private long endPage;
    private String navigator;
    private long totalPages;

    public void setField(long listSize, int naviSize, long page, long totalCount, long totalPages) {
        long totalPageCount = (totalCount - 1L) / listSize + 1L;
        boolean nowFirst = page <= (long)naviSize;
        boolean nowEnd = (totalPageCount - 1L) / (long)naviSize * (long)naviSize < page;
        long preLastPage = (page - 1L) / (long)naviSize * (long)naviSize;
        long startPage = preLastPage + 1L;
        long endPage = preLastPage + (long)naviSize;
        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        this.totalCount = totalCount;
        this.totalPageCount = totalPageCount;
        this.nowFirst = nowFirst;
        this.nowEnd = nowEnd;
        this.pageNo = page;
        this.naviSize = naviSize;
        this.preLastPage = preLastPage;
        this.startPage = startPage;
        this.endPage = endPage;
        this.totalPages = totalPages;
    }

    public void makeNavigator() {
        StringBuffer navigator = new StringBuffer();
        if (this.isNowFirst()) {
            navigator.append("<li><a class='first-page pagination page-active' data-page='0'></a></li> \n");
            navigator.append("<li><a class='prev-page pagination page-active' data-page=" + this.preLastPage + "></a></li> \n");
        } else {
            navigator.append("<li><a class='first-page pagination page-active' data-page='0'></a></li> \n");
            navigator.append("<li><a class='prev-page pagination page-active' data-page=" + (this.preLastPage - 10L < 0L ? 0L : this.preLastPage - 10L) + "></a></li> \n");
        }

        for(long i = this.startPage; i <= this.endPage; ++i) {
            if (this.pageNo == i) {
                navigator.append("<li><a class='num pagination page-active current' data-page=" + (i - 1L) + ">" + i + "</a></li> \n");
            } else {
                navigator.append("<li><a class='num pagination page-active' data-page=" + (i - 1L) + ">" + i + "</a></li> \n");
            }
        }

        if (this.isNowEnd()) {
            navigator.append("<li><a class='next-page pagination disable' disable='true'></a></li>");
            navigator.append("<li><a class='last-page pagination disable' disable='true'></a></li>");
        } else {
            navigator.append("<li><a class='next-page pagination page-active' data-page=" + (this.preLastPage + (long)this.naviSize) + "></a></li>");
            navigator.append("<li><a class='last-page pagination page-active' data-page=" + (this.totalPages - 1L) + "></a></li>");
        }

        this.navigator = navigator.toString();
    }

    public static PaginationUtils getPagination(long size, long page, long totalCount, long totalPages) {
        try {
            PaginationUtils pagination = new PaginationUtils();
            ++page;
            pagination.setField(size, 10, page, totalCount, totalPages);
            pagination.makeNavigator();
            return pagination;
        } catch (Exception var11) {
            log.error("Make Pagination Error => " + var11.getMessage());
            return null;
        }
    }
}