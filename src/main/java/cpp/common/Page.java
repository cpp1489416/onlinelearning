package cpp.common;

import java.util.LinkedList;
import java.util.List;

// page 工具，前端返回pageSize和pageIndex，后端设置totalCount，其他字段会自动设置
public class Page<T> {
    int pageSize = 5; // 每一页的数量
    int pageIndex; // 第几页
    int itemsTotalCount; // 总记录数量
    int pageTotalCount; // 总页数

    boolean isFirstPage;
    boolean isLastPage;

    int startIndex; // 一个页面第一个元素的位置
    int endIndex; // 一个页面最后一个元素的位置
    List<T> items = new LinkedList<>();

    int dotCount = 10;
    List<Integer> dots = new LinkedList<>();
    boolean isEmpty;
    String orderBy;
    boolean ascendingOrder = true;


    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            pageSize = 1;
        }
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getItemsTotalCount() {
        return itemsTotalCount;
    }

    public void setItemsTotalCount(int itemsTotalCount) {
        if (itemsTotalCount == 0) {
            isEmpty = true;
        }
        this.itemsTotalCount = itemsTotalCount;
        this.pageTotalCount = itemsTotalCount / this.pageSize + (this.itemsTotalCount % this.pageSize != 0 ? 1 : 0);
        dots = new LinkedList<>();
        int startIndex;
        int endIndex;
        if (pageIndex - dotCount / 2 > 1) {
            startIndex = pageIndex - dotCount / 2;
            endIndex = pageIndex + dotCount / 2 - 1;
            if (endIndex > pageTotalCount) {
                endIndex = pageTotalCount;
                startIndex = endIndex - dotCount + 1;
            }
        } else {
            startIndex = 1;
            endIndex = pageTotalCount <= dotCount ? pageTotalCount : dotCount;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            if (i <= 0) {
                continue;
            }
            this.dots.add(Integer.valueOf(i));
        }
        if (this.isFirstPage || this.isLastPage) {
            //showDot = false;
        } else {
            if (dots.size() > 0) {
                if (dots.get(dots.size() - 1) == this.pageTotalCount) {
                    // showDot = false;
                }
            }
        }

        updateStartIndexAndEndIndexByPageIndexAndItemsTotalCount();

        if (pageIndex == 1) {
            isFirstPage = true;
        }
        if (this.endIndex >= itemsTotalCount - 1) {
            isLastPage = true;
        }
    }

    public void updateStartIndexAndEndIndexByPageIndexAndItemsTotalCount() {
        this.startIndex = (this.pageIndex - 1) * this.pageSize;
        if (this.startIndex <= 0) {
            this.startIndex = 0;
        }
        this.endIndex = startIndex + pageSize - 1;
        endIndex = endIndex >= itemsTotalCount - 1 ? itemsTotalCount - 1 : endIndex;
    }

    public int getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(int pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getDotCount() {
        return dotCount;
    }

    public void setDotCount(int dotCont) {
        this.dotCount = dotCont;
    }

    public List<Integer> getDots() {
        return dots;
    }

    public void setDots(List<Integer> dots) {
        this.dots = dots;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAscendingOrder() {
        return ascendingOrder;
    }

    public void setAscendingOrder(boolean ascendingOrder) {
        this.ascendingOrder = ascendingOrder;
    }
}
