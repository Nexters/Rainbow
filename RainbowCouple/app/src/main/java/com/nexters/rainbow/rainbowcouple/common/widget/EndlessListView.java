package com.nexters.rainbow.rainbowcouple.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 무한 스크롤 리스트 뷰
 * 아래로 스크롤 할 때 마다 새로운 데이터를 불러온다.
 */
public class EndlessListView extends ListView implements AbsListView.OnScrollListener{

    private int nextPageNumber = 0;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private RequestNextDataCallback requestNextDataCallback = null;

    public interface RequestNextDataCallback {
        void loadNextData();
    }

    public EndlessListView(Context context) {
        super(context);
        initialize();
    }

    public EndlessListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public EndlessListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isLastViewItem(firstVisibleItem, visibleItemCount, totalItemCount)
                && !isLoading) {

            if (requestNextDataCallback != null) {
                isLoading = true;
                requestNextDataCallback.loadNextData();
            }
        }
    }

    private boolean isLastViewItem(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int totalVisibleItemCount = firstVisibleItem + visibleItemCount;

        return (totalItemCount != 0) && (totalVisibleItemCount == totalItemCount);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void initData() {
        this.nextPageNumber = 0;
        this.isLastPage = false;
        this.isLoading = false;
    }

    public void setPageData(int nextPage, boolean isLastPage) {
        this.nextPageNumber = nextPage;
        this.isLastPage = isLastPage;
        this.isLoading = false;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public int getNextPageNumber() {
        return nextPageNumber;
    }

    public void setRequestNextDataCallback(RequestNextDataCallback requestNextDataCallback) {
        this.requestNextDataCallback = requestNextDataCallback;
    }
}
