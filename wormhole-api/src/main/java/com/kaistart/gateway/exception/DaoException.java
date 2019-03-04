package com.kaistart.gateway.exception;


/**
 * 业务异常基类，所有业务异常都必须继承于此异常
 * 定义异常时，需要先确定异常所属模块。例如：数据库操作insert报错 可以定义为 [901001] 前3位数为系统模块编号，后3位为错误代码 ,唯一 <br>
 *         基础支撑服务异常 101 <br>
 *         用户工程异常 102 <br>
 * @author hewei
 *  
 */
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;
	
	/**
	 * 数据库操作,insert返回0
	 */
	public static final DaoException DB_INSERT_RESULT_0 = new DaoException(901001, "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final DaoException DB_UPDATE_RESULT_0 = new DaoException(901002, "数据库操作,update返回0");
	
	/**
     * 数据库操作,delete返回0
     */
    public static final DaoException DB_DELETE_RESULT_0 = new DaoException(901003, "数据库操作,delete返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final DaoException DB_SELECTONE_IS_NULL = new DaoException(901004, "数据库操作,selectOne返回null");

	/**
	 * 数据库操作,list返回null
	 */
	public static final DaoException DB_LIST_IS_NULL = new DaoException(901005, "数据库操作,list返回null");
	
	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public DaoException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public DaoException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message) {
		super(message);
	}
	
	/**
     * 实例化异常
     * 
     * @param msgFormat
     * @param args
     * @return
     */
    public DaoException newInstance(String msgFormat, Object... args) {
        return new DaoException(this.code, msgFormat, args);
    }
    
    public DaoException print() {
        return new DaoException(this.code, this.msg);
    }
}
