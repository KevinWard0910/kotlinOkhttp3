package qiaoping.com.netlibrary.assist

open class ResponseData {
    /**返回码 */
    var code: String? = ""
    /**消息提示 */
    var message: String? = ""
    /**返回对象 */
    var data: Any? = ""
    /**请求结果 */
    var status: Boolean = false
    /**总记录数,针对分页 */
    //    public int countRecord;
    var hasNextPage: Boolean = false

    constructor()
    constructor(status: Boolean, errorMsg: String?, data: Any?){
        this.status = status
        this.message=errorMsg
        this.data=data
    }
}
