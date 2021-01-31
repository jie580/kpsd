package com.ming.sjll.search;

import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;

public interface OccupationImplements {

    public void searchKey(HttpParamsObject param, boolean isSearch, CommonCallback cb, HttpParamsObject itemClickParam);
}
