package com.github.xionghuicoder.microservice.common.bean.enums;

/**
 * HTTP结果枚举类，<tt>code</tt>为<tt>0</tt>表示请求成功
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public enum HttpResultEnum implements IHttpResultEnum {
  Success(0, "common-m00000"), // 请求成功
  InsertSuccess(0, "common-m00001"), // 新增成功
  UpdateSuccess(0, "common-m00002"), // 修改成功
  DeleteSuccess(0, "common-m00003"), // 删除成功
  BatchDeleteSuccess(0, "common-m00004"), // 批量删除{0}条数据成功
  QuerySuccess(0, "common-m00005"), // 查询成功
  DownloadSuccess(0, "common-m00006"), // 下载成功
  UploadSuccess(0, "common-m00007"), // 上传成功
  ImportSuccess(0, "common-m00008"), // 导入成功
  ExportSuccess(0, "common-m00009"), // 导出成功

  Failed(2, "common-m10001"), // 请求失败
  Throwable(4, "common-m10002"), // 发生异常
  NotExistedError(6, "common-m10003"), // 原数据不存在
  UpdateWhileUpdatedDeletedFail(8, "common-m10004"), // 修改失败,数据被删除或被修改
  UpdateWhileUpdated(10, "common-m10005"), // 修改失败,数据有变动
  DeleteWhileUpdated(12, "common-m10006"), // 删除失败,数据有变动
  DeleteNotExistedError(14, "common-m10007"), // 删除失败,数据不存在或者被修改
  BatchDeleteOriginsNullError(16, "common-m10008"), // 批量操作失败:origins为null
  BatchDeleteWhileChangeError(18, "common-m10009"), // 批量删除失败:数据有变动
  UndefinedError(20, "common-m10010"), // 未定义错误
  DownloadFailed(22, "common-m10011"), // 下载失败
  UploadFailed(24, "common-m10012"), // 上传失败
  ImportFailed(26, "common-m10013"), // 导入失败
  ExportFailed(28, "common-m10014"), // 导出失败
  BatchInsertFailed(30, "common-m10015"), // 批量新增失败

  UploadNullFileError(50, "common-m20011"), // 上传文件为空
  FunctionLackError(52, "common-m20012"), // 缺少function参数
  BodyFormatError(54, "common-m20013"), // body格式不正确
  BodyLackError(56, "common-m20014"), // 缺少body参数
  PermissionsLackError(58, "common-m20015"), // 缺少权限信息
  UserLackError(60, "common-m20016"), // 缺少用户信息
  ParamsError(62, "common-m20017"), // 参数错误
  NullControllerError(64, "common-m20018"), // 未找到对应服务
  NotSupportController(66, "common-m20019"), // 服务不支持调用
  RequestMethodError(68, "common-m20020"), // 请求方式不合法
  ReturnNullError(70, "common-m20021"), // 返回结果为空
  ParamsCreatorNullError(72, "common-m20022"), // 创建人为空
  ParamsUpdateNullError(74, "common-m20023"), // 修改人为空
  ParamsUuidNullError(76, "common-m20024"), // uuid为null
  VersionNullError(78, "common-m20025"), // version为null
  UpdateDiffNullError(80, "common-m20026"), // 修改参数错误:diff为null
  UpdateOriginEmptyError(82, "common-m20027"), // 修改参数错误:origin为空
  CallServiceFailed(84, "common-m20028"), // 调用服务失败
  NoAclMenuRightError(86, "common-m20029"), // 没有操作该菜单权限
  ;
  public final int code;
  public final String languageCode;

  private HttpResultEnum(int code, String languageCode) {
    this.code = code;
    this.languageCode = languageCode;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getLanguageCode() {
    return this.languageCode;
  }
}
