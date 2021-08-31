# es-demo

**es 测试项目**


**简介**：<p>测试es</p>


**HOST**:localhost:8989


**联系人**:


**Version**:1.0

**接口路径**：/v2/api-docs


# es

## 创建索引

**接口描述**:kevin

**接口地址**:`/index/createIndex`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：
暂无



**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 删除

**接口描述**:kevin

**接口地址**:`/index/delete`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"id": "",
	"pid": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqIdDto  | ReqIdDto   |

**schema属性说明**



**ReqIdDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|id| id  | body | false |string  |    |
|pid| 段落id  | body | false |string  |    |

**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 通过内容id,保存单个数据

**接口描述**:kevin

**接口地址**:`/index/saveOne`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"id": "",
	"pid": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqIdDto  | ReqIdDto   |

**schema属性说明**



**ReqIdDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|id| id  | body | false |string  |    |
|pid| 段落id  | body | false |string  |    |

**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 将数据库存入es

**接口描述**:kevin

**接口地址**:`/index/saveToes`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：
暂无



**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 分页

**接口描述**:kevin

**接口地址**:`/index/scroll`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"page": 0,
	"sid": "",
	"size": 0,
	"text": "",
	"type": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqScrollerDto  | ReqScrollerDto   |

**schema属性说明**



**ReqScrollerDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|page| 当前页数  | body | false |integer(int32)  |    |
|sid| 分页id  | body | false |string  |    |
|size| 每页数量  | body | false |integer(int32)  |    |
|text| 查询内容  | body | false |string  |    |
|type| 所属分类 1 全能校长 2 云端知识库   9全部    | body | false |string  |    |

**响应示例**:

```json
{
	"cloudContentList": [
		{
			"content": "",
			"contentId": "",
			"contentIntroduction": "",
			"contentName": "",
			"contentSonType": "",
			"coverImage": "",
			"modleName": "",
			"pageTemplateType": "",
			"sheetStatus": "",
			"templateId": ""
		}
	],
	"dettContentList": [
		{
			"content": "",
			"contentId": "",
			"contentIntroduction": "",
			"contentName": "",
			"contentSonType": "",
			"coverImage": "",
			"modleName": "",
			"pageTemplateType": "",
			"sheetStatus": "",
			"templateId": ""
		}
	],
	"flush": true,
	"list": [
		{
			"auditStatus": "",
			"content": [
				{
					"content": "",
					"id": ""
				}
			],
			"contentSonType": "",
			"coursewareId": "",
			"coursewareName": "",
			"coverImage": "",
			"templateType": ""
		}
	],
	"sid": ""
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|cloudContentList|   |array  | 首页搜索模型   |
|dettContentList|   |array  | 首页搜索模型   |
|flush| 分页过期刷新页面 true 刷新 false 不刷新  |boolean  |    |
|list| 全部搜索结果  |array  | 课件   |
|sid| 分页id  |string  |    |



**schema属性说明**




**首页搜索模型**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|content | 课件内容   |string  |    |
|contentId |    |string  |    |
|contentIntroduction | 简介   |string  |    |
|contentName | 内容名称/标题   |string  |    |
|contentSonType | 内容下级类型(1分类 2课件)   |string  |    |
|coverImage | 封面图   |string  |    |
|modleName | 模块名称   |string  |    |
|pageTemplateType | 过渡页模板类型(0无 1一类 2二类 3三类)   |string  |    |
|sheetStatus | 是否支持定制(0否 1是)   |string  |    |
|templateId | 定制表单模版Id    |string  |    |

**课件**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|auditStatus | 审核状态(0待提交 1审核中 2已退回 3待上架 4上架中 5已下架)   |string  |    |
|content | 内容   |array  | EsContent   |
|contentSonType | 内容下级类型(1分类 2课件)   |string  |    |
|coursewareId | 主键   |string  |    |
|coursewareName | 课件名称   |string  |    |
|coverImage | 封面图   |string  |    |
|templateType | 内容模板类型(1图文 2单图 3图视频 4视频)   |string  |    |

**EsContent**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|content | 内容   |string  |    |
|id | id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResScrollerDto|
## 搜索

**接口描述**:kevin

**接口地址**:`/index/search`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"text": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |QueryDto  | QueryDto   |

**schema属性说明**



**QueryDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|text|   | body | false |string  |    |

**响应示例**:

```json
[
	{
		"content": "",
		"contentId": "",
		"contentIntroduction": "",
		"contentName": "",
		"contentSonType": "",
		"coverImage": "",
		"modleName": "",
		"pageTemplateType": "",
		"sheetStatus": "",
		"templateId": ""
	}
]
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|content| 课件内容  |string  |    |
|contentId|   |string  |    |
|contentIntroduction| 简介  |string  |    |
|contentName| 内容名称/标题  |string  |    |
|contentSonType| 内容下级类型(1分类 2课件)  |string  |    |
|coverImage| 封面图  |string  |    |
|modleName| 模块名称  |string  |    |
|pageTemplateType| 过渡页模板类型(0无 1一类 2二类 3三类)  |string  |    |
|sheetStatus| 是否支持定制(0否 1是)  |string  |    |
|templateId| 定制表单模版Id   |string  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |首页搜索模型|
## 更新

**接口描述**:tangfan

**接口地址**:`/index/update`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"id": "",
	"pid": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqIdDto  | ReqIdDto   |

**schema属性说明**



**ReqIdDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|id| id  | body | false |string  |    |
|pid| 段落id  | body | false |string  |    |

**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
# 课件es

## 创建课件索引

**接口描述**:kevin

