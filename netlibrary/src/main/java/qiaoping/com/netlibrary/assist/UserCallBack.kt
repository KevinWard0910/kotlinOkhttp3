package qiaoping.com.netlibrary.assist

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:用户的请求回调
 */
 interface UserCallBack {
    /**
     * 请求执行前监听响应,UI线程(可以设置请求进度条/等待框等)
     */
     fun start(){}

    /**
     * 请求成功监听响应,UI线程
     */
     fun onSuccess(result:ResponseData)

    /**
     * 执行过程中监听响应，仅用于文件上传、下载过程中的进度条加载显示，UI线程
     *@param result
     */
     fun onFailure(result: ResponseData)

    /**
     * 执行过程中监听响应，仅用于文件上传、下载过程中的进度条加载显示，UI线程
     * @param count
     * @param current
     */
     fun onLoading(count:Long,current:Long)

}