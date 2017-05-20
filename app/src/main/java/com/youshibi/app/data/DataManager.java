package com.youshibi.app.data;


import com.youshibi.app.data.bean.Book;
import com.youshibi.app.data.bean.BookType;
import com.youshibi.app.data.bean.DataList;
import com.youshibi.app.data.net.RequestClient;
import com.youshibi.app.rx.HttpResultFunc;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;

/**
 * Created by zchu on 16-11-17.
 * <p>
 * 负责提供数据，这里采用了 Repository 模式，DataManager 就是一个仓库管理员，业务层 需要什么东西只需告诉仓库管理员，由仓库管理员把东西拿给它，并不需要知道东西实际放在哪。
 * 常见的数据来源有，RestAPI、SQLite数据库、本地缓存等
 * <p>
 * ps：有些时候，访问数据压根就涉及不到什么业务逻辑，如：请求数据给一个列表展示，这就压根没有业务逻辑
 * 这时present直接访问数据层就可以了，当然最好还是写一个业务逻辑类，什么也不干，只是转发一下数据，以后突然有业务逻辑了，就只要关注这个业务逻辑类就可以了
 */

public class DataManager {

    private static final DataManager sInstance = new DataManager();

    private DataManager() {
    }

    public static DataManager getInstance() {
        return sInstance;
    }

    public Observable<DataList<Book>> getBookList(int page, int size) {
        return getBookList(page, size, 0);
    }

    public Observable<DataList<Book>> getBookList(int page, int size, long bookType) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pageIndex", page);
        hashMap.put("pageSize", size);
        if(bookType>0) hashMap.put("bookType", bookType);
        return RequestClient
                .getServerAPI()
                .getBookList(hashMap)
                .map(new HttpResultFunc<DataList<Book>>());
    }

    public Observable<ArrayList<BookType>> getBookType() {
        return RequestClient
                .getServerAPI()
                .getBookType()
                .map(new HttpResultFunc<ArrayList<BookType>>());
    }
}