**接口地址**:`/cours/createCours`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：
暂无



**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 删除

**接口描述**:kevin

**接口地址**:`/cours/delete`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"id": "",
	"pid": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqIdDto  | ReqIdDto   |

**schema属性说明**



**ReqIdDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|id| id  | body | false |string  |    |
|pid| 段落id  | body | false |string  |    |

**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## saveAll

**接口描述**:kevin

**接口地址**:`/cours/saveAll`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：
暂无



**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 保存一个

**接口描述**:kevin

**接口地址**:`/cours/saveOne`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"id": "",
	"pid": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqIdDto  | ReqIdDto   |

**schema属性说明**



**ReqIdDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|id| id  | body | false |string  |    |
|pid| 段落id  | body | false |string  |    |

**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 分页

**接口描述**:kevin

**接口地址**:`/cours/scroll`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"page": 0,
	"sid": "",
	"size": 0,
	"text": "",
	"type": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqScrollerDto  | ReqScrollerDto   |

**schema属性说明**



**ReqScrollerDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|page| 当前页数  | body | false |integer(int32)  |    |
|sid| 分页id  | body | false |string  |    |
|size| 每页数量  | body | false |integer(int32)  |    |
|text| 查询内容  | body | false |string  |    |
|type| 所属分类 1 全能校长 2 云端知识库   9全部    | body | false |string  |    |

**响应示例**:

```json
{
	"cloudContentList": [
		{
			"content": "",
			"contentId": "",
			"contentIntroduction": "",
			"contentName": "",
			"contentSonType": "",
			"coverImage": "",
			"modleName": "",
			"pageTemplateType": "",
			"sheetStatus": "",
			"templateId": ""
		}
	],
	"dettContentList": [
		{
			"content": "",
			"contentId": "",
			"contentIntroduction": "",
			"contentName": "",
			"contentSonType": "",
			"coverImage": "",
			"modleName": "",
			"pageTemplateType": "",
			"sheetStatus": "",
			"templateId": ""
		}
	],
	"flush": true,
	"list": [
		{
			"auditStatus": "",
			"content": [
				{
					"content": "",
					"id": ""
				}
			],
			"contentSonType": "",
			"coursewareId": "",
			"coursewareName": "",
			"coverImage": "",
			"templateType": ""
		}
	],
	"sid": ""
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|cloudContentList|   |array  | 首页搜索模型   |
|dettContentList|   |array  | 首页搜索模型   |
|flush| 分页过期刷新页面 true 刷新 false 不刷新  |boolean  |    |
|list| 全部搜索结果  |array  | 课件   |
|sid| 分页id  |string  |    |



**schema属性说明**




**首页搜索模型**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|content | 课件内容   |string  |    |
|contentId |    |string  |    |
|contentIntroduction | 简介   |string  |    |
|contentName | 内容名称/标题   |string  |    |
|contentSonType | 内容下级类型(1分类 2课件)   |string  |    |
|coverImage | 封面图   |string  |    |
|modleName | 模块名称   |string  |    |
|pageTemplateType | 过渡页模板类型(0无 1一类 2二类 3三类)   |string  |    |
|sheetStatus | 是否支持定制(0否 1是)   |string  |    |
|templateId | 定制表单模版Id    |string  |    |

**课件**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|auditStatus | 审核状态(0待提交 1审核中 2已退回 3待上架 4上架中 5已下架)   |string  |    |
|content | 内容   |array  | EsContent   |
|contentSonType | 内容下级类型(1分类 2课件)   |string  |    |
|coursewareId | 主键   |string  |    |
|coursewareName | 课件名称   |string  |    |
|coverImage | 封面图   |string  |    |
|templateType | 内容模板类型(1图文 2单图 3图视频 4视频)   |string  |    |

**EsContent**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|content | 内容   |string  |    |
|id | id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResScrollerDto|
## 搜索

**接口描述**:kevin

**接口地址**:`/cours/search`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"text": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |QueryDto  | QueryDto   |

**schema属性说明**



**QueryDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|text|   | body | false |string  |    |

**响应示例**:

```json
[
	{
		"auditStatus": "",
		"content": [
			{
				"content": "",
				"id": ""
			}
		],
		"contentSonType": "",
		"coursewareId": "",
		"coursewareName": "",
		"coverImage": "",
		"templateType": ""
	}
]
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|auditStatus| 审核状态(0待提交 1审核中 2已退回 3待上架 4上架中 5已下架)  |string  |    |
|content| 内容  |array  | EsContent   |
|contentSonType| 内容下级类型(1分类 2课件)  |string  |    |
|coursewareId| 主键  |string  |    |
|coursewareName| 课件名称  |string  |    |
|coverImage| 封面图  |string  |    |
|templateType| 内容模板类型(1图文 2单图 3图视频 4视频)  |string  |    |



**schema属性说明**




**EsContent**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|content | 内容   |string  |    |
|id | id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |课件|
## 更新

**接口描述**:tangfan

**接口地址**:`/cours/update`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"id": "",
	"pid": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqIdDto  | ReqIdDto   |

**schema属性说明**



**ReqIdDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|id| id  | body | false |string  |    |
|pid| 段落id  | body | false |string  |    |

**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
## 批量更新

**接口描述**:tangfan

**接口地址**:`/cours/updateAll`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"id": "",
	"pid": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dto| dto  | body | true |ReqIdDto  | ReqIdDto   |

**schema属性说明**



**ReqIdDto**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|id| id  | body | false |string  |    |
|pid| 段落id  | body | false |string  |    |

**响应示例**:

```json

```

**响应参数**:


暂无





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